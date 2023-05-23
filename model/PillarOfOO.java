package model;

public class PillarOfOO implements Item{
    private final String myName;
    public PillarOfOO(final String theName) {
        this.myName = "Pillar of " + theName;
    }
    public String getName() {
        return myName;
    }
}
