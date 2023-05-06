package controller;

import model.Dungeon;
import model.Hero;
import model.Potion;
import view.AdventureView;
import view.InventoryView;

public class DungeonAdventure {
    private Dungeon myDungeon;
    private Hero myHero;
    private AdventureView myAdventureView;
    private InventoryView myInventoryView;

    public DungeonAdventure() {
        myInventoryView = new InventoryView(item -> {
            if (item instanceof Potion potion) {
                potion.use(myHero);
            } else {
                System.out.println("You cannot use this item");
            }
        });
    }
    public void winGame() {

    }
    public void loseGame() {

    }
}
