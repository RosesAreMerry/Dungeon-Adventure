package model;

public abstract class DungeonCharacter {
    private String myName;
    private int myHitPoints;
    private int myDamageMin;
    private int myDamageMax;
    private int myAttackSpeed;
    double myHitChance;

    /**
     *initialize the fields
     */
    public DungeonCharacter(String theName, int theHitPoints, Double theHitChance, int theDamageMin,
                            int theDamageMax, int theAttackSpeed) {
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
        if (myHitPoints <= 0){
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
    public int getHitPoints() {
        return myHitPoints;
    }
    public void setHitPoints(int theHitPoints) {
        myHitPoints = theHitPoints;
    }
    public int getAttackSpeed() {
        return myAttackSpeed;
    }

    public int getDamageMax() {
        return myDamageMax;
    }

    public double getMyHitChance() {
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
        double random = Math.random();
        return random <= myHitChance;
    }
    public abstract void attack(DungeonCharacter theOpponent);
}







