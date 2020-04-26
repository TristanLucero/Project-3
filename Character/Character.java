
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Project-3.cs_2365_project_3;

import java.util.Random; 
import java.util.Arrays; 
import java.util.ArrayList;

/**
 *
 * @author lanegmacdougall
 */
public class Character 
{
     
    private int user;  // 0 for user, 1 for AI
    
    private boolean isAlive;  // True if character alive, false if character has been eliminated
    
    private boolean isZombieDead;  // True if eliminated as a zombie and permanently removed from the game; otherwise, false
    
    private boolean isZombie;  // True if character has re-entered game as a zombie (Undead or Alive expansion pack)
    
    private boolean isZombieMaster;  // True if character with Renegade role has been designated as Zombie Master (Undead or Alive expansion pack)
    
    private String name;  // Character name 
    
    private int role;  // The role assigned to a character, Sherrif, Deputy, Outlaw or Renegade
    
    private int health;  // Health points for each charatcer
    
    private int maxHealth;  // Maximum health points available to a given character (set at instantiation)
    
    private int ability;  // Character special ability
    
    private int arrows;  // Number of die player does not re-roll
    
    private boolean chiefsArrow;  // Boolean value indicating whether or not the character holds the Indian Chief's arrow (expansion pack)
    
    private boolean helpedSheriff;  // Indicates whether or not the character has helped the Sheriff by providing him beer (health)
    
    private boolean shotSheriff;  // Indicates whether or not the character has shot the Sheriff
    
    
    // ArrayList contains all available game characters; Characters to be used in 
    // gameplay are removed from dupeList and added to playerList
    public static ArrayList<Character> dupeList = new ArrayList<>(12);
    
    // ArrayList contains all Character objects used in gameplay
    public static ArrayList<Character> playerList = new ArrayList<>();
    
    // Constructor method - takes character health points, ability code, and the name of 
    // the character as input
    // Remaining attributes are set to default values
    public Character(int healthPts, int abilityCode, String characterName){
        
        this.setUser(1);
        
        this.setLifeStatus(true);
        
        this.setZombieDead(false);
        
        this.setZombie(false);
        
        this.setZombieMaster(false);
        
        this.setHealth(healthPts);
        
        // Initial health value passed into constructor is the maximum health value that a character can have
        this.setMaxHealth(healthPts);  
        
        this.setAbility(abilityCode);
        
        this.setRole(0);
        
        this.setArrowCount(0);
        
        this.setChiefsArrow(false);
        
        this.setName(characterName);
        
        this.setHelpedSheriff(false);
        
        this.setShotSheriff(false);
        
        
    }
    
   // Sets user attribute as either 0 for program user, or 1 for AI
    public void setUser(int user){
        
        this.user = user;
        
    }
    
    // Sets isAlive attribute to either true for alive, or false for dead
    public void setLifeStatus(boolean alive){
        
        this.isAlive = alive;
        
    }
    
    
    // Sets the isZombieDead attribute, which indicates that a "zombie" character
    // has been eliminated (second elimination in current gameplay), to true 
    // if the character is a zombie and has been eliminated, or false otherwise
    public void setZombieDead(boolean zombieDead){
        
        // Checks if argument is true, if yes, checks if the character is currently a zombie
        if (zombieDead == true){
            if (this.getZombieStatus() == true)
                
                // If both are true, set isZombieDead attribute to true
                this.isZombieDead = zombieDead;
            
        // Else, isZombieDead attribute is set to false
            else
                this.isZombieDead = false;
        } else
            this.isZombieDead = zombieDead;    
        
    }
    
    
    // Sets isZombie attribute according to argument
    public void setZombie(boolean zombieStatus){
        
        this.isZombie = zombieStatus;
        
    }
    
    
    // Sets isZombieMaster attribute according to argument
    public void setZombieMaster(boolean zombieMasterStatus){
        
        this.isZombieMaster = zombieMasterStatus;
        
    }
    
    
    // Sets Character object's health points; used during initialization to set
    // initial (and maximum) health value
    public void setHealth(int healthPts){
        
        this.health = healthPts;
        
    }
    
    
    // Sets the maximum health points a Character object can have according to the 
    // health points that a Character object is initialized with
    public void setMaxHealth(int maxHealthPts){
        
        
        this.maxHealth = maxHealthPts;
        
    }
    
    
    // Sets the ability code of a Character object
    public void setAbility(int abilityCode){
        
        this.ability = abilityCode;
        
    }
    
    
    // Sets the role code of a Character object
    public void setRole(int roleType){
        
        this.role = roleType;
        
    }
    
    
    // Sets the total arrow count of a Character object according to the argument 
    // passed into the function
    public void setArrowCount(int arrowCount){
        
        this.arrows = arrowCount;
        
    }
    
    // If argument hasChiefsArrow is true, the chief's arrow is given to the 
    // Character object, setting the object's chiefsArrow attribute to true and
    // adding 2 arrows to the object's character count
    //
    // If argument hasChiefsArrow is false, hasChiefsArrow attribute is set to false
    public void setChiefsArrow(boolean hasChiefsArrow){
        
        if (hasChiefsArrow == true){
            this.chiefsArrow = hasChiefsArrow;
        
            this.changeArrows(2);
        }
        
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
    
    
    public boolean getIsZombieDead(){
        
        return this.isZombieDead;
        
    }
    
    
    public boolean getZombieStatus(){
        
        return this.isZombie;
        
    }
    
    
    public boolean getZombieMasterStatus(){
        
        return this.isZombieMaster;
        
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
    
    
    public void changeHealth(int amtChange){
        
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
    
    
    public static int crownTheZombieMaster(int indexOfCurrPlayer){
        
        int renegadeCount = 0;
        
        int indexOfMaster = -1;
        
        ArrayList <Integer> renegadePositions = new ArrayList<>();
        
        Character player;
        
        int i;
        
        for (i = 0; i < Character.playerList.size(); i++){
            player = Character.playerList.get(i);
            if (player.getIsAlive() == true && player.getRoleNum() == 4){
                
                renegadeCount += 1;
                
                if (renegadeCount == 1)
                    indexOfMaster = i;
                
                renegadePositions.add(i);
                
            }
        }
        
        
        if (renegadeCount == 2){
            
            int numDist1, numDist2;
            
            int trueDist1, trueDist2;
            
            
            numDist1 = Math.abs(indexOfCurrPlayer - renegadePositions.get(0));
            
            if (numDist1 <= 4)
                trueDist1 = numDist1;
            else
                trueDist1 = 8 - numDist1;
            
            
            numDist2 = Math.abs(indexOfCurrPlayer - renegadePositions.get(1));
            
            
            
            if (numDist2 <= 4)
                trueDist2 = numDist2;
            else
                trueDist2 = 8 - numDist2;
            
            
            if ((trueDist1 < trueDist2) || (trueDist1 == trueDist2))
                indexOfMaster = renegadePositions.get(0);
            else
                indexOfMaster = renegadePositions.get(1);
        }
        
        
        if (renegadeCount > 0){
        
            player = Character.playerList.get(indexOfMaster);
            
            player.setZombieMaster(true);
            
        }
        
        return indexOfMaster;
        
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
        
        
        Character.playerList.get(0).setRole(4);
        Character.playerList.get(1).setRole(3);
        Character.playerList.get(2).setRole(2);
        Character.playerList.get(3).setRole(1);
        Character.playerList.get(4).setRole(2);
        Character.playerList.get(5).setRole(3);
        Character.playerList.get(6).setRole(4);
        Character.playerList.get(7).setRole(3);
        
        Character.assignUser(numPlayers);
        Character.assignRole(numPlayers);

        for (i = 0; i < numPlayers; i++){
            
            player = playerList.get(i);
            
            System.out.println(player.toString());
        }
        
        System.out.println();
        
        player = Character.playerList.get(3);
        
        player.setZombie(true);
        player.setZombieDead(true);
        System.out.println("Zombie dead: " + player.isZombieDead);
        

    }

}
