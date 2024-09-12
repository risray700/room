import java.util.Scanner; //may be necessary for input

import javax.swing.JOptionPane; //may be necessary for input

import kareltherobot.*;

import java.util.ArrayList;
import java.util.Collections;

public class DriverV implements Directions {
    // declared here so it is visible in all the methods!!
    // It will be assigned a value in the getInfo method
    private static Robot roomba = new Robot(26, 101, East, 0);

    static ArrayList<ArrayList<Integer>> pileMap =new ArrayList<>();

    // You will add very many variables!!

    public static void main(String[] args) {
        // LEAVE THIS ALONE!!!!!!
        DriverV d = new DriverV();

        /**
         * This section of code gets info from the user in the following order: 1. Ask
         * the user
         * which world file they wish to process. Right now, that world file name is
         * hardcoded in. 2. Ask the user for the starting location and direction of the
         * Robot. A new Robot should be constructed and assigned to the global
         * (instance) variable named roomba that is declared on line 10.
         * 
         * An inelegant way to interact with the user is via the console, using
         * System.out.println and a Scanner that scans System.in (input from the
         * console). A more elegant way to get user input might include using a
         * JOptionPane.
         */

        String wrldName = "finalTestWorld2024.wld";

        World.readWorld(wrldName);
        World.setVisible(true);
        World.setDelay(0);

        /**
         * This section will have all the logic that takes the Robot to every location
         * and cleans up all piles of beepers. Think about ways you can break this
         * large, complex task into smaller, easier to solve problems.
         */

        // the line below causes a null pointer exception
        // what is that and why are we getting it?
        int beep = 0;
        int area = 0;
        int pile = 0;
        int currRow=0;
        int tmpPile=0;
        ArrayList<Integer> piles=new ArrayList<>();
        while (true) {
            pileMap.add(new ArrayList<>());
            while (true) {
                
                if(roomba.nextToABeeper()){
                    pile++;
                    while (roomba.nextToABeeper()) {
                        roomba.pickBeeper();
                        beep++;
                        tmpPile++;
                    }
                }
                
                pileMap.get(currRow).add(tmpPile);
                if(!(tmpPile==0)){
                    piles.add(tmpPile);
                }
                tmpPile=0;
                
                if(!roomba.frontIsClear()){
                    break;
                }
                roomba.move();
                area++;
                
            }
            

            roomba.turnLeft();
            if (!roomba.frontIsClear()) {
                break;
            }
            

            roomba.move();
            currRow++;
            pileMap.add(new ArrayList<>());
            area++;
            roomba.turnLeft();
            while (true) {
                if(roomba.nextToABeeper()){
                    pile++;
                    while (roomba.nextToABeeper()) {

                        roomba.pickBeeper();
                        beep++;
                        tmpPile++;
                    }
                }
                
                pileMap.get(currRow).add(tmpPile);
                if(!(tmpPile==0)){
                    piles.add(tmpPile);
                }
                
                tmpPile=0;
                
                if(!roomba.frontIsClear()){
                    break;
                }
                roomba.move();
                area++;
            }

            roomba.turnLeft();
            roomba.turnLeft();
            roomba.turnLeft();
            if (!roomba.frontIsClear()) {
                break;
            }
            roomba.move();
            area++;
            currRow++;
            roomba.turnLeft();
            roomba.turnLeft();
            roomba.turnLeft();
        }
        roomba.turnLeft();
        roomba.turnLeft();
        roomba.turnLeft();
        int maxPile=0;
        ArrayList<Integer> maxPileCoords = new ArrayList<>();
        maxPile=Collections.max(piles);



        Collections.reverse(pileMap);
        
        //System.out.println("The largest pile (From the top left corner) is " + (maxPileCoords.get(1))+" right and " + (maxPileCoords.get(0))+" down");
        
        int tmpIndex=0;
        for(ArrayList<Integer> b:pileMap){
            if (tmpIndex%2==0){
                Collections.reverse(b);
            }
            tmpIndex++;
        }

        Boolean shouldBreak=false;
        for(ArrayList<Integer> c:pileMap){
            for(int e : c){
                if (e==maxPile){
                    shouldBreak=true;
                    maxPileCoords.add(pileMap.indexOf(c));
                    maxPileCoords.add(c.indexOf(e));
                    break;
                }
            }
            if(shouldBreak){
                break;
            }
        }


        
       
        System.out.println("The number of beepers are " + beep);
        System.out.println("The area of the room is " + (area + 1));
        System.out.println("The number of piles are " + (pile));
        System.out.println("The largest pile is " + (maxPile));
        System.out.println("The largest pile (From the bottom left corner) is " + (maxPileCoords.get(1))+" right and " + (maxPileCoords.get(0))+" down");
        System.out.println("The average pile size is "+(double)beep/piles.size());
        System.out.println("The percent dirty is "+(double)(piles.size()/(area+1.0)*100)+"% dirty");
        
    }

}
/**
 * This method displays the results of cleaning the room. All of the info
 * in the pdf that describes the problem need to be displayed. You can present
 * this info in the console (boring) or you can present using JOptionPane
 * (cool!)
 */