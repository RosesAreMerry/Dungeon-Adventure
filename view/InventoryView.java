package view;

import model.Item;

public class InventoryView extends ConsoleView {

    public void showInventory(Item[] theInventory) {
        for (Item item : theInventory) {
            writeLine(item.getName() + "\n");
        }
        write("Enter the name of the item you want to use: ");



    }

}
