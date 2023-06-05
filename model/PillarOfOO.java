package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Pillar of OO in the game.
 *
 * @author Chelsea Dacones
 */
public class PillarOfOO implements Item, Serializable {
    @Serial
    private static final long serialVersionUID = -3779876013390249890L;
    private final String myName;

    /**
     * Constructs a PillarOfOO object with the specified name.
     *
     * @param theName the name of the pillar
     */
    public PillarOfOO(final String theName) {
        this.myName = "Pillar of " + theName;
    }

    /**
     * Retrives the name of the pillar.
     *
     * @return the name of the pillar
     */
    public String getName() {
        return myName;
    }
}
