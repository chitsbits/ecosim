/*
 * Plant.java
 * @author Sunny Jiao
 * Plant entity
 * April 16, 2019
 * @version 1.0
 */

 /*
 * Plant class
 */
class Plant extends Entity{
    
    /*
    * Constructor
    */
    public Plant(){
        super((int)(Math.random() * 20) + 20, 120); // Plant will have a random health from 20 - 40 and a maxage of 120
        this.setNutrition((int)(this.getHealth() * 0.3));  // Plant will have a lower nutrition than other entities
    }
    
    /*
    * Update the entity for 1 cycle
    * For plants, simply update health and age, since plants don't move
    */
    public void update(Entity[][] world){
        this.setHealth(this.getHealth() - 1);
        this.setAge(this.getAge() + 1);
    }
    
    /*
    * Determine whether a new plant will spawn
    */
    public boolean spread(){
        double randNum = Math.random();
        // Grass will have approximately 7% chance to spread
        if(randNum <= 0.07){
            return true;
        }
        return false;
    }
    
    /*
    * Overriden toString method of the object class
    * Returns a string representing a plant ("G")
    */
    @Override
    public String toString(){
        return "G";
    }
}