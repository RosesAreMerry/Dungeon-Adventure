package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Potion item.
 */
public abstract class Potion implements Item, Serializable {
    @Serial
    private static final long serialVersionUID = -2424298242670619484L;
    private final String myName;

    /**
     * Constructs a Potion object with the specified name.
     *
     * @param theName the name of the potion
     */
    public Potion(final String theName) {
        myName = theName;
    }

    /**
     * Retrieves the name of the potion.
     *
     * @return the name of the potion
     */
    public String getName() {
        return myName;
    }

    /**
     * Uses the potion to provide some effect to the hero.
     * The specific effect is implemented in subclasses.
     *
     * @param theHero the hero using the potion
     */
    public abstract void use(Hero theHero);
}
