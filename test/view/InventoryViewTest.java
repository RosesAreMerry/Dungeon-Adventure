package view;

import model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.InventoryView;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class InventoryViewTest extends ConsoleViewTestAbstract {

    StringBuffer myItemsOutput = new StringBuffer();
    Consumer<Item> myMockedUseItem = (Item i) -> myItemsOutput.append(i.getName());

    Item[] myTestItems = {  new MockItem("A"), new MockItem("B"), new MockItem("C"), new MockItem("D"),
                            new MockItem("E"), new MockItem("F"), new MockItem("G"), new MockItem("H"),
                            new MockItem("I"), new MockItem("J"), new MockItem("K"), new MockItem("L")};

    InventoryView iv;

    @BeforeEach
    void setUp() {
        myItemsOutput.setLength(0);
        iv = new InventoryView(myCustomWriter, myCustomReader, myMockedUseItem);
    }

    @Test
    void showInventoryTestNumber() {
        Item[] items = {new MockItem("A"), new MockItem("B"), new MockItem("C")};
        myMockedInput.add("2");
        iv.showInventory(items);

        assertEquals("A\nB\nC\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("B", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestOption() {
        Item[] items = {new MockItem("A"), new MockItem("B"), new MockItem("C")};
        myMockedInput.add("B");
        iv.showInventory(items);

        assertEquals("A\nB\nC\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("B", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestOptionManyOptions() {
        myMockedInput.add("L");
        iv.showInventory(myTestItems);

        assertEquals("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("L", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestNumberManyOptions() {
        myMockedInput.add("12");
        iv.showInventory(myTestItems);

        assertEquals("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("L", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestFirstOptionManyOptions() {
        myMockedInput.add("A");
        iv.showInventory(myTestItems);

        assertEquals("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("A", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestFirstNumberManyOptions() {
        myMockedInput.add("1");
        iv.showInventory(myTestItems);

        assertEquals("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nEnter the name of the item you want to use: ", myMockedOutput.toString());
        assertEquals("A", myItemsOutput.toString());
    }

    @Test
    void showInventoryTestBadInput() {
        myMockedInput.add("bad input");
        myMockedInput.add("B");
        iv.showInventory(myTestItems);

        assertEquals("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nEnter the name of the item you want to use: \nInvalid choice. Please enter a number or the name of an option: ", myMockedOutput.toString());
        assertEquals("B", myItemsOutput.toString());
    }
}