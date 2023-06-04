package model;

import java.io.Serializable;
import java.util.Random;
/**
 * The Healable interface represents an character that can be healed.
 *
 * @author Maliha Hossain
 * @version 15th May 2023
 */
public interface Healable  {

    /**
     * Heals the character
     */
    void heal();

    /**
     * The amount of hit points to heal.
     * @return the number of hit points to restore.
     */
    int healAmount();

}
