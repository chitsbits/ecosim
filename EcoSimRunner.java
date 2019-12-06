/*
 * EcoSimRunner.java
 * @author Sunny Jiao
 * Ecosystem simulator runner file
 * April 16, 2019
 * @version 1.0
 */

import java.util.Scanner;

/*
* Ecosystem simulator runner program
* Recommended starting values:
* Worldsize: 25
* Sheep: 50
* Wolves: 8
* Plants: 260
*/
class EcoSimRunner{
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter size of world (length):");
        int worldSize = input.nextInt();

        // Create the ecosim
        EcoSim mySim = new EcoSim(worldSize);
        
        System.out.println("Enter number of sheep to spawn:");
        int numSheep = input.nextInt();
        for(int i = 0; i < numSheep; i++){
            mySim.spawnEntity(new Sheep());
        }
        
        System.out.println("Enter number of wolves to spawn:");
        int numWolves = input.nextInt();
        for(int i = 0; i < numWolves; i++){
            mySim.spawnEntity(new Wolf());
        }
        
        System.out.println("Enter number of plants to spawn:");
        int numPlants = input.nextInt();
        for(int i = 0; i < numPlants; i++){
            mySim.spawnEntity(new Plant());
        }

        System.out.println("End sim if extinction occurs? Y/N");
        String str = input.next();
        if(str.equals("N")){
             mySim.disableExtinction();
        }
    
        // Run simulation
        mySim.runSim();

        // End
        input.close();
    }
}