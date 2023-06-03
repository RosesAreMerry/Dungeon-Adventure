package model;

import java.io.Serializable;
import java.util.Random;
/**
 * This is the healable inteface for other classes to use
 * @author malihahossain
 * @version 15th May 2023
 */
public interface Healable  {

    void heal();

    int healAmount();

}
