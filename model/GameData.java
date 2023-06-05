package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the serialized data of a game session.
 * Stores the information about the dungeon and the hero (player).
 *
 * @author Chelsea Dacones
 */
public class GameData implements Serializable {
    private final Dungeon myDungeon;
    private final Hero myHero;
    @Serial
    private static final long serialVersionUID = 3702061674761280347L;

    /**
     * Constructs a GameData object with the specified dungeon and hero.
     *
     * @param theDungeon the dungeon of the game session
     * @param theHero the hero of the game session
     */
    public GameData(final Dungeon theDungeon, final Hero theHero) {
        myDungeon = theDungeon;
        myHero = theHero;
    }

    /**
     * Returns the dungeon associated with the game session.
     *
     * @return the dungeon object
     */
    public Dungeon getDungeon() {
        return myDungeon;
    }

    /**
     * Returns the hero associated with the game session.
     *
     * @return the hero object
     */
    public Hero getHero() {
        return myHero;
    }
}
