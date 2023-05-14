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

    private final String myName;

    MockItem(final String theName) {
        myName = theName;
    }

    @Override
    public String getName() {
        return myName;
    }
}
