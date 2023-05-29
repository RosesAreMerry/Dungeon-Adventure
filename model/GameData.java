package model;

import java.io.Serializable;

public class GameData implements Serializable {

    private Dungeon myDungeon;
    private Hero myHero;

    public GameData(Dungeon theDungeon, Hero theHero) {
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
