package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;
import view.RoomData;

import java.util.*;
import java.util.function.Consumer;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 */
public class DungeonAdventure {
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;
    private Dungeon myDungeon;
    private Hero myHero;
    private RoomData myCurrentRoomData;
    private Room myCurrentRoom;

    public DungeonAdventure() {
        myAdventureView = new AdventureView();
        myCurrentRoom = null;
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
        boolean playAgain = true;
        while (playAgain) {
            playGame();
            playAgain = promptPlayAgain();
        }
    }

    /**
     * Handles the overall game play.
     */
    private void playGame() {
        // Introduction and game setup
        displayIntroduction();
        myHero = createAdventurer();
        createDungeon();
        myAdventureView.sendMessage("\nYou walk into a dungeon.");

        // Main game loop
        do {
            displayCurrentRoom();
            displayOptions();
        } while (!myHero.isFainted());

        // Game conclusion
        displayGameResult();
        displayDungeon();
    }

    /**
     * Displays the introduction of the game to the user.
     */
    private void displayIntroduction() {
        myAdventureView.sendMessage("[Introduction placeholder]");
    }

    /**
     * Creates an adventurer character based on user's input.
     * Prompts the user for their name and character selection and returns the corresponding Hero object.
     *
     * @return the Hero character selected by the user.
     */
    private Hero createAdventurer() {
        final String name = myAdventureView.promptUserInput("\nWhat is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        myAdventureView.sendMessage("Pick your character: ");
        final String character = myAdventureView.promptUserChoice(new String[]{"Thief", "Warrior", "Priestess"}, false);
        return switch (character) {
            case "Thief" -> new Thief(name);
            case "Warrior" -> new Warrior(name);
            case "Priestess" -> new Priestess(name);
            default -> null;
        };
    }

    /**
     * Creates the dungeon for the game.
     */
    private void createDungeon() {
        final DungeonBuilder myDungeonBuilder = new DungeonBuilder();
        myDungeon = myDungeonBuilder.buildDungeon(10);
        setUpRoom(myDungeon.getCurrentRoom());
    }

    private String[] generateItems() {
        final int itemCount = (int) (Math.random() * 3);
        String[] myItems = new String[0];
        for (int i = 0; i < itemCount; i++) {
            final List<String> myItemList = new ArrayList<>(Arrays.asList(myItems));
            final Item item = generateRandomItem();
            myHero.addToInventory(item);
            // Add the new element
            myItemList.add(item.getName());
            // Convert the Arraylist to array
            myItems = myItemList.toArray(myItems);
        }
        return myItems;
    }

    private Item generateRandomItem() {
        final int itemType = (int) (Math.random() * 6);
        return switch (itemType) {
            case 0 -> new HealingPotion();
            case 1 -> new VisionPotion();
            case 2 -> new PillarOfOO("Abstraction");
            case 3 -> new PillarOfOO("Encapsulation");
            case 4 -> new PillarOfOO("Inheritance");
            case 5 -> new PillarOfOO("Polymorphism");
            default -> null;
        };
    }

    private String[] generateMonsters() {
        final int monsterCount = (int) (Math.random() * 3);
        String[] myMonsters = new String[0];
        for (int i = 0; i < monsterCount; i++) {
            final List<String> myMonsterList = new ArrayList<>(Arrays.asList(myMonsters));
            final Monster monster = generateRandomMonster();
            // Add the new element
            myMonsterList.add(monster.getName());
            // Convert the Arraylist to array
            myMonsters = myMonsterList.toArray(myMonsters);
        }
        return myMonsters;
    }

    private Monster generateRandomMonster() {
        final int monsterType = (int) (Math.random() * 3);
        final MonsterFactory monsterFactory = new MonsterFactory();
        return switch (monsterType) {
            case 0 -> monsterFactory.createMonster("Ogre");
            case 1 -> monsterFactory.createMonster("Gremlin");
            case 2 -> monsterFactory.createMonster("Skeleton");
            default -> null;
        };
    }
    
    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        myAdventureView.printRoom(myCurrentRoomData, null);
    }

    private void setRoomData(final RoomData theRoomData) {
        myCurrentRoomData = theRoomData;
    }

    private void setUpRoom(final Room theRoom) {
        myCurrentRoom = theRoom;
        final Direction[] doors = (myCurrentRoom.getDoors()).keySet().toArray(new Direction[0]);
        final String[] doorsString = Arrays.stream(doors)
                .map(direction -> direction.toString().substring(0, 1).toUpperCase()
                        + direction.toString().substring(1).toLowerCase())
                .toArray(String[]::new);
        myCurrentRoomData = new RoomData(doorsString,
                generateItems(),
                generateMonsters(), false, false);
        setRoomData(myCurrentRoomData);
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        myAdventureView.sendMessage("What do you want to do?");
        final List<String> options = new ArrayList<>();
        if (myCurrentRoomData.getMonsters().length == 0) {
            for (final String door : myCurrentRoomData.getDoors()) {
                final String doorTitleCase = door.substring(0, 1).toUpperCase() + door.substring(1).toLowerCase();
                options.add("Go " + doorTitleCase);
            }
        }
        options.add("See Inventory");
        for (final String monster : myCurrentRoomData.getMonsters()) {
            options.add("Battle " + monster);
        }
        final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
        myAdventureView.sendMessage("You chose: " + choice);
        handleAction(choice);
    }

    /**
     * Handles the user's chosen action.
     * Takes the user's selection and executes the corresponding logic in the game.
     *
     * @param theChoice the user's chosen action.
     */
    private void handleAction(final String theChoice) {
        final Map<String, Runnable> actions = new HashMap<>();
        if (theChoice.startsWith("Go")) { // handle moving to other rooms
            final String direction = theChoice.substring(3);
            actions.put("Go " + direction, () -> handleMoving(direction));
        }
        final String monster = theChoice.substring(7);
        actions.put("Battle " + monster, () -> handleCombat(monster));
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void handleCombat(final String theOpponent) {
        if (myCurrentRoomData.getMonsters() != null && myCurrentRoomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonster(theOpponent);
            combat.initiateCombat(myHero, opponent);
            myCurrentRoomData.removeMonsterFromRoom(theOpponent);
            setRoomData(myCurrentRoomData);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
            }
        }
    }

    private void handleMoving(final String theDirection) {
        final Direction enumDirection = Direction.valueOf(theDirection.toUpperCase());
        myDungeon.move(enumDirection);
        setUpRoom(myDungeon.getCurrentRoom());
        System.out.println(myHero.getName() + " has moved to the " + theDirection);
    }

    /**
     * Displays the final game result to the user.
     * Based on whether the user's character has fainted or won the game.
     */
    private void displayGameResult() {
        if (myHero.isFainted()) {
            myAdventureView.sendMessage("Game over!");
        } else {
            myAdventureView.sendMessage("You Win!");
        }
    }

    /**
     * Displays the entire dungeon layout to the user.
     */
    private void displayDungeon() {

    }

    /**
     * Prompts the user to play the game again or exit.
     *
     * @return true if the user wants to play again; false otherwise.
     */
    private boolean promptPlayAgain() {
        final String choice = myAdventureView.promptUserChoice(new String[] {"Play Again", "Exit"}, false);
        return choice.equals("Play Again");
    }

    /**
     * The main entry point of the game.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        new DungeonAdventure();
    }
}
