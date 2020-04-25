//CS 2365 OOP Spring 202 Section 002
//Peter Wharton
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Project_3_Game;

import java.util.*;


public class Game {

    private int arrowDeck = 9;
    private boolean chiefsArrow = true;
    private int numPlayers, numBeers, targetOne, targetTwo, arrows, beforeAlive;
    private int currAlive;
    private boolean isHuman = false;
    private int [] alive = new int [] {1, 0, 0, 0};
    
    //Method to start the game off and get the number of players wanted 
    public int StartGame() {
        int numPlayersUser, startGame;
        boolean pack;
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players do you want to play with? (3-7) ");
        numPlayersUser = scan.nextInt();
        pack = scan.hasNextBoolean();
        
        SetNumPlayers(numPlayersUser);
        
        //Creates all 16 different characters possible
        Character.initializeAllCharacters(pack);
        
        //Picks a character for each player 
        Character.assignCharacters(numPlayersUser, pack);
        
        //Picks a role for each player 
        Character.assignRole(numPlayersUser);
        
        //Assigns the user to a player 
        Character.assignUser(numPlayersUser);
        
        
        //Get rid of this at some point? Goes to GUI?
        System.out.println("Do you want to start the game now? (1: Yes, 2: No) ");
        startGame = scan.nextInt();
        while (startGame != 1) {
            System.out.println("Enter 1 to start when ready: ");
            startGame = scan.nextInt();
        }
        
        return numPlayers;
    }
    
    //Sets the games number of players that can be used for the methods
    public void SetNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
    
    //Method that gets the number of arrows left in the deck 
    public int GetArrowDeckNum () {
        return arrowDeck;
    }
    
    //Method that returns a boolean for if the chiefs arrow is in the deck or not
    public boolean CheckChiefsArrow() {
        return chiefsArrow;
    }
    
    //Method that checks if the current player is Human or AI 
    public boolean PlayerType(int index) {
        
        if (Character.playerList.get(index).getUser() == 1) {
            isHuman = true;
        }
        
        return isHuman;
    }
    
    //Method for checking who is still alive 
    public void WhoAlive() {
        alive[0] = 0; alive[1] = 0; alive[2] = 0; alive[3] = 0;
        // index 0 = sheriff, 1 = deputy, 2 = outlaw, 3 = renegade
        // if value is 0 means dead, 1 if alive, > 1 more than one alive
        
        for (int i = 0; i < Character.playerList.size(); ++i) {
            
            switch(Character.playerList.get(i).getRoleNum()) {
                case 4: 
                    alive[3] ++;
                    break;
                case 3:
                    alive[2] ++;
                    break;
                case 2:
                    alive[1] ++;
                    break;
                case 1: 
                    alive[0] ++;
                    break;
            }
        }
    }
    
    //Bart Cassidy player menu/running - 1
    public void BartCassidy(int currPlayer) {
        //speical of taking an arrow instead of losing health
            //except for indian or dynamite attack, and cant be last arrow

        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Calamity Janet player menu/running - 2
    public void CalamityJanet(int currPlayer) {
        //Can use a 1 target as a 2, or a 2 target as 1
        
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates roll count
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Jesse Jones player menu/running - 3
    public void JesseJones(int currPlayer) {
        //4 or less health you can heal for 2 life per bear rolled 
                    //FIXME - does this apply to yourself or everyone?
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Jourdonnais player menu/running - 4
    public void Jourdonnais(int currPlayer) {
        //only lose one health from indians (special done in Indian Attack)
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Paul Regret player menu/running - 5
    public void PaulRegret(int currPlayer) {
        //never lose health from gatling Gun (Done in GatlingAttack)
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Pedro Ramirez player menu/running - 6
    public void PedroRamirez(int currPlayer) {
        //if you lose life, you can dicard an arrow (done in change health/gatling)
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Rose Doolan player menu/running - 7
    public void RoseDoolan(int currPlayer) {
        //target can go one space farther 
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Vulture Sam player menu/running - 8
    public void VultureSam(int currPlayer) {
        //when someone dies you gain two life points (special where health is lost) 
        
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }   
    }
    
    //Apache Kid player menu/running - 9
    public void ApacheKid(int currPlayer) {
        //if you roll arrow you can take the chief's arrow from another player
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            
            //for special
            else if (Character.playerList.get(currPlayer).hasChiefsArrow()) {
                //nothing happens b/c he already has it
            }
            else if (Character.playerList.get(currPlayer).hasChiefsArrow() == false) {
                
                //GUI option to take the Chiefs arrow or not for humans = playerChoice
                int playerChoice;
                if (playerChoice == 1) {
                    if (chiefsArrow) {
                        Character.playerList.get(currPlayer).setChiefsArrow(true);
                    }
                    else {
                        int chiefsArrowIndex = Character.FindChiefsArrow();
                        Character.RemoveChiefsArrow(chiefsArrowIndex, currPlayer);
                    }
                }
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Bill Noface player menu/running - 10
    public void BillNoface(int currPlayer) {
        //apply arrow results only after your last roll
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
            
            //For special, arrows happens at the end of the turn
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                    }
                    else {
                        Character.playerList.get(currPlayer).changeArrows(arrows);
                        arrowDeck -= arrows;
                    }
                    
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
        }  
    }
    
    //Belle Star player menu/running - 11
    public void BelleStar(int currPlayer) {
        //after each roll you can change one dynamite to gatting gun
                    //not if you roll 3 or more dynamite
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            //For special and changing one dynamite to gat gun
            else if (Dice.HasDynamite()) {
                Dice.BelleStarSpecial();
            }
            
            //FIXME - not sure how to get this to work with gatling count if im changing one
            //index manually
            
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        }
    }
    
    //Greg Digger player menu/running - 12
    public void GregDigger(int currPlayer) {
        //you can use the whiskey bottle twice for each time you roll it 
                
        //Start by rolling all the dice and then using the gatling gun
        Dice.rollAllDice();
        while (Dice.rerollsPlayer != 4 && stopTurn != 1 && 
                Character.playerList.get(currPlayer).isAlive()){

            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    if (Character.playerList.get(currPlayer).isAlive() != true) {
                        break;
                    }
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                break;
            }
            
            //Updates 
            Dice.rerollsPlayer ++;
            if (Dice.rerollsPlayer <= 3) {
                //Ask the user in the GUI if they want to reroll anything, 
                //If they dont pass in the endTurn thing
                if(endTurn == 1) {
                    break;
                }
                else{
                    //Have the GUI update the dice that want to be rerolled
                }
            }
            else {
                break;
            }

        }
        
        //Checks if they died in the middle of their turn 
        if (Character.playerList.get(currPlayer).isAlive() != true) {
            }
        else {
            //gets the number of beers for finishing turn 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 4) {
                        numBeers += 1;
                    }
            }

            //Applies beers as wanted
            while (numBeers != 0) {
                //Get the player index of who they want to heal from the GUI = heal Player
                //GUI should be showing dead people so they should be able to choose that in GUI
                Beer(healPlayer);
            }

            //check target one's and applies those changes 
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 2) {
                        targetOne += 1;
                    }
            }
            while (targetOne != 0) {
                TargetOne(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }

            //check target two's and applies those changes
            for (int i = 0; i < 5; ++i) {
                    if (Dice.diceArray[i] == 3) {
                        targetTwo += 1;
                    }
            }
            while (targetTwo != 0) {
                TargetTwo(Character.playerList.get(currPlayer).getRoleNum(), currPlayer);
            }
            Dice.gatlingUsed = false;
        } 
    }
     
    //For when Bart Cassidy takes damage from other than indian attack or dynamite
    public void BartCassidySpecial(int playerType, int playerIndex) {
        int playerChoice;
        
        if (playerType == 1) { //AI
            if (arrowDeck > 1) {
                    Character.playerList.get(playerIndex).changeArrows(1);
                    arrowDeck --;
                }
                else {
                    Character.playerList.get(playerIndex).changeHealth(-1);
                }
        }
        else {              //Human
            if (arrowDeck > 1) {
                    //Give option to take an arrow instead of damage = playerChoice
                    if (playerChoice == 1) {
                        Character.playerList.get(playerIndex).changeArrows(1);
                        arrowDeck --;
                    }
                    else {
                        Character.playerList.get(playerIndex).changeArrows(1);
                        arrowDeck --;
                    }
                }
                else {
                    Character.playerList.get(playerIndex).changeHealth(-1);
                }
        }
        
    }
    
    //For when Pedro Ramirez takes damage 
    public void PedroRamirezSpecial(int playerIndex) {
        if (Character.playerList.get(playerIndex).getArrowCount() > 0) {
            Character.playerList.get(playerIndex).changeArrows(-1);
            arrowDeck ++;
        }
    }
    
    //Method that will shoot the target one, takes in the amount of targets, 
    //and the player to hit
    public void TargetOne(int playerSpecial, int currPlayer) {
        int optionOne, optionTwo, playerOption, playerChoice;
        
        
        if (Character.VultureSam()) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //Calamity Janet specials
        if (playerSpecial == 2 && Character.playerList.get(currPlayer).getUser() == 0) {  
            //get from GUI if they want to use one as a one or two = playerChoice
            if (playerChoice == 1) {                    //they want to use one as a one
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 1;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 1;
                }

                //For no special effecting choice
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
            
            else {                                  //They want to use one as a two
                
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 1;
                }
                else if (currPlayer == numPlayers - 2) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 2;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 2;
                }
                else if (currPlayer == 1) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 2;
                }
                
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
        }
        
        //Rose Doolan Special for human
        else if (playerSpecial == 7 && Character.playerList.get(currPlayer).getUser() == 0) {
            //get from GUI if they want to use one as a one or one as a two = playerChoice
            if (playerChoice == 1) {                    //they want to use one as a one
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 1;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 1;
                }

                //For no special effecting choice
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
            
            else {                                  //They want to use one as a two
                
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 1;
                }
                else if (currPlayer == numPlayers - 2) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 2;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 2;
                }
                else if (currPlayer == 1) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 2;
                }
                
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
        }
        else {
            //Finds starting index for options
            if (currPlayer == numPlayers - 1) {
                optionOne = 0;
            }
            else {
                optionOne = currPlayer + 1;
            }

            if (currPlayer == 0) {
                optionTwo = numPlayers - 1;
            }
            else {
                optionTwo = currPlayer - 1;
            }

            //For no special effecting choice
            //To find the first person they can attack
            while (Character.playerList.get(optionOne).isAlive() == false) {
                optionOne ++;
                if (optionOne >= numPlayers) {
                    optionOne = 0;
                }
            }
            //To find the second person they can attack
            while (Character.playerList.get(optionTwo).isAlive() == false) {
                optionTwo --;
                if (optionTwo <= 0) {
                    optionTwo = numPlayers - 1;
                }
            } 
            
            //Give Options to GUI and then get the players choice back -> playerOption
        
            //Bart Cassidy Special for AI
            if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                    Character.playerList.get(playerOption).getUser() == 1) { 
                BartCassidySpecial(1, playerOption);
                targetOne -= 1;
            }
            //Bart Cassidy Special for human
            else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                    Character.playerList.get(playerOption).getUser() == 0) {  
                BartCassidySpecial(0, playerOption);
                targetOne -= 1;
            }
            //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            else {
                Character.playerList.get(playerOption).changeHealth(-1);
                targetOne -= 1;
            }
        }
        
        
        //For Vulture Sam's special to count alive
        if (Character.VultureSam()) {
            int samsIndex = Character.SamsPlace();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //updates GUI for health, alive/dead
    }
    
    //Method that will shoot the target two, takes in the amount of targets, 
    //and the player to hit
    public void TargetTwo(int playerSpecial, int currPlayer) {
        int optionOne, optionTwo, playerOption, playerChoice;
        
        if (Character.VultureSam()) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //For if the player special can effect their choice
            //Calamity Janet specials
        if (playerSpecial == 2 && Character.playerList.get(currPlayer).getUser() == 0) {       
         
            //get from GUI if they want to use one as a one or two = playerChoice
            if (playerChoice == 1) {                    //they want to use two as a one
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 1;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 1;
                }

                //For no special effecting choice
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetTwo -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetTwo -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetTwo -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetTwo -= 1;
                }
            }
            
            else {                                  //They want to use two as a two
                
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 1;
                }
                else if (currPlayer == numPlayers - 2) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 2;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 2;
                }
                else if (currPlayer == 1) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 2;
                }
                
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetTwo -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetTwo -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetTwo -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetTwo -= 1;
                }
            }
        }
        
        //Rose Doolan Special for human
        else if (playerSpecial == 7 && Character.playerList.get(currPlayer).getUser() == 0) {
            //get from GUI if they want to use one as a two as a two or a two as a three 
            // = playerChoice
            if (playerChoice == 1) {                    //they want to use two as a three
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 2;
                }
                else if (currPlayer == numPlayers -2) {
                    optionOne = 1;
                }
                else if (currPlayer == numPlayers -3) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 3;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 3;
                }
                else if (currPlayer == 1) {
                    optionTwo = numPlayers - 2;
                }
                else if (currPlayer == 2) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 3;
                }

                //For no special effecting choice
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
            
            else {                                  //They want to use two as a two
                
                //Finds starting index for options
                if (currPlayer == numPlayers - 1) {
                    optionOne = 1;
                }
                else if (currPlayer == numPlayers - 2) {
                    optionOne = 0;
                }
                else {
                    optionOne = currPlayer + 2;
                }

                if (currPlayer == 0) {
                    optionTwo = numPlayers - 2;
                }
                else if (currPlayer == 1) {
                    optionTwo = numPlayers - 1;
                }
                else {
                    optionTwo = currPlayer - 2;
                }
                
                //To find the first person they can attack
                while (Character.playerList.get(optionOne).isAlive() == false) {
                    optionOne ++;
                    if (optionOne >= numPlayers) {
                        optionOne = 0;
                    }
                }
                //To find the second person they can attack
                while (Character.playerList.get(optionTwo).isAlive() == false) {
                    optionTwo --;
                    if (optionTwo <= 0) {
                        optionTwo = numPlayers - 1;
                    }
                } 

                //Give Options to GUI and then get the players choice back -> playerOption

                //Bart Cassidy Special for AI
                if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 1) { 
                    BartCassidySpecial(1, playerOption);
                    targetOne -= 1;
                }
                //Bart Cassidy Special for human
                else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                        Character.playerList.get(playerOption).getUser() == 0) {  
                    BartCassidySpecial(0, playerOption);
                    targetOne -= 1;
                }
                //Pedro Ramirez Special for both human and AI 
                else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                    PedroRamirezSpecial(playerOption);
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
                else {
                    Character.playerList.get(playerOption).changeHealth(-1);
                    targetOne -= 1;
                }
            }
        }
        
        //For no special effecting choice
        else {
            //Finds starting index for options
            if (currPlayer == numPlayers - 1) {
                optionOne = 1;
            }
            else if (currPlayer == numPlayers - 2) {
                optionOne = 0;
            }
            else {
                optionOne = currPlayer + 2;
            }

            if (currPlayer == 0) {
                optionTwo = numPlayers - 2;
            }
            else if (currPlayer == 1) {
                optionTwo = numPlayers - 1;
            }
            else {
                optionTwo = currPlayer - 2;
            }

            //To find the first person they can attack
            while (Character.playerList.get(optionOne).isAlive() == false) {
                optionOne ++;
                if (optionOne >= numPlayers) {
                    optionOne = 0;
                }
            }
            //To find the second person they can attack
            while (Character.playerList.get(optionTwo).isAlive() == false) {
                optionTwo --;
                if (optionTwo <= 0) {
                    optionTwo = numPlayers - 1;
                }
                }
            
            //Give Options to GUI and then get the players choice back -> playerOption

            //Bart Cassidy Special for AI
            if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                    Character.playerList.get(playerOption).getUser() == 1) { 
                BartCassidySpecial(1, playerOption);
                targetTwo -= 1;
            }
            //Bart Cassidy Special for human
            else if (Character.playerList.get(playerOption).getAbilityNum() == 1 &&
                    Character.playerList.get(playerOption).getUser() == 0) {  
                BartCassidySpecial(0, playerOption);
                targetTwo -= 1;
            }
            //Pedro Ramirez Special for both human and AI 
            else if (Character.playerList.get(playerOption).getAbilityNum() == 6) {
                PedroRamirezSpecial(playerOption);
                Character.playerList.get(playerOption).changeHealth(-1);
                targetTwo -= 1;
            }
            else {
                Character.playerList.get(playerOption).changeHealth(-1);
                targetTwo -= 1;
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.VultureSam()) {
            int samsIndex = Character.SamsPlace();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //updates GUI for health, alive/dead
    }
    
    //Method that heals a player, take in the player number (index) of who they want to heal 
    public void Beer(int playerNum) {
        //For Jesse Jones special 
        if (Character.playerList.get(playerNum).getAbilityNum() == 3 &&
                Character.playerList.get(playerNum).getHealth() <= 4) {
            Character.playerList.get(playerNum).changeHealth(2);
            numBeers -= 1;
        }
        else {
            Character.playerList.get(playerNum).changeHealth(1);
            numBeers -= 1;
        }
        
        //updates GUI after each time it's called
    }
    
    //Method to use when the dynamite goes off
    public void DynamiteAttack(int playerIndex) {
        //Pedro Ramirez Special for both human and AI 
        if (Character.playerList.get(playerIndex).getAbilityNum() == 6) {
            PedroRamirezSpecial(playerIndex);
            Character.playerList.get(playerIndex).changeHealth(-1);
        }
        else {
            Character.playerList.get(playerIndex).changeHealth(-1);
        }
        
        //Tell GUI to update health, and player alive/dead
    }
    //When there are no more Indian cards in the deck runs through and reduces 
    //everyones health for the given amount of arrow cards they have
    public void IndianAttack() {
        if (Character.VultureSam()) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.playerList.get(i).isAlive() == false) {
                //skip that player 
            }
            //Jourdonnais Special 
            else if (Character.playerList.get(i).getAbilityNum() == 4) {
                int arrowCount = Character.playerList.get(i).getArrowCount();
                arrowDeck += arrowCount;
                Character.playerList.get(i).changeHealth(-1);
                Character.playerList.get(i).changeArrows(arrowCount * -1);
            }
            else {
                int arrowCount = Character.playerList.get(i).getArrowCount();
                arrowDeck += arrowCount;
                Character.playerList.get(i).changeHealth(arrowCount * -1);
                Character.playerList.get(i).changeArrows(arrowCount * -1);
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.VultureSam()) {
            int samsIndex = Character.SamsPlace();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }

        //Tells the GUI to update here for health, and player alive/dead , and arrow count
    }
    
    //When there are 3 or more gatling guns this is called and drops everyone health
    //unless they have a special to stop it or get an option to stop it
    public void GatlingAttack(int playerPos) {
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.VultureSam()) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
            if (i == playerPos) {
                //no health is taken from current player
            }
            else if (Character.playerList.get(i).isAlive() == false) {
                //skip dead player 
            }
            //Paul Regret Special
            else if (Character.playerList.get(i).getAbilityNum() == 5){
                //doesnt lose health
            }
            //If Bart Cassidy special and human to give option
            else if (Character.playerList.get(i).getAbilityNum() == 1 && 
                    Character.playerList.get(i).getUser() == 0) {
                BartCassidySpecial(0, i);
            }
            //If Bart Cassidy special and AI
            else if (Character.playerList.get(i).getAbilityNum() == 1 && 
                    Character.playerList.get(i).getUser() == 1){
                BartCassidySpecial(1, i);
            }
            //Pedro Ramirez Special for both human and AI 
            else if (Character.playerList.get(i).getAbilityNum() == 6) {
                PedroRamirezSpecial(i);
                Character.playerList.get(i).changeHealth(-1);
            }
            else {
                Character.playerList.get(i).changeHealth(-1);
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.VultureSam()) {
            int samsIndex = Character.SamsPlace();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        //Tells the GUI to update here for health, and player alive/dead 
    }
    
//Main Class -------------------------------------------------------------------
    
    public static void main(String[] args) {
        Game currGame = new Game();
        int currPlayer = 0, numPlayers, currChar; 
        
        //Initalizes the game 
        numPlayers = currGame.StartGame();
      
        //Used to start gameplay with the sheriff
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.playerList.get(i).getRoleNum() == 1) {
                currPlayer = i;
                break;
            }
        }
        //Starts the loop for playing 
        while(currGame.alive[0] == 1) {
            
            currChar = Character.playerList.get(currPlayer).getAbilityNum();
            
            switch(currChar) {
                case 1:     //Bart Cassidy
                    currGame.BartCassidy(currPlayer);
                    break;
                    
                case 2:     //Calamity Janet
                    currGame.CalamityJanet(currPlayer);
                    break;
                    
                case 3:     //Jesse Jones
                    currGame.JesseJones(currPlayer);
                    break;
                    
                case 4:     //Jourdonnais
                    currGame.Jourdonnais(currPlayer);
                    break;
                    
                case 5:     //Paul Regret
                    currGame.PaulRegret(currPlayer);
                    break;
                    
                case 6:     //Pedro Ramirez
                    currGame.PedroRamirez(currPlayer);
                    break;
                    
                case 7:     //Rose Doolan
                    currGame.RoseDoolan(currPlayer);
                    break;
                    
                case 8:     //Vulture Sam
                    currGame.VultureSam(currPlayer);
                    break;
                    
                case 9:     //Apache Kid
                    currGame.ApacheKid(currPlayer);
                    break;
                    
                case 10:    //Bill Noface
                    currGame.BillNoface(currPlayer);
                    break;
                    
                case 11:    //Belle Star
                    currGame.BelleStar(currPlayer);
                    break;
                    
                case 12:    //Greg Digger
                    currGame.GregDigger(currPlayer);
                    break;   
            }
            
            //Check if only the sheriff and/or Deputy are alive to end the game
            currGame.WhoAlive();
            if ((currGame.alive[0] >= 1 || currGame.alive[1] >= 1) && 
                    (currGame.alive[2] == 0 && currGame.alive[3] == 0)) {
                break;
            }
            
            //used to cycle through the player list 
            if (currPlayer == (numPlayers - 1)) {
                currPlayer = 0;
            }
            else {
                currPlayer ++;
            }
            
        }
        
        //Used for showing who the winner is 
        if (currGame.alive[0] == 0 && currGame.alive[1] == 0) {
            if (currGame.alive[2] == 0 && currGame.alive[3] == 1) {
                //renegade wins since there is only one renegade alive 
            } 
            else {
                //outlaw wins since either sheriff is dead and there are outlaws
                //or sheriff is dead and there is more than one renegade left
            }
        }
        else {
            //sheriff & deputy win since others are dead 
        }
    }
}
