package model;

import java.util.Random;

/**
 * Theif Class represents a character in the game
 */
public class Thief extends Hero{
    private boolean surpriseAttack;
    private int originalHitPoints;
    public Thief(final String theName) {
        super(theName, 75, 0.8, 20, 40, 6, 0.4);
         surpriseAttack = false;
         originalHitPoints = getHitPoints();
    }

    /**
     * determines if the specialskill was used or not
     */
    private void specialSkill(){
        Random rand= new Random();
        if(rand.nextDouble()<=.4){
            surpriseAttack=true;
        }
    }

    /**
     *
     * @return boolean value ti check if the theif was caught
     */
    private boolean caught(){
        boolean caught;
        Random rand= new Random();
        if(rand.nextDouble()<=.2){
          caught=true;
        }
        else {
            caught = false;
        }
        return caught;
    }

    /**
     * attack method
     * @param theOpponent
     */
    @Override
    public  void attack(DungeonCharacter theOpponent){
        int numofAttack = Math.max(1, this.getAttackSpeed()/ theOpponent.getAttackSpeed());
        boolean isCaught = caught();
        if(isCaught){
            System.out.println(getName() +" " + "is caught");
            return;
        }
        if(surpriseAttack) {
            for(int i=0;i<numofAttack+1;i++){
                int damage = new Random().nextInt(getDamageMax() -getDamageMin() + 1) // refactor this lines
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
            }
        }
        else{
            for(int i=0;i<numofAttack;i++){
                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1) // refactor this lines
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
            }
        }
    }
 }

