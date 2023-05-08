package controller;

import model.Dungeon;
import model.Hero;
import model.Theif;
import view.AdventureView;

public class DungeonAdventure {
    private Dungeon myDungeon;
    private Hero myHero;
    private AdventureView myAdventureView;

    public static void main(String[] theArgs) {
        AdventureView view = new AdventureView();
        String name = view.promptUserInput("What is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        view.sendMessage("Pick your character: ");
        String character = view.promptUserChoice(new String[]{"Theif", "Warrior", "Priestess"}, false);

        view.sendMessage("You walk into a dungeon.");

        view.printRoom("The door slams shut behind you.", new String[]{"North", "South"}, new String[]{"health potion"});

        view.sendMessage("What do you want to do?");

        String choice = view.promptUserChoice(new String[]{"Go North", "Go South", "See Inventory", "Look Around"}, false);

        view.sendMessage("You chose: " + choice);
    }

    private void winGame() {

    }

    private void loseGame() {

    }
}
