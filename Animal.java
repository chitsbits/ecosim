/*
 * Animal.java
 * @author Sunny Jiao
 * Abstract animal class
 * April 16, 2019
 * @version 1.0
 */

 /*
 * Abstract animal object, extends entity
 * Animals are able to move around and interact with other entities
 * Two animal subclasses: Wolf and Sheep
 */

abstract class Animal extends Entity{
    
    private int direction;  // Direction in the world that the animal will move to
    private int prevX;  // X coordinate in the previous cycle
    private int prevY;  // Y coordinate in the previous cycle
    private int reproductionTimer;  // Cooldown for reproduction
    
    /*
    * Constructor
    * Initalizes basic entity properties (i.e. health, age, etc.)
    * and reprduction timer
    */
    public Animal(int h, int m){
        super(h, m);
        this.reproductionTimer = 0;
    }
    
    /*
    * Update the animal for 1 cycle:
    * Updates health, age, reproduction timer, coordinates
    */
    public void update(Entity[][] world){
        this.setAge(this.getAge() + 1);
        this.setHealth(this.getHealth() - 1);
        this.reproductionTimer += 1;
        this.findPath(world);
        this.updateCoord();
    }

    /*
    * @return true if the animal is able to reproduce
    */
    public boolean canReproduce(){
        return reproductionTimer > 9 && this.getHealth() > 20;
    }
    
    /*
    * Set direction to a number between 0 - 4
    */
    public void findPath(Entity[][] world){
        
        this.prevX = this.getX();  // Save coordinates from before it moves
        this.prevY = this.getY();
        
        /* 1 - left
         * 2 - up
         * 3 - right
         * 4 - down
         * 0 - stationary
         */
        
        boolean validMove = false;
        
        do{
            direction = (int)((Math.random()) * 5);
            if((direction == 1 && this.getX() - 1 >= 0) || (direction == 2 && this.getY() - 1 > 0) ||
               (direction == 3 && this.getX() + 1 < world.length) || (direction == 4 && this.getY() + 1 < world.length)){
                validMove = true;
               }
        }while(!validMove);
    }

    /*
    * Update the animal's X,Y based on the direction
    */
    public void updateCoord(){
        if(direction == 1){
            this.setX(this.getX() - 1);
        }else if(direction == 2){
            this.setY(this.getY() - 1);
        }else if(direction == 3){
            this.setX(this.getX() + 1);
        }else if(direction == 4){
            this.setY(this.getY() + 1);
        }
    }
    
    // Getter and setters
    public int getPrevX(){
        return this.prevX;
    }
    public int getPrevY(){
        return this.prevY;
    }
    public void setDirection(int d){
        this.direction = d;
    }
    public void setReproductionTimer(int t){
        this.reproductionTimer = t;
    }
}