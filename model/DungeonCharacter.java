package model;

import java.util.Random;

public abstract class DungeonCharacter {
    private String myName;
    private int myHitPoints;
    private int myDamageMin;
    private int myDamageMax;
    private int myAttackSpeed;
    private double myHitChance;

    /**
     *initialize the fields
     */
    protected DungeonCharacter(final String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed) {
        this.myName = theName;
        this.myHitPoints = theHitPoints;
        this.myHitChance = theHitChance;
        this.myDamageMin = theDamageMin;
        this.myDamageMax = theDamageMax;
        this.myAttackSpeed = theAttackSpeed;
    }
    
    /**
     * returns true or false if the hitpoint is less than or equal to 0
     * @return
     */

   public boolean isFainted() {
       if(myHitPoints<=0){
           return true;
       }
       else{
           return false;
       }
    }
    public int getNumAttacks(DungeonCharacter opponent) {
        int numberofattack=0;
        if(this.getAttackSpeed()>= opponent.getAttackSpeed()){
            numberofattack=this.getAttackSpeed()/ opponent.getAttackSpeed();
        }
        else{
            numberofattack= opponent.getAttackSpeed()/this.getAttackSpeed();
        }
        return numberofattack;
    }

    public String getName() {
       return myName;
    }
    public void setName(String theName) {
        this.myName = theName;
    }

    public int getHitPoints() {
      return myHitPoints;
    }

    public void setHitPoints(int theHitPoints) {
        this.myHitPoints=theHitPoints;
    }

    public int getAttackSpeed() {
        return myAttackSpeed;
    }

    public int getDamageMax() {
        return myDamageMax;
    }  


    public Double getMyHitChance(){
        return myHitChance;
    }
    public int getDamageMin() {
        return myDamageMin;
    }

    /**
     * Decides if character can attack based on chance to hit.
     *
     * @return true if character can attack opponent; otherwise, false
     */
    public boolean canAttack() {
        final double randomValue = new Random().nextDouble();
        return randomValue < myHitChance;
    }

    /**
     * @param theOpponent
     * @return
     */
    public abstract void attack(DungeonCharacter theOpponent);

}







