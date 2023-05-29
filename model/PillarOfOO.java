package model;

import java.io.Serializable;

public class PillarOfOO implements Item, Serializable {
    private final String myName;
    public PillarOfOO(final String theName) {
        this.myName = "Pillar of " + theName;
    }
    public String getName() {
        return myName;
    }
}
