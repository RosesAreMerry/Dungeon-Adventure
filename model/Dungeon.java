package model;
public class Dungeon {

    private Room myHeroLocation;

    Dungeon(final Room theEntrance) {
        myHeroLocation = theEntrance;
    }
    public Room getCurrentRoom() {
        return myHeroLocation;
    }

    public void move(final Direction theDirection) {
        if (myHeroLocation.getDoor(theDirection) != null) {
            myHeroLocation = myHeroLocation.getDoor(theDirection);
        } else {
            throw new IllegalArgumentException("There is no door in that direction");
        }
    }

}
