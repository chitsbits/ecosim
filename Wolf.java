/*
 * Wolf.java
 * @author Sunny Jiao
 * Wolf animal
 * April 16, 2019
 * @version 1.0
 * 
 */

class Wolf extends Animal implements Comparable<Wolf>{
   
    /*
    * Constructors
    */
    public Wolf(){
        super((int)(Math.random() * 10) + 50, 200); // Wolf will have random health from 50-60 and max age of 200
    }
    
    public Wolf(int health){
        super(health, 200);
    }

    /*
    * Overrides the Animal class' findPath method
    * Wolves have a seperate path finding method, where they will go toward
    * a sheep if it is within a 4 block radius
    */
    @Override
    public void findPath(Entity[][] world){

        // Find random path by default
        super.findPath(world);

        int x = this.getX();
        int y = this.getY();
        // If prey is in vicinity, go towards them
        for(int i = x - 4; i < x + 4; i++){
            for(int j = y - 4; j < y + 4; j++){
                if(i >= 0 && j >= 0 && i < world.length && j < world.length){
                    if(world[i][j] instanceof Sheep){
                        Entity prey = world[i][j];
                        if(x > prey.getX()){
                            this.setDirection(1);  // Move left
                        }else if(x < prey.getX()){
                            this.setDirection(3);  // Move right
                        }else{
                            if(y > prey.getY()){
                                this.setDirection(2); // Move up
                            }else if(y < prey.getY()){
                                this.setDirection(4);  // Move down
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*
    * @return this wolf's health compared to the other wolf's health
    */
    public int compareTo(Wolf other){
        return this.getHealth() - other.getHealth();
    }
    
    /*
    * Overriden toString method of the object class
    * Returns a string representing a wolf ("W")
    */
    @Override
    public String toString(){
        return "W";
    }
}