/*
 * Sheep.java
 * @author Sunny Jiao
 * Sheep animal
 * April 16, 2019
 * @version 1.0
 * 
 */

class Sheep extends Animal{
    
    /*
    * Constructors
    */
    public Sheep(){
        super((int)(Math.random() * 10) + 35, 170); // Sheep will have random health from 35-45 and max age of 170
    }
    
    public Sheep(int health){
        super(health, 170);
    }
    
    /*
    * Overriden toString method of the object class
    * Returns a string representing a sheep ("S")
    */
    @Override
    public String toString(){
        return "S";
    }
}