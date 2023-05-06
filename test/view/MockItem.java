package test.view;

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
