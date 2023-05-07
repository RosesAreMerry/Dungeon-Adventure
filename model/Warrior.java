package model;

public class Warrior extends Hero{

    public Warrior(String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
    }

    @Override
    public int attack(DungeonCharacter opponent) {
        return 0;
    }
}
