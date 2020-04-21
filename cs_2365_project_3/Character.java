
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package cs_2365_project_3;

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
    
    private int maxHealth; //Maximum health points available to a given character (set at instantiation)
    
    private int ability; //Character special ability
    
    private int arrows; //Number of die player does not re-roll
    
    private boolean chiefsArrow; //Boolean value indicating whether or not the character holds the Indian Chief's arrow (expansion pack)
    
    private boolean helpedSheriff; //Indicates whether or not the character has helped the Sheriff by providing him beer (health)
    
    private boolean shotSheriff; //Indicates whether or not the character has shot the Sheriff
    


    public static ArrayList<Character> playerList = new ArrayList();
    
    public static ArrayList<Character> dupeList = new ArrayList(12);
    
    
    public Character(int healthPts, int abilityCode, String characterName){
        
        this.setUser(1);
        
        this.setLifeStatus(true);
        
        this.setHealth(healthPts);
        
        // Initial health value passed into constructor is the maximum health value that a character can have
        this.setMaxHealth(healthPts);  
        
        this.setAbility(abilityCode);
        
        this.setRole(0);
        
        this.setArrowCount(0);
        
        this.setName(characterName);
        
        this.setHelpedSheriff(false);
        
        this.setShotSheriff(false);
        
        
    }
    
   
    public void setUser(int user){
        
        this.user = user;
        
    }
    
    
    public void setLifeStatus(boolean alive){
        
        this.isAlive = alive;
        
    }
    
    
    public void setHealth(int healthPts){
        
        this.health = healthPts;
        
    }
    
    
    public void setMaxHealth(int maxHealthPts){
        
        
        this.maxHealth = maxHealthPts;
        
    }
    
    
    public void setAbility(int abilityCode){
        
        this.ability = abilityCode;
        
    }
    
    
    public void setRole(int roleType){
        
        this.role = roleType;
        
    }
    

    public void setArrowCount(int arrowCount){
        
        this.arrows = arrowCount;
        
    }
    
    
    public void setChiefsArrow(boolean hasChiefsArrow){
        
        this.chiefsArrow = hasChiefsArrow;
        
    }
    
    
    public void setName(String characterName){
        
        this.name = characterName;
        
    }
    
    
    public void setHelpedSheriff(boolean didHelp){
        
        this.helpedSheriff = didHelp;
        
    }
    
    
    public void setShotSheriff(boolean didShoot){
        
        this.shotSheriff = didShoot;
        
    }
    
    
    public int getUser(){
        
        return this.user;
        
    }
    
    
    public boolean getIsAlive(){
        
        return this.isAlive;
        
    }
    
    
    public String getName(){
        
        return this.name;
        
    }
    
    
    public int getRoleNum(){
        
        return this.role;
        
    }
    
    
    public int getHealth(){
        
        return this.health;
        
    }
    
    
    public int getMaxHealth(){
        
        return this.maxHealth;
        
    }
    
    
    public int getAbilityNum(){
        
        return this.ability;
        
    }
    
    
    public int getArrowCount(){
        
        return this.arrows;
        
    }
    
    
    public boolean hasChiefsArrow(){
        
        boolean hasTheArrow = false;
        
        if (this.chiefsArrow == true)
            hasTheArrow = true;
        
        return hasTheArrow;
            
        
    }
    
    
    public boolean getHelpedSheriff(){
        
        return this.helpedSheriff;
        
    }
    
    
    public boolean getShotSheriff(){
        
        return this.shotSheriff;
        
    }
    
    
    
    @Override
    public String toString(){
        
        return "Control: " + ((this.user == 0) ? "User":"CPU") + ", Name: " + this.name + ", Alive? " + this.isAlive + ", Health: " + this.health + ", Ability: " + 
                this.ability + ", Role: " + this.role + ", Arrow Count: " + this.arrows + ", Helped Sheriff? " + this.helpedSheriff;

    }
    
    // Constructor Arguments:
    // int healthPts, int abilityCode, String characterName
            
    public static void initializeAllCharacters(boolean expansionPack) //Initializes all player objects and assigns them to an ArrayList
    {
        Character char1 = new Character(8, 1, "Bart Cassidy");
        //Special Ability: Can take an arrow instead of losing a lifepoint only to shoot 1 or 2 
       

        Character char2 = new Character(9, 5, "Paul Regret");
        //Special Ability: Never lose lifepoints to the gattling gun 
        

        Character char3 = new Character(8, 6, "Pedro Ramirez");
        //Special Ability: When you lose a lifepoint, you can discard an arrow 
        

        Character char4 = new Character(8, 2, "Calamity Janet");
        //Special Ability: Can use a shoot 1 as a 2 and vice versa 
    

        Character char5 = new Character(9, 7, "Rose Doolan");
        //Special Ability: Can use shoot 1 and 2 for players sitting one place further 
        

        Character char6 = new Character(9, 3, "Jesse Jones");
        //Special Ability: If you have 4 or less lifepoints beer heals 2 lifepoints 
        

        Character char7 = new Character(7, 4, "Jourdonnais");
        //Special Ability: Never lose more than 1 lifepoint to indians 
        

        Character char8 = new Character(9, 8, "Vulture Sam");
        //Special Ability: When a player is eliminated you gain 2 lifepoints 
        


        Character.dupeList.add(char1);
        
        Character.dupeList.add(char2);
        
        Character.dupeList.add(char3);
        
        Character.dupeList.add(char4);
        
        Character.dupeList.add(char5);
        
        Character.dupeList.add(char6);
        
        Character.dupeList.add(char7);
        
        Character.dupeList.add(char8);
        
        
        if (expansionPack){
            
            Character charExp1 = new Character (9, 9, "Apache Kid");
            
            Character charExp2 = new Character(9, 10, "Bill Noface");
            
            Character charExp3 = new Character(8, 11, "Belle Star");
            
            Character charExp4 = new Character (7, 12, "Greg Digger");
            
            
            Character.dupeList.add(charExp1);
            
            Character.dupeList.add(charExp2);
            
            Character.dupeList.add(charExp3);
            
            Character.dupeList.add(charExp4);
            
        }
        
    }
    
    
    public static void assignCharacters(int numPlayers, boolean expansionPack) //Creates an ArrayList of randomly assigned characters, final version will pass in numPlayers
    {
        
        Random rand = new Random();
        
        int randNum; 
        
        int range;
        
        if (expansionPack)
            range = 12;
        else
            range = 8;
        
        for (int i = 0; i < numPlayers; i++) 
        {
            randNum = rand.nextInt(range); 
            
            playerList.add(dupeList.get(randNum));
            dupeList.remove(randNum); 
            
            range--; 
        }
         
    }
    
    
    public static void assignRole(int numPlayers) //Randomly assigns a role to each character, final version will pass in numPlayers
    {
        //Roles:   1: Sheriff;   2: Deputy;   3: Outlaw;   4: Renegade
        
        Character player; 
        
        ArrayList<Integer> roles;
        
        int range;
        
        Random rand = new Random();
        
        int randNum; 
        
        if (numPlayers == 3){
            roles = new ArrayList<>(Arrays.asList(2,3,4));
            
        }else if(numPlayers == 4)
            roles = new ArrayList<>(Arrays.asList(1,3,3,4));
        
        else if(numPlayers == 5)
            roles = new ArrayList<>(Arrays.asList(1,2,3,3,4)); 
        
        else if(numPlayers == 6)
            roles = new ArrayList<>(Arrays.asList(1,2,3,3,3,4)); 
        
        else if(numPlayers == 7)
            roles = new ArrayList<>(Arrays.asList(1,2,2,3,3,3,4)); 
        
        else if(numPlayers == 8)
            roles = new ArrayList<>(Arrays.asList(1,2,2,3,3,3,4,4));
        
        else {
            numPlayers = 4;
            roles = new ArrayList<>(Arrays.asList(1,3,3,4));
        }
            
        
        range = numPlayers; 
            
        for(int i = 0; i < numPlayers; i ++)
        {
            randNum = rand.nextInt(range);
                
            player = playerList.get(i);
                
            player.role = roles.get(randNum); 
            roles.remove(randNum);
                
            range--; 
                
            //Test case to display the randomly assigned charcters 
            //System.out.println(player.toString());
                
        }
        
    }
    
    
    public static void assignUser(int numPlayers){
        
        playerList.get(0).user = 0;
        
    }
    
    
    public void addLife() //Adds life to a specific character
    {
        
        this.health ++; 
        
    }
    
    
    public void loseLife() //Subtracts life from a specific charatcer 
    {
        
        this.health --; 
        
    }
    
    
    public int changeHealth(int amtChange){
        
        int newHealthValue;
        
        int lifeStatus = 1;  // 1 if Character object is "alive," 0 if the Character object is "dead"
        
        newHealthValue = this.getHealth() + amtChange;
        
        if (newHealthValue <= 0){
            this.setHealth(0);
            this.setLifeStatus(false);
            lifeStatus = 0;
        } else {
            if (newHealthValue > this.getMaxHealth())
                this.setHealth(getMaxHealth());
            else
                this.setHealth(newHealthValue);
        }
        
        return lifeStatus;   
        
    }
    
    
    public boolean isAlive() //Checks to see if a player is alive 
    {
        
        if (this.health == 0)
        {
            isAlive = false; 
        }
        else 
            isAlive = true; 
        
        return isAlive; 
        
    }
    
    
    public void changeArrows(int numArrows){
        
        this.arrows += numArrows;
        
    }
    
    
    public void shoot(Character shotPlayer){
        
        shotPlayer.loseLife();
        
    }
    
    
    public static double aliveToDeadRatio(){
        
        int i;
        
        int numAlive = 0;
        
        int numDead = 0;
        
        double lifeRatio;
        
        Character player;
        
        for (i = 0; i < Character.playerList.size(); i++){
            
            player = Character.playerList.get(i);
            
            if (player.getIsAlive() == true)
                numAlive += 1;
            
            else
                numDead += 1;
            
        }
        
        if (numDead == 0)
            lifeRatio = Double.MAX_VALUE;
        else
            lifeRatio = (double) numAlive/numDead;
        
        
        return lifeRatio;
        
    }
    
    
    public static int countAlive(){
        
        int i;
        
        int numAlive = 0;
        
        Character player;
        
        for (i = 0; i < Character.playerList.size(); i++){
            
            player = Character.playerList.get(i);
            
            if (player.getIsAlive() == true)
                numAlive += 1;
        }
        
        return numAlive;
        
    }
    
    
    public static int countDead(){
        
        int i;
        
        int numDead = 0;
        
        Character player;
        
        for (i = 0; i < Character.playerList.size(); i++){
            
            player = Character.playerList.get(i);
            
            if (player.getIsAlive() == false)
                numDead += 1;
        }
        
        
        return numDead;
        
    }
    
    
    public static double aliveToTotalRatio(){
        
        int i;
        
        int numAlive = 0;
        
        int numPlayers = Character.playerList.size();
        
        double lifeRatio;
        
        Character player;
        
        for (i = 0; i < numPlayers; i++){
            
            player = Character.playerList.get(i);
            
            if (player.getIsAlive() == true)
                numAlive += 1;
        }
        
        lifeRatio = (double) numAlive/numPlayers;
        
        
        return lifeRatio;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) //Driver for testing the class 
    {
        int i;
        
        int numPlayers = 8;
        
        Character player;
        
        Character.initializeAllCharacters(false);
         
        Character.assignCharacters(numPlayers, false);
        
        Character.assignRole(numPlayers);
        
        Character.assignUser(numPlayers);

        for (i = 0; i < numPlayers; i++){
            
            player = playerList.get(i);
            
            System.out.println(player.toString());
        }
        
        System.out.println(Character.countAlive());
        System.out.println(Character.countDead());
        
        for (i = 0; i < 3; i++){
            player = playerList.get(i);
            
            player.setLifeStatus(false);
        }
        
        System.out.println(Character.countAlive());
        System.out.println(Character.countDead());
        
        

    }

}
