package view;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AdventureView extends ConsoleView {

    public AdventureView() {
        super();
    }

    public AdventureView(Consumer<String> theCustomWriter, Supplier<String> theCustomReader) {
        super(theCustomWriter, theCustomReader);
    }

    public void printRoom(String theRoom) {
        // TODO Write this method
    }

    public void printDungeon(String theDungeon) {
        // TODO Write this method
    }

}
