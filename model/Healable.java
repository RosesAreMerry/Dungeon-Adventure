package model;

import java.util.Random;
/**
 * This is the healable inteface for other classes to use
 * @author malihahossain
 * @version 15th May 2023
 */
public interface Healable {

    void heal();

    int healAmount();

//    public default void heal(int theOriginalHitPoints, int theNewHitPoints){
//        int range=theOriginalHitPoints-theNewHitPoints;
//        Random rand = new Random();
//        int healamount= rand.nextInt(range)+1;
//        theNewHitPoints+=healamount;
//    }
}
