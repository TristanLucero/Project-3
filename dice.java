package com.company;

import java.util.*;

public class Dice {
    static int diceArray[] = new int[6]; //each dice in the diceArray holds the value of the symbols
    /*0 = arrow
      1 = dynamite
      2 = bull's eye "1"
      3 = bull's eye "2"
      4 = beer
      5 = gatling
    */
    static boolean diceRerolled[] = new boolean[6]; //gets updated everytime a dice is rolled, so dice can't be rolled again
    public int gatlingCount = 0; //how many times gatling has been rolled, default zero
    private int rerollsPlayer = 0; //rerolls player has done in total
    public static int dynamiteCount = 0;

    static private boolean saloonDice[] = new boolean[6];
    static private boolean deadOrAliveDice[] = new boolean[6];
    static private boolean expansionUsed = false;


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
    public Dice rollOneDice(Dice dice, int diceIndex){
        if (canBeRerolled(dice, diceIndex)){
            Random rand = new Random();
            diceArray[diceIndex] = rand.nextInt(6);
            diceRerolled[diceIndex] = true;
            rerollsPlayer++;
        }
        calcDiceCounts();
        return dice;
    }

    //Rerolled dice, up to the full amount of dice. Array values set to true will be rerolled if they can be.
    public Dice rerollDice(Dice dice, boolean diceToReroll[]){
        Random rand = new Random();
        boolean diceHaveBeenRerolled = false; //will be set to true if any dice is rerolled

        if(diceToReroll[0] == true && canBeRerolled(dice, 0)){
            diceArray[0] = rand.nextInt(6);
            diceRerolled[0] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[1] == true && canBeRerolled(dice, 1)){
            diceArray[1] = rand.nextInt(6);
            diceRerolled[1] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[2] == true && canBeRerolled(dice, 2)){
            diceArray[2] = rand.nextInt(6);
            diceRerolled[2] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[3] == true && canBeRerolled(dice, 3)){
            diceArray[3] = rand.nextInt(6);
            diceRerolled[3] = true;
            diceHaveBeenRerolled = true;
        }
        if(diceToReroll[4] == true && canBeRerolled(dice, 4)){
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
    public boolean canBeRerolled(Dice dice, int diceIndex){
        if(diceRerolled[diceIndex] == true) //if dice is rerolled, you can't reroll it again
            return false;
        //-----------------------------------------CHANGE FOR EXPANSION-----------------------
        if(diceArray[diceIndex] == 1) //if dice is dynamite, can't reroll
            return false;
        return true;
    }

    public String toString(int symbolIndex){ //stopped here lately
        String string = new String("");
        if(saloonDice[symbolIndex] == true){
            if(symbolIndex == 0)
                string = "Broken Arrow";
            else if(symbolIndex == 1)
                string = "Bullet";
            else if(symbolIndex == 2)
                string = "Double Bull's Eye \"1\"";
            else if(symbolIndex == 3)
                string = "Double Bull's Eye \"2\"";
            else if(symbolIndex == 4)
                string = "Double Beer";
            else if(symbolIndex == 5)
                string = "Double Gatling";
        }
        else if(deadOrAliveDice[symbolIndex] == true){
            if(symbolIndex == 0)
                string = "Indian Arrow";
            else if(symbolIndex == 1)
                string = "Dynamite";
            else if(symbolIndex == 2)
                string = "Whiskey Bottle";
            else if(symbolIndex == 3)
                string = "Fight a Dual";
            else if(symbolIndex == 4)
                string = "Fight a Dual";
            else if(symbolIndex == 5)
                string = "Gatling";
        }
        else{
            if(symbolIndex == 0)
                string = "Indian Arrow";
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
        }
        return string;
    }

    //if gatling is >= 3 returns true
    public boolean gatlingReady(){
        if(gatlingCount >= 3) {
            gatlingCount = 0;
            return true;
        }
        else
            return false;
    }

    //if dynamite is >= 3 returns true
    public boolean dynamiteReady(){
        if(dynamiteCount >= 3) {
            dynamiteCount = 0;
            return true;
        }
        else
            return false;
    }

    //calculate gatling and dynamite counts for the current dice in hand
    //-------------------------------------NEED TO CHANGE FOR EXPANSION----------------------------------------------
    public void calcDiceCounts(){
        gatlingCount = 0;
        dynamiteCount = 0;
        for(int i = 0; i < diceArray.length; i++){
            if(saloonDice[i] == true){
                if(diceArray[i] == 5)
                    gatlingCount++;
            }
            if(deadOrAliveDice[i] == true){
                if(diceArray[i] == 5)
                    gatlingCount++;
                if(diceArray[i] == 1)
                    dynamiteCount++;
            }
            else{
                if (diceArray[i] == 5)
                    gatlingCount++;
                if (diceArray[i] == 1)
                    dynamiteCount++;
            }
        }
    }

    //Call the function to use expansion in dice class,
    // pass the number of dice you want to use from saloon expansion, either 1 or 2 or them.
    //2 deadOrAliveDice / black dice used automatically
    public static void useExpansion(int saloonDiceToUse){
        expansionUsed = true;
        deadOrAliveDice[4] = true;
        deadOrAliveDice[5] = true;
        if(saloonDiceToUse == 1)
            saloonDice[3] = true;
        else if(saloonDiceToUse == 2){
            saloonDice[2] = true;
            saloonDice[3] = true;
        }
    }

    //sets expansion to not be used, sets all dice to default dice
    public static void dontUseExpansion(){
        expansionUsed = false;
        for (int i = 0; i < diceArray.length; i++) {
            deadOrAliveDice[i] = false;
            saloonDice[i] = false;
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        // write your code here
    }
}
