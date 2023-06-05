package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleViewTest extends ConsoleViewTestAbstract {

    AdventureView myAdventureView;

    @BeforeEach
    void setUp() {
        myAdventureView = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void sendMessage() {
        myAdventureView.sendMessage("Hello World!");
        assertEquals("\033[1mHello World!\033[0m\n", myMockedOutput.toString());
    }

    @Test
    void testPromptUserChoice() {
        final String[] options = {"A", "B", "C", "D", "E", "F"};
        myMockedInput.add("4");

        try {
            assertEquals("D", myAdventureView.promptUserChoice(options));
            assertEquals("1. A\n2. B\n3. C\n4. D\n5. E\n6. F\nChoose an option: ", myMockedOutput.toString());
        } catch (final Exception theException) {
            fail("Exception thrown");
        }

    }

    @Test
    void testPromptUserChoiceOneOption() {
        final String[] options = {"A"};
        myMockedInput.add("1");

        try {
            assertEquals("A", myAdventureView.promptUserChoice(options));
            assertEquals("1. A\nChoose an option: ", myMockedOutput.toString());
        } catch (final Exception theException) {
            fail("Exception thrown");
        }
    }

    @Test
    void testPromptUserChoiceNoOptions() {
        final String[] options = {};
        myMockedInput.add("1");

        try {
            myAdventureView.promptUserChoice(options);
            fail("Exception not thrown");
        } catch (final Exception theException) {
            assertEquals("Must have at least one option.", theException.getMessage());
        }
    }
}