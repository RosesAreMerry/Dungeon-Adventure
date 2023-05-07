package model;

public abstract class DungeonCharacter {
    private String myName;
    private int myHitPoints;
    private int myDamageMin;
    private int myDamageMax;
    private int myAttackSpeed;
    private double myHitChance;

    protected DungeonCharacter(String theName, int theHitPoints, double theHitChance,
                               int theDamageMin, int theDamageMax, int theAttackSpeed){
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

    public boolean canAttack() {
        double random = Math.random();
        return random <= myHitChance;
    }
    public abstract void attack(DungeonCharacter theOpponent);

}







