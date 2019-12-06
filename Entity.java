/*
 * Entity.java
 * @author Sunny Jiao
 * Abstract class for an entity
 * April 16, 2019
 * @version 1.0
 * 
 */

 /*
 * Abstract class that describes an entity in the simulation
 */
abstract class Entity{
    
    private int age;
    private int maxAge;
    private int health;
    private int nutrition;
    private int x,y;

    /*
    * Constructor
    * Initalizes the age, health, max age, and nutrition
    */
    public Entity(int h, int m){
        this.age = 0;
        this.health = h;
        this.maxAge = m;
        this.nutrition = (int)(this.getHealth() * 0.5) + 5;  // Nutritional value = fraction of its health + 5
    }
    
    public abstract void update(Entity[][] world);
    
    /*
    * @return true if this entity and other entity intersect at the same x,y
    */
    public boolean checkIntersection(Entity other){
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
    
    /*
    * @return true if entity's age has surpassed the max age, or if its health is 0
    */
    public boolean checkIfDead(){
        if(this.age >= this.maxAge){
            return true;            
        }else if(this.health <= 0){
            return true;
        }
        return false;
    }

    // Getter methods
    public int getAge(){
        return this.age;
    }
    public int getMaxAge(){
        return this.maxAge;
    }
    public int getNutrition(){
        return this.nutrition;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getHealth(){
        return this.health;
    }

    // Setter methods
    public void setAge(int a){
        this.age = a;
    }
    public void setMaxAge(int a){
        this.maxAge = a;
    }
    public void setHealth(int h){
        this.health = h;
    }
    public void setNutrition(int n){
        this.nutrition = n;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}