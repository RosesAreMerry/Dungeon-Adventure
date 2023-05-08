package model;

public abstract class Potion implements Item {
    private final String myName;
    public Potion(String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }
    public abstract void use(Hero theHero);
}
