/*
 * EcoSim.java
 * @author Sunny Jiao
 * Ecosystem simulator
 * April 16, 2019
 * @version 1.0
 * 
 */

import java.util.List;
import java.util.ArrayList;

/*
* Ecosystem Simulator
* Simulates wolves, sheep and grass
* Additions:
*   - Age: Entities have ages; when they grow too old, they will die regardless of their health
*   - Reproductive timer: Animals will require a cooldown period before they can reproduce again
*   - Wolf pathfinding: Wolves will go towards a sheep if it is within 4 blocks
*   - Grass growth: Grass only spreads from other grass; if all grass dies, no new grass will spawn
*   - Nutrition: Nutritional value is based off of the entity's current health
*/
class EcoSim{
    
    private Entity[][] world;  // Visual representation of the ecosystem
    private List<Entity> entityList;  // List of all entities alive in the simulation
    private List<Entity> spawnQueue;  // List of entities queued to spawn
    private boolean inGame;
    private boolean endOnExtinction;  // Setting on whether to end the program on an extinction
    private int cycles;  // Number of iterations passed in the simulation
    private int sheepCount = 0;
    private int wolfCount = 0;
    private int plantCount = 0;
    
    /* Constructor
     * Initializes a world with the given size
     */
    public EcoSim(int size){
        world = new Entity[size][size];  
        entityList = new ArrayList<Entity>();
        spawnQueue = new ArrayList<Entity>();
        endOnExtinction = true;
    }
    
    /*
    * Update the simulation for 1 cycle:
    * Updates entity locations, deals with entity interactions, spawns new entities,
    * deletes dead entities, and updates the 2D world array
    */
    public void updateWorld(){
        
        // Iterate through each entity and update their coords, health, age
        for(int i = 0; i < entityList.size(); i++){
            Entity ent = entityList.get(i);
            
            ent.update(world);

            // Spawn plants
            if(ent instanceof Plant){
                if(((Plant)ent).spread()){
                    spawnQueue.add(new Plant());
                }
            }
            
            if(ent instanceof Animal){
                // Loop through each other entity
                for(int j = 0; j < entityList.size(); j++){
                    Entity ent2 = entityList.get(j);
                    // Check for intersection
                    if(ent != ent2 && ent.checkIntersection(ent2)){
                        // Special case for Animal interacting with animals: They will never occupy the same
                        // box at the same time; interactions will occur between them regardless
                        if(ent2 instanceof Animal){
                            ent.setX(((Animal)ent).getPrevX());
                            ent.setY(((Animal)ent).getPrevY());
                        }
                        // If the intersection results in removed entities, shift the iterations back
                        if(interaction(ent, ent2)){
                            if(j < i){
                                i--;
                            }
                        }
                        break;  // We can stop checking for other interactions once one has already been made
                    }
                }
            }
            
            // Remove entity if it dies from age/decreasing health
            if(ent.checkIfDead()){
                entityList.remove(i);
                i--;
            }
        }
        
        // Spawn new entities
        for(Entity ent : spawnQueue){
            spawnEntity(ent);
        }
        // Wipe the spawn queue (new entities will be ignored if map is full)
        spawnQueue = new ArrayList<Entity>();
        
        // Refresh the 2D array with the updated entities
        for(int i = 0; i < world.length; i++){
            for(int j = 0; j < world[i].length; j++){
                world[i][j] = null;
            }
        }
        for(int i = 0; i < entityList.size(); i++){
            Entity ent = entityList.get(i);
            world[ent.getY()][ent.getX()] = ent;   
        }

        updateCount(); // Update animal numbers
        // Check for extinction
        if(endOnExtinction){
            if(checkExistance() != null){
                inGame = false;
            }
        }
        this.cycles++;  // Increment the number of cycles
    }
    
    /* 
     * Make interaction between two entities
     * @return True if an entity was deleted in the interaction, false otherwise
     */
    
    public boolean interaction(Entity ent1, Entity ent2){
        
        // Sheep + Plant
        if(ent1 instanceof Sheep && ent2 instanceof Plant){
            ent1.setHealth(ent1.getHealth() + ent2.getNutrition());
            entityList.remove(ent2);
            return true;
        }
        
        // Sheep + Sheep
        else if(ent1 instanceof Sheep && ent2 instanceof Sheep){
            if(((Animal)ent1).canReproduce() && ((Animal)ent2).canReproduce()){
                ent1.setHealth(ent1.getHealth() - 10);
                ent2.setHealth(ent2.getHealth() - 10);
                ((Animal)ent1).setReproductionTimer(0);
                ((Animal)ent2).setReproductionTimer(0);
                spawnQueue.add(new Sheep(20));
            }
            return false;
        }
        // Sheep + Wolf
        else if(ent1 instanceof Sheep && ent2 instanceof Wolf){
            ent2.setHealth(ent2.getHealth() + ent1.getNutrition());
            entityList.remove(ent1);
            return true;
        }
        // Sheep + Wolf
        else if(ent1 instanceof Wolf && ent2 instanceof Sheep){
            ent1.setHealth(ent1.getHealth() + ent2.getNutrition());
            entityList.remove(ent2);
            return true;
        }
        
        // Wolf + Plant
        else if(ent1 instanceof Wolf && ent2 instanceof Plant){
            entityList.remove(ent2);
            return true;
        }

        // Wolf + Wolf
        else if(ent1 instanceof Wolf && ent2 instanceof Wolf){
            // Breed
            if(((Animal)ent1).canReproduce() && ((Animal)ent2).canReproduce()){
                ent1.setHealth(ent1.getHealth() - 10);
                ent2.setHealth(ent2.getHealth() - 10);
                ((Animal)ent1).setReproductionTimer(0);
                ((Animal)ent2).setReproductionTimer(0);
                spawnQueue.add(new Wolf(25));
                return false;
            }
            // Fight
            else{
                if(((Wolf)ent1).compareTo((Wolf)ent2) > 0){
                    ent2.setHealth(ent2.getHealth() - 5);
                }else if(((Wolf)ent1).compareTo((Wolf)ent2) < 0){
                    ent1.setHealth(ent1.getHealth() - 5);
                }else{
                    ent1.setHealth(ent1.getHealth() - 5);
                    ent2.setHealth(ent2.getHealth() - 5);
                }
            }
        }
        return false;
    }
    
    /*
    * @return if the world is full
    */
    public boolean worldIsFull(){
        return entityList.size() == world.length * world.length;
    }

    public boolean checkCoord(int x, int y){
        for(Entity ent : entityList){
            if(ent.getX() == x && ent.getY() == y){
                return true;
            }
        }
        return false;
    }
    
    /*
    * Add an entity into the entity list
    */
    public void spawnEntity(Entity ent){
        
        // Only spawn Entity if there is space on the world
        if(!worldIsFull()){
            int x = (int)(Math.random() * world.length);
            int y = (int)(Math.random() * world.length);
            while(checkCoord(x,y)){
                x = (int)(Math.random() * world.length);
                y = (int)(Math.random() * world.length);
            }
            entityList.add(ent);
            ent.setX(x);
            ent.setY(y);
        }
    }
    
    /*
    * Display a string representation of the world
    */
    public void printWorld(){
        
        for(int i = 0; i < world.length; i++){
            for(int j = 0; j < world[i].length; j++){
                if(world[i][j] == null){
                    System.out.print(".\t");
                }else{
                    System.out.print(world[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
    * Count the current number of animals
    */
    public void updateCount(){
        
        plantCount = 0;
        sheepCount = 0;
        wolfCount = 0;

        for(int i = 0; i < entityList.size(); i++){
            if(entityList.get(i) instanceof Sheep){
                sheepCount++;
            }else if(entityList.get(i) instanceof Wolf){
                wolfCount++;
            }else if(entityList.get(i) instanceof Plant){
                plantCount++;
            }
        }
    }

    /*
    * Check if an animal is extinct
    * @return the animal as a string if it is extinct, null otherwise
    */
    public String checkExistance(){

        if(sheepCount == 0){
            return "Sheep";
        }else if(wolfCount == 0){
            return "Wolves";
        }else if(plantCount == 0){
            return "Plants";
        }else return null;        
    }

    /*
    * Disable the stopping of the program at an extinction
    */
    public void disableExtinction(){
        endOnExtinction = false;
    }
    
    /*
    * Run the simulation loop
    */
    public void runSim(){
        
        //Set up Grid Panel
        DisplayEcoGrid grid = new DisplayEcoGrid(this);
    
        inGame = true;
        while(inGame){
            
            //Display the grid on a Panel
            grid.refresh();
            
            //Small delay
            try{
                Thread.sleep(400);
            }catch(Exception e){};
            
            updateWorld();
        }
        grid.refresh();
        if(endOnExtinction){
            System.out.println(checkExistance() + " have gone extinct!");
        }
    }
    
    /*
    * Print a string representation of the living entities
    */
    public void printEntList(){
        for(int i = 0; i < entityList.size(); i++){
            System.out.print(entityList.get(i) + " " + entityList.get(i).getHealth() + "  ");
        }
        System.out.println();
    }

    // Getter methods
    public Entity[][] getWorld(){
        return this.world;
    }

    public int getCycles(){
        return this.cycles;
    }

    public boolean getEndOnExtinction(){
        return endOnExtinction;
    }

    public int getEntityCount(String strEnt){
        if(strEnt.equals("Sheep")){
            return this.sheepCount;
        }else if(strEnt.equals("Wolf")){
            return this.wolfCount;
        }else{
            return this.plantCount;
        }
    }
}
