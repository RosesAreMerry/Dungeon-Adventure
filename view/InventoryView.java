package view;

import model.Item;

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
    public void showInventory(final Item[] theInventory) {
        final String[] options = new String[theInventory.length];

        for (int i = 0; i < theInventory.length; i++) {
            options[i] = theInventory[i].getName();
        }

        for (final String name : options) {
            writeLine(name);
        }

        final Item item = theInventory[askForOption(options, "Enter the name of the item you want to use: ")];

        myUseItem.accept(item);
    }

}
