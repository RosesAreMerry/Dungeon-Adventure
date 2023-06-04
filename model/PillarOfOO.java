package model;

import java.io.Serial;
import java.io.Serializable;

public class PillarOfOO implements Item, Serializable {
    private final String myName;
    @Serial
    private static final long serialVersionUID = -3779876013390249890L;
    public PillarOfOO(final String theName) {
        this.myName = "Pillar of " + theName;
    }
    public String getName() {
        return myName;
    }
}
