package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;
import view.RoomData;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 */
public class DungeonAdventure implements Serializable {
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;
    private Dungeon myDungeon;
    private Hero myHero;
    private RoomData myCurrentRoomData;
    private Room myCurrentRoom;
    private List<Monster> monsters;
    private GameData myGameData;
    private static final long serialVersionUID = 1L;

    public DungeonAdventure() throws InterruptedException {
        myAdventureView = new AdventureView();
        myCurrentRoom = null;
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
            if (item instanceof PillarOfOO) {
                myAdventureView.sendMessage("Pillars cannot be used");
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
        monsters = new ArrayList<>(); // maliha
        boolean playAgain = true;
        while (playAgain) {
            playGame();
            playAgain = promptPlayAgain();
        }
    }

    /**
     * Handles the overall game play.
     */
    private void playGame() throws InterruptedException {
        // Introduction and game setup
        displayIntroduction();
        String choice = myAdventureView.promptUserChoice(new String[]{"Load Game", "New Game"}, true);
        if (choice.equals("Load Game")) {
            loadSavedGame();
        }  else {
            myHero = createAdventurer();
            createDungeon();
        }
        myGameData = new GameData(myDungeon, myHero);
        myAdventureView.sendMessage("\nYou walk into a dungeon.");

        // Main game loop
        do {
//            Thread.sleep(1000);
            displayCurrentRoom();
//            Thread.sleep(2000);
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

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        myAdventureView.printRoom(myCurrentRoomData, null);
        myAdventureView.sendMessage(myCurrentRoom.toString());
    }

    private void setRoomData(final RoomData theRoomData) {
        myCurrentRoomData = theRoomData;
    }

    private void setUpRoom(final Room theRoom) {
        myCurrentRoom = theRoom;
//        final Direction[] doors = (myCurrentRoom.getDoors()).keySet().toArray(new Direction[0]);
//        final String[] doorsString = Arrays.stream(doors)
//                .map(direction -> direction.toString().substring(0, 1).toUpperCase()
//                        + direction.toString().substring(1).toLowerCase())
//                .toArray(String[]::new);
//        final String[] myItems = myCurrentRoom.getItems().stream().map(Item::getName).toArray(String[]::new);
//        myCurrentRoomData = new RoomData(doorsString,
//                myItems, new String[]{}, false, false);
//        myHero.addToInventory(myCurrentRoom.getItems());
        myCurrentRoomData = new RoomData(myCurrentRoom);
        setRoomData(myCurrentRoomData);
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        if (myCurrentRoomData.getMonsters() != null) {
            for (final String monster : myCurrentRoomData.getMonsters()) {
                handleCombat(monster);
            }
        } if (!myHero.isFainted()) {
            myAdventureView.sendMessage("What do you want to do?");
            final List<String> options = new ArrayList<>();
            if (myCurrentRoomData.getMonsters().length == 0) {
                for (final String door : myCurrentRoomData.getDoors()) {
                    final String doorTitleCase = door.substring(0, 1).toUpperCase() + door.substring(1).toLowerCase();
                    options.add("Go " + doorTitleCase);
                }
                options.add("Look Around");
            }
            if (myHero.getMyInventory().size() > 0) {
                options.add("See Inventory");
            }
            options.add("Save Game");
//        for (final String monster : myCurrentRoomData.getMonsters()) {
//            options.add("Battle " + monster);
//        }
            final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
            myAdventureView.sendMessage("You chose: " + choice);
            handleAction(choice);
        }
    }

    /**
     * Handles the user's chosen action.
     * Takes the user's selection and executes the corresponding logic in the game.
     *
     * @param theChoice the user's chosen action.
     */
    private void handleAction(final String theChoice) {
        final Map<String, Runnable> actions = new HashMap<>();
        GameData gameData = new GameData(myDungeon, myHero);
        if (theChoice.startsWith("Go")) { // handle moving to other rooms
            final String direction = theChoice.substring(3);
            actions.put("Go " + direction, () -> handleMoving(direction));
        }
//        final String monster = theChoice.substring(7);
//        actions.put("Battle " + monster, () -> handleCombat(monster));
        actions.put("Look Around", this::lookAroundAction);
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        actions.put("Save Game", () -> GameSerializationUtil.saveGame("DA", gameData));
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    /**
     * Edited this-added the opponent to the monsters array
     * need ti make a monster array here
     * @param theOpponent
     */
    private void handleCombat(final String theOpponent) {
        if (myCurrentRoomData.getMonsters() != null && myCurrentRoomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            //making opponent a global variable to be able to access it for serialization
             final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            combat.initiateCombat(myHero, opponent);
            myCurrentRoomData.removeMonsterFromRoom(theOpponent);
            setRoomData(myCurrentRoomData);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
            } else {
                myAdventureView.sendMessage("You were defeated by the " + theOpponent + "!");
            }
            // add the opponent to an array
           monsters.add(opponent);
        }
    }

    private void handleMoving(final String theDirection) {
        final Direction enumDirection = Direction.valueOf(theDirection.toUpperCase());
        myDungeon.move(enumDirection);
        setUpRoom(myDungeon.getCurrentRoom());
        System.out.println("You have moved to the " + theDirection);
    }

    private void lookAroundAction() {
        if (myCurrentRoomData.getItems().length > 0) {
            final StringBuilder sb = new StringBuilder();
            myAdventureView.buildList(sb,
                    myCurrentRoomData.getItems(),
                    "You acquired ",
                    "You acquired ",
                    "!",
                    true);
            myAdventureView.sendMessage(String.valueOf(sb));
            myHero.addToInventory(myCurrentRoom.getItems());
            myCurrentRoom.getItems().clear();
        }
        myCurrentRoomData = new RoomData(myCurrentRoomData.getFlavor(), myCurrentRoomData.getDoors(),
                new String[]{}, new String[]{}, myCurrentRoomData.isPit(), myCurrentRoomData.isExit());
        setRoomData(myCurrentRoomData);
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
    public void saveGameState() {
        String filename="DungeonAdventure.ser";
        try {
            //saving the object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.myHero);
            out.writeObject(monsters); // the monsters hero battles
            out.close();
            System.out.println("object has been serialized .");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }
    public void loadGameState() {
        this.myHero=null;
        //this.opponent=null;
        try {
            FileInputStream file = new FileInputStream("DungeonAdventure.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            this.myHero= (Hero) in.readObject();
            monsters = (ArrayList) in.readObject();
            in.close();
            file.close();
            System.out.println("Game state loaded successfully.");
            System.out.println("Serialized data: " + myHero);
            for(int i=0; i<monsters.size();i++) {
                System.out.println("Serialized data" + monsters.get(i));
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game state: " + e.getMessage());

        }
    }

    public void loadSavedGame() {
        myGameData = GameSerializationUtil.loadGame("DA");
        if (myGameData != null) {
            myCurrentRoomData = new RoomData(myGameData.getDungeon().getCurrentRoom());
            myCurrentRoom = myGameData.getDungeon().getCurrentRoom();
            setRoomData(myCurrentRoomData);
            myHero = myGameData.getHero();
            myAdventureView.sendMessage("Game loaded successfully!");
            myAdventureView.sendMessage("\nCurrent hit points: " + myHero.getHitPoints());
        } else {
            myAdventureView.sendMessage("Failed to load game.");
        }
    }

    /**
     * The main entry point of the game.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) throws InterruptedException {
        new DungeonAdventure();
    }
}
