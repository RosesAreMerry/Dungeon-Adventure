package model;

import java.io.Serializable;

public class GameData implements Serializable {
    private final Dungeon myDungeon;
    private final Hero myHero;

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
