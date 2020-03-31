package com.company;

import java.util.*;

public class Dice {
    public int diceArray[] = new int[5];
    public boolean diceRerolled[] = new boolean[5];
    public int gatlingCount = 0; //how many times gatling has been rolled, default zero
    public int rerollsPlayer = 0; //rerolls player has done in total


    public void dice(){
    }

    public Dice rollAllDice(Dice dice){
        Random rand = new Random();
        for (int i = 0; i < diceArray.length; i++) {
            diceArray[i] = rand.nextInt(6);
            if (diceArray[i] == 5)
                gatlingCount++;
        }
        return dice;
    }

    //only rolls 1 dice, doesn't need array just uses index.
    public Dice rollOneDice(Dice dice, int diceIndex){
        if (canBeRerolled(dice, diceIndex)){
            Random rand = new Random();
            diceArray[diceIndex] = rand.nextInt(6);
            diceRerolled[diceIndex] = true;
        }
        return dice;
    }

    //Rerolled dice, up to the full amount of dice. Array values set to true will be rerolled if they can be.
    public Dice rerollDice(Dice dice, boolean diceToReroll[]){
        Random rand = new Random();
        if(diceToReroll[0] == true && canBeRerolled(dice, 0)){
            diceArray[0] = rand.nextInt(6);
            diceRerolled[0] = true;
        }
        if(diceToReroll[1] == true && canBeRerolled(dice, 1)){
            diceArray[1] = rand.nextInt(6);
            diceRerolled[1] = true;
        }
        if(diceToReroll[2] == true && canBeRerolled(dice, 2)){
            diceArray[2] = rand.nextInt(6);
            diceRerolled[2] = true;
        }
        if(diceToReroll[3] == true && canBeRerolled(dice, 3)){
            diceArray[3] = rand.nextInt(6);
            diceRerolled[3] = true;
        }
        if(diceToReroll[4] == true && canBeRerolled(dice, 4)){
            diceArray[4] = rand.nextInt(6);
            diceRerolled[4] = true;
        }
        return dice;
    }

    //checks to see if dice can be rerolled or not at the index provided
    public boolean canBeRerolled(Dice dice, int diceIndex){
        if(diceRerolled[diceIndex] == true) //if dice is rerolled, you can't reroll it again
            return false;
        if(diceArray[diceIndex] == 1) //is dice is dynamite, can't reroll. ALSO SET CONDITION FOR CHARACTER ONCE CHARACTER DONE, BLACK JACK CAN REROLL DYNAMITE
            return false;
        return true;
    }

    public String toString(int symbolIndex){ //stopped here lately
        String string = new String("");
        if(symbolIndex == 0)
            string = "Indian arrow";
        else if(symbolIndex == 1)
            string = "Dynamite";
        else if(symbolIndex == 2)
            string = "Bull's Eye \"1\"";
        else if(symbolIndex == 3)
            string = "Bull's Eye \"2\"";
        else if(symbolIndex == 4)
            string = "Beer";
        else if(symbolIndex == 5)
            string = "Gatling";
        return string;
    }

    //checks if gatling is ready ALSO NEEDS TO CHECK FOR WILLY THE KID, WHO ONLY NEEDS 2 GATLING TO BE READY
    public boolean gatlingReady(){
        if(gatlingCount >= 3) {
            gatlingCount = 0;
            return true;
        }
        else
            return false;
    }

    public static void main(String[] args) {
        // write your code here
    }
}


