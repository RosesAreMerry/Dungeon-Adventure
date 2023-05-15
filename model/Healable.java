package model;

import java.util.Random;

public interface Healable {

    void heal();
//
//    public default void heal(int theOriginalHitPoints, int theNewHitPoints){
//        int range=theOriginalHitPoints-theNewHitPoints;
//        Random rand = new Random();
//        int healamount= rand.nextInt(range)+1;
//        theNewHitPoints+=healamount;
//    }
}
