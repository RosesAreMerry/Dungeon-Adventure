package view;

public class RoomData {
    private final String myFlavorText;
    private final String[] myDoors;
    private final String[] myItems;
    private final String[] myMonsters;

    private final boolean myIsPit;

    private final boolean myIsExit;

    public RoomData(final String theFlavorText,
                    final String[] theDoors,
                    final String[] theItems,
                    final String[] theMonsters,
                    final boolean theIsPit,
                    final boolean theIsExit) {
        myFlavorText = theFlavorText;
        myDoors = theDoors;
        myItems = theItems;
        myMonsters = theMonsters;
        myIsPit = theIsPit;
        myIsExit = theIsExit;
    }

    public RoomData(final String[] theDoors,
                    final String[] theItems,
                    final String[] theMonsters,
                    final boolean theIsPit,
                    final boolean theIsExit) {
        this("You are in a room.", theDoors, theItems, theMonsters, theIsPit, theIsExit);
    }

    public String[] getDoors() {
        return myDoors;
    }

    public String[] getItems() {
        return myItems;
    }

    public String getFlavor() {
        return myFlavorText;
    }

    public String[] getMonsters() {
        return myMonsters;
    }

    public boolean isPit() {
        return myIsPit;
    }

    public boolean isExit() {
        return myIsExit;
    }
}
