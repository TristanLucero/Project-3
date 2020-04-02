//CS 2365 OOP Spring 202 Section 002
//Peter Wharton
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Project_3_Game;
import java.util.*;
import java.io.*;


public class Game {

    private ArrayList<Character> playerList;
    private boolean onlySheriffDeputy;
    
    //Method to start the game off and get the number of players wanted 
    public int StartGame() {
        int numPlayers, startGame;
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players do you want to play with? (2-7) ");
        numPlayers = scan.nextInt();
        
        //Creates all 16 different characters possible
        Character.initializeAllCharacters();
        
        //Picks a character for each player 
        Character.assignCharacters(numPlayers);
        
        //Picks a role for each player 
        Character.assignRole(numPlayers);
        
        //Assigns the user to a player 
        Character.assignUser(numPlayers);
        
        //Sets up the playerList
        playerList = Character.GetPlayerList();
        
        
        //Get rid of this at some point? Goes to GUI?
        System.out.println("Do you want to start the game now? (1: Yes, 2: No) ");
        startGame = scan.nextInt();
        while (startGame != 1) {
            System.out.println("Enter 1 to start when ready: ");
            startGame = scan.nextInt();
        }
        
        return numPlayers;
    }
    
    //Method that will send the dice to the GUI 
    public static int [] SendDiceToGUI() {
        int [] info = {1 , 2};
        //something in here with dice
        
        return info;
    }
    
    //Method that checks if the current player is Human or AI 
    public boolean PlayerType(int index) {
        Character currChar = playerList.get(index);
        boolean isHuman = false;
        
        if (currChar.getRole() == 1) {
            isHuman = true;
        }
        
        return isHuman;
    }
    
    //Method for checking who is still alive 
    public int [] WhoAlive() {
        int [] alive = new int [] {0, 0, 0, 0};
        // index 0 = sheriff, 1 = deputy, 2 = outlaw, 3 = renegade
        // if value is 0 means dead, 1 if alive, > 1 more than one alive
        
        for (int i = 0; i < playerList.size(); ++i) {
            if (playerList.get(i).GetRole() == 4) {
                alive[3] ++;
            } 
            else if (playerList.get(i).GetRole() == 3) {
                alive[2] ++;
            }
            else if (playerList.get(i).GetRole() == 2) {
                alive[1] ++;
            }
            else {
                alive[0] ++;
            }
        }
        
        return alive;
    }
    
    
    
    
//Main Class -------------------------------------------------------------------
    
    public static void main(String[] args) {
        Game currGame = new Game();
        int currPlayer = 0, numPlayers = 0;
        int [] endGame = new int[] {1, 0, 0, 0}; 
        
        
        //Initalizes the game 
        numPlayers = currGame.StartGame();
      
        //Starts the loop for playing 
        while(endGame[0] >= 1) {
            
            //Check if only the sheriff and/or Deputy are alive to end the game
            endGame = currGame.WhoAlive();
            if ((endGame[0] >= 1 || endGame[1] >= 1) && (endGame[3] == 0 &&
                                                             endGame[4] == 0)) {
                break;
            }
            
            //Check if human or AI is the current player 
            if (currGame.PlayerType(currPlayer)) {
                //Start giving options for human to play in menu style (Send 
                //Strings to GUI?)
                //Ask if they want to start their turn with a roll
                //roll dice
                //give options based off what they rolled and what their special is 
                //end turn ask what they want to hit/do with dice 
                //check other players to see if there needs to be something that happens
                //check if someone is dead - if they died remove from player list
                    //numplayers is reduced by one
            }
            else {
                //Run through AI game play
                //start with a roll -> save to string to show on GUI?
                //check what player's roll is
                //check if special applies to rolls 
                //based on roll keep / reroll what goes to the goal 
                //keep rolling until no more rolls
                //end of turn apply dice as shown in accordance with goal
                //check others to see if their spcials need to change something
                //check if someone is dead - if they died remove from player list 
                    //numplayers is reduced by one
            }
            
            //used to cycle through the player list 
            if (currPlayer == numPlayers) {
                currPlayer = 0;
            }
            else {
                currPlayer ++;
            }
            
        }
        
        //Used for showing who the winner is 
        if (endGame[0] == 0 && endGame[1] == 0) {
            if (endGame[2] == 0 && endGame[3] == 1) {
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
