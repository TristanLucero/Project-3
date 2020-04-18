package com.company;

import java.util.*;

public class Dice {
    static int diceArray[] = new int[5]; //each dice in the diceArray holds the value of the symbols
    /*0 = arrow
      1 = dynamite
      2 = bull's eye "1"
      3 = bull's eye "2"
      4 = beer
      5 = gatling
    */
    static boolean diceRerolled[] = new boolean[5]; //gets updated everytime a dice is rolled, so dice can't be rolled again
    public int gatlingCount = 0; //how many times gatling has been rolled, default zero
    private int rerollsPlayer = 0; //rerolls player has done in total
    private int previousDice[] = new int[5];

    public static int dynamiteCount = 0; //EDIT THIS AND ADD MORE TO IT


    public void dice(){
    }

    public Dice rollAllDice(Dice dice){
        Random rand = new Random();
        for (int i = 0; i < diceArray.length; i++) {
            diceArray[i] = rand.nextInt(6);
        }
        calcDiceCounts();
        return dice;
    }

    //only rolls 1 dice, doesn't need array just uses index.
    public Dice rollOneDice(Dice dice, int diceIndex, Character character){
        if (canBeRerolled(dice, diceIndex, character)){
            Random rand = new Random();
            diceArray[diceIndex] = rand.nextInt(6);
            diceRerolled[diceIndex] = true;
            rerollsPlayer++;
        }
        calcDiceCounts();
        return dice;
    }

    //Rerolled dice, up to the full amount of dice. Array values set to true will be rerolled if they can be.
    public Dice rerollDice(Dice dice, boolean diceToReroll[], Character character){
        Random rand = new Random();
        boolean diceHaveBeenRerolled = false; //will be set to true if any dice is rerolled

        if(diceToReroll[0] == true && canBeRerolled(dice, 0, character)){
            diceArray[0] = rand.nextInt(6);
            diceRerolled[0] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[1] == true && canBeRerolled(dice, 1, character)){
            diceArray[1] = rand.nextInt(6);
            diceRerolled[1] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[2] == true && canBeRerolled(dice, 2, character)){
            diceArray[2] = rand.nextInt(6);
            diceRerolled[2] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[3] == true && canBeRerolled(dice, 3, character)){
            diceArray[3] = rand.nextInt(6);
            diceRerolled[3] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[4] == true && canBeRerolled(dice, 4, character)){
            diceArray[4] = rand.nextInt(6);
            diceRerolled[4] = true;
            diceHaveBeenRerolled = true;
        }
        if (diceHaveBeenRerolled = true) {
            rerollsPlayer++;
            calcDiceCounts();
        }
        return dice;
    }

    //checks to see if dice can be rerolled or not at the index provided
    public boolean canBeRerolled(Dice dice, int diceIndex, Character character){
        if(diceRerolled[diceIndex] == true) //if dice is rerolled, you can't reroll it again
            return false;
        if(diceArray[diceIndex] == 1) { //if dice is dynamite, can't reroll unless they are character Black Jack
            if(character.getAbilityNum() == 3)
                return true;
            else
                return false;
        }
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

    //checks if gatling is ready which is normally 3 gatling rolls, or 2 with Willy The Kid
    public boolean gatlingReady(Character character){
        if(gatlingCount >= 3 || gatlingCount >= 2 && character.getAbilityNum() == 16) {
            gatlingCount = 0;
            return true;
        }
        else
            return false;
    }

    public boolean dynamiteReady(Character character){
        if(dynamiteCount >= 3 /*|| gatlingCount >= 2 && character.getAbilityNum() == 16*/) {
            dynamiteCount = 0;
            return true;
        }
        else
            return false;
    }

    public void calcDiceCounts(){
        gatlingCount = 0;
        dynamiteCount = 0;
        for(int i = 0; i < diceArray.length; i++){
            if(diceArray[i] == 5)
                gatlingCount++;
            if(diceArray[i] == 1)
                dynamiteCount++;
        }
    }

    public static void main(String[] args) {
        // write your code here
    }
}
