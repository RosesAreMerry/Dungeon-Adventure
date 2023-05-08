package test.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AdventureView;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleViewTest extends ConsoleViewTestAbstract {

    AdventureView av;

    @BeforeEach
    void setUp() {
        av = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void sendMessage() {
        av.sendMessage("Hello World!");
        assertEquals("\033[1mHello World!\033[0m\n", myMockedOutput.toString());
    }

    @Test
    void testPromptUserChoice() {
        String[] options = {"A", "B", "C", "D", "E", "F"};
        myMockedInput.add("4");

        try {
            assertEquals("D", av.promptUserChoice(options));
            assertEquals("1. A\n2. B\n3. C\n4. D\n5. E\n6. F\nEnter your choice: ", myMockedOutput.toString());
        } catch (Exception e) {
            fail("Exception thrown");
        }

    }

    @Test
    void testPromptUserChoiceOneOption() {
        String[] options = {"A"};
        myMockedInput.add("1");

        try {
            assertEquals("A", av.promptUserChoice(options));
            assertEquals("1. A\nEnter your choice: ", myMockedOutput.toString());
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testPromptUserChoiceNoOptions() {
        String[] options = {};
        myMockedInput.add("1");

        try {
            av.promptUserChoice(options);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals("Must have at least one option.", e.getMessage());
        }
    }
}