package model;

import java.io.Serial;
import java.io.Serializable;

public class GameData implements Serializable {
    private final Dungeon myDungeon;
    private final Hero myHero;
    @Serial
    private static final long serialVersionUID = 3702061674761280347L;
    public GameData(final Dungeon theDungeon, final Hero theHero) {
        myDungeon = theDungeon;
        myHero = theHero;
    }

    public Dungeon getDungeon() {
        return myDungeon;
    }

    public Hero getHero() {
        return myHero;
    }
}
