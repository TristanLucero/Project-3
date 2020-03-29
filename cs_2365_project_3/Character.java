
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_2365_project_3;

import java.util.Random; 
import java.util.Arrays; 
import java.util.ArrayList;

/**
 *
 * @author maxam
 */
public class Character 
{
     
    private int user; //0 for user, 1 for AI
    
    private boolean isAlive;
    
    private String name; //Character name 
    
    private int role; //The role assigned to a character, Sherrif, Deputy, Outlaw or Renegade
    
    private int health; //Health points for each charatcer 
    
    private int ability; //Character special ability
    
    private int arrows; //Number of die player does not re-roll
    
    //private ArrayList<Dice>dice;
    
    
    
    //private static int numPlayers = 8; //Number of players determined by the user 
    //private int randNum; 
    //private int range; 

    //Random rand = new Random(); 

    public static ArrayList<Character> playerList = new ArrayList();
    public static ArrayList<Character> dupeList = new ArrayList(16);
    
    public Character(int healthPts, int abilityCode, int roleType, String characterName){
        
        this.setHealth(healthPts);
        
        this.setAbility(abilityCode);
        
        this.setRole(roleType);
        
        this.setName(characterName);
    }
    
    
    public void setHealth(int healthPts){
        
        this.health = healthPts;
        
    }
    
    
    public void setAbility(int abilityCode){
        
        this.ability = abilityCode;
        
    }
    
    
    public void setRole(int roleType){
        
        this.role = roleType;
        
    }
    
    
    public void setName(String characterName){
        
        this.name = characterName;
        
    }
    
    
    @Override
    public String toString(){
        
        return "Name: " + this.name + ", Health: " + this.health + ", Ability: " + this.ability + ", Role: " + this.role;

    }
    
    public static void initializeAllCharacters() //Initializes all player objects and assigns them to an ArrayList
    {
        Character char1 = new Character(8, 1, 0, "Bart Cassidy");
        //Special Ability: Can take an arrow instead of losing a lifepoint only to shoot 1 or 2 
       

        Character char2 = new Character(9, 2, 0, "Paul Regret");
        //Special Ability: Never lose lifepoints to the gattling gun 

        Character char3 = new Character(8, 3, 0, "Black Jack");
        //Special Ability: Can re roll dynamite if 2 or less were rolled 
        

        Character char4 = new Character(8, 4, 0, "Pedro Ramirez");
        //Special Ability: When you lose a lifepoint, you can discard an arrow 
        

        Character char5 = new Character(8, 5, 0, "Calamity Jane");
        //Special Ability: Can use a shoot 1 as a 2 and vice versa 
    

        Character char6 = new Character(9, 6, 0, "Rose Doolan");
        //Special Ability: Can use shoot 1 and 2 for players sitting one place further 
     

        Character char7 = new Character(7, 7, 0, "El Gringo");
        //Special Ability: When a player causes you to lose one or more lifepoints they take an arrow 
        

        Character char8 = new Character(8, 8, 0, "Sid Ketchum");
        //Special Ability: At the start of your turn, any player of your choice including you gains 1 lifepoint 
        

        Character char9 = new Character(9, 9, 0, "Jesse Jones");
        //Special Ability: If you have 4 or less lifepoints beer heals 2 lifepoints 
        

        Character char10 = new Character(8, 10, 0, "Slab The Killer");
        //Special Ability: Can use beer as a shoot 1 or 2, takes 2 lifepoints away from targetted player 
        

        Character char11 = new Character(7, 11, 0, "Jourdonnais");
        //Special Ability: Never lose more than 1 lifepoint to indians 
        

        Character char12 = new Character(8, 12, 0, "Suzy Lafayette");
        //Special Ability: If you fail to roll any shoot 1 or 2, you gain 2 lifepoints 
        

        Character char13 = new Character(7, 13, 0, "Kit Carlson");
        //Special Ability: For each gattling you roll you can remove one arrow from any player, including yourself if 3 gattlings are rolled 
        

        Character char14 = new Character(9, 14, 0, "Vulture Sam");
        //Special Ability: When a player is eliminated you gain 2 lifepoints 
        

        Character char15 = new Character(8, 15, 0, "Lucky Duke");
        //Special Ability: You get one extra re-roll 
        

        Character char16 = new Character(8, 16, 0, "Willy The Kid");
        //Special Ability: Only need s2 gattling rolls to use the gattling gun 
        

        dupeList.add(char1);
        
        dupeList.add(char2);
        
        dupeList.add(char3);
        
        dupeList.add(char4);
        
        dupeList.add(char5);
        
        dupeList.add(char6);
        
        dupeList.add(char7);
        
        dupeList.add(char8);
        
        dupeList.add(char9);
        
        dupeList.add(char10);
        
        dupeList.add(char11);
        
        dupeList.add(char12);
        
        dupeList.add(char13);
        
        dupeList.add(char14);
        
        dupeList.add(char15);
        
        dupeList.add(char16);
    }

    public static void assignCharacters(int numPlayers) //Creates an ArrayList of randomly assigned characters, final version will pass in numPlayers
    {
        Character.initializeAllCharacters(); 
        
        Random rand = new Random();
        
        int randNum; 
        
        int range = 16; 
        
        for (int i = 0; i < numPlayers; i++) 
        {
            randNum = rand.nextInt(range); 
            
            playerList.add(dupeList.get(randNum));
            dupeList.remove(randNum); 
            
            range--; 
        }
        
        //Character.assignRole(); 
    }
    
    
    public static void assignRole(int numPlayers) //Randomly assigns a role to each character, final version will pass in numPlayers
    {
        //Roles: 1: Sheriff 2: Deputy 3: Outlaw 4: Renegade
        
        Character player; 
        
        ArrayList<Integer> roles;
        
        int range;
        
        Random rand = new Random();
        
        int randNum; 
        
        if(numPlayers == 4)
            roles = new ArrayList<Integer>(Arrays.asList(1,3,3,4));
        
        else if(numPlayers == 5)
            roles = new ArrayList<Integer>(Arrays.asList(1,2,3,3,4)); 
        
        else if(numPlayers == 6)
            roles = new ArrayList<Integer>(Arrays.asList(1,2,3,3,3,4)); 
        
        else if(numPlayers == 7)
            roles = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,4)); 
        
        else if(numPlayers == 8)
            roles = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,4,4)); 
        
        range = numPlayers; 
            
        for(int i = 0; i < numPlayers; i ++)
        {
            randNum = rand.nextInt(range);
                
            player = playerList.get(i);
                
            player.role = roles.get(randNum); 
            roles.remove(randNum);
                
            range--; 
                
            //Test case to display the randomly assigned charcters 
            System.out.println(player.toString());
                
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) //Driver for testing the class 
    {
        //Character test = new Character();
        System.out.println("         =========Player List=========");
        Character.initializeAllCharacters();
        test.create();

    }

}
