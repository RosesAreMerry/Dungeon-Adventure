package view;

/**
 * MockItem is a mock implementation of the Item interface.
 * It is used to test the InventoryView class.
 *
 * @author Rosemary
 * @version May 7th 2023
 * @see view.InventoryView
 */
class MockItem implements model.Item {

    private String myName;

    MockItem(String name) {
        myName = name;
    }

    @Override
    public String getName() {
        return myName;
    }
}
