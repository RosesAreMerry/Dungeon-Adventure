package model;

public abstract class Potion implements Item {
    private final String myName;
    public Potion(final String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }
    public abstract void use(Hero theHero);
}
