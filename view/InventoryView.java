package view;

import model.Hero;
import model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A view that shows the inventory.
 *
 * @author Rosemary
 * @version May 7th 2023
 * @see view.ConsoleView
 * */
public class InventoryView extends ConsoleView {

    private final Consumer<Item> myUseItem;

    public InventoryView(final Consumer<Item> theUseItem) {
        super();
        myUseItem = theUseItem;
    }

    /**
     * A constructor that allows for custom input and output.
     * This is useful for testing.
     *
     * @param theCustomWriter A custom output method.
     * @param theCustomReader A custom input method.
     * */
    public InventoryView(final Consumer<String> theCustomWriter, final Supplier<String> theCustomReader, final Consumer<Item> theUseItem) {
        super(theCustomWriter, theCustomReader);

        myUseItem = theUseItem;
    }

    /**
     * Shows the inventory screen.
     *
     * @param theInventory The inventory to show.
     * */
    public void showInventory(final ArrayList<Item> theInventory) {
        final String[] options = new String[theInventory.size() + 1];
        final HashMap<String, Integer> itemCount = new HashMap<>();
        for (final Item item: theInventory) {
            final String itemName = item.getName();
            itemCount.put(itemName, itemCount.getOrDefault(itemName, 0) + 1);
        }

        for (final Map.Entry<String, Integer> entry: itemCount.entrySet()) {
            final String itemName = entry.getKey();
            final int itemQuantity = entry.getValue();
            final String itemDescription;
            if (itemQuantity > 1) {
                itemDescription = itemQuantity + " " + itemName + "s";
            } else {
                itemDescription = itemName;
            }
            writeLine(itemDescription);
        }
        writeLine("Close Inventory");
        for (int i = 0; i < theInventory.size(); i++) {
            options[i] = theInventory.get(i).getName();
        }
        options[theInventory.size()] = "Close Inventory";
        final int selectedItem = askForOption(options, "Enter the name of the item you want to use: ");
        if (selectedItem == theInventory.size()) {
            return;
        }
        final Item item = theInventory.get(selectedItem);
        myUseItem.accept(item);
    }
}
