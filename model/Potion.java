package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Potion implements Item, Serializable {
    private final String myName;
    @Serial
    private static final long serialVersionUID = -2424298242670619484L;
    public Potion(final String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }
    public abstract void use(Hero theHero);
}
