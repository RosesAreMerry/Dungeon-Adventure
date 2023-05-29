package model;

import java.io.Serializable;

public abstract class Potion implements Item, Serializable {
    private final String myName;
    public Potion(final String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }
    public abstract void use(Hero theHero);
}
