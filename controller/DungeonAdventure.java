package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DungeonAdventure {
    private Dungeon myDungeon;
    private Hero myHero;
    private final AdventureView myAdventureView;

    private final InventoryView myInventoryView;

    public DungeonAdventure() {
        myAdventureView = new AdventureView();
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
        playGame();
    }

    private void playGame() {
        // Introduction and game setup
        displayIntroduction();
        myHero = createAdventurer();
        createDungeon();
        myHero.addToInventory(new HealingPotion());
        myAdventureView.sendMessage("\nYou walk into a dungeon.");
        myAdventureView.printRoom("The door slams shut behind you.", new String[]{"North", "South"}, new String[]{"Health Potion"});

        // Main game loop
        do {
            displayCurrentRoom();
            displayOptions();

        } while (!myHero.isFainted());

        // Game conclusion
        displayGameResult();
        displayDungeon();
    }
    private void displayIntroduction() {
    }
    private Hero createAdventurer() {
        final String name = myAdventureView.promptUserInput("\nWhat is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        myAdventureView.sendMessage("Pick your character: ");
        final String character = myAdventureView.promptUserChoice(new String[]{"Thief", "Warrior", "Priestess"}, false);
        switch (character) {
            case "Thief" -> {
                return new Thief(name);
            }
            case "Warrior" -> {
                return new Warrior(name);
            }
            case "Priestess" -> {
                return new Priestess(name);
            }
        }
        return null;
    }
    private void createDungeon() {

    }
    private void displayCurrentRoom() {

    }
    private void displayOptions() {
        myAdventureView.sendMessage("What do you want to do?");
        final String choice = myAdventureView.promptUserChoice(new String[]{"Go North", "Go South", "See Inventory", "Look Around"}, true);
        myAdventureView.sendMessage("You chose: " + choice + "\n");
        handleAction(choice);
    }
    private void handleAction(final String theChoice) {
        final Map<String, Runnable> actions = new HashMap<>();
        actions.put("Go North", () -> myDungeon.move("North"));
        actions.put("Go South", () -> myDungeon.move("South"));
        actions.put("Go East", () -> myDungeon.move("East"));
        actions.put("Go West", () -> myDungeon.move("West"));
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        actions.put("Look Around", () -> { /* handle Look Around action */ });
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void displayGameResult() {
        if (myHero.isFainted()) {
            myAdventureView.sendMessage("Game over!");
        } else {
            myAdventureView.sendMessage("You Win!");
        }
    }

    private void displayDungeon() {

    }
    private void winGame() {
    }

    private void loseGame() {
    }

    public static void main(final String[] theArgs) {
        new DungeonAdventure();
    }
}
