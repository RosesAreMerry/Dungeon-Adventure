package model;

public abstract class DungeonCharacter {
    private String myName;
    private int myHitPoints;
    private int myDamageMin;
    private int myDamageMax;

    private int myAttackSpeed;
    private Double myHitChance;

    /**
     *initialize the fields
     */
    protected DungeonCharacter(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed){
    this.myName=theName;
    this.myHitPoints=theHitPoints;
    this.myHitChance=theHitChance;
    this.myDamageMin=theDamageMin;
    this.myDamageMax=theDamageMax;
    this.myAttackSpeed=theAttackSpeed;
    }

    /**
     * returns true or false if the hitpoint is less than or equal to 0
     * @return
     */
    private boolean isFainted() {
       if(myHitPoints<=0){
           return true;
       }
       else{
           return false;
       }
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

    /**
     * @param opponent
     * @return
     */
    public abstract int  attack(DungeonCharacter opponent);

}







