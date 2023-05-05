package test.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AdventureView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class AdventureViewTest {

    StringBuffer myMockedOutput = new StringBuffer();
    LinkedList<String> myMockedInput = new LinkedList<>();
    Consumer<String> myCustomWriter = (String s) -> myMockedOutput.append(s);
    Supplier<String> myCustomReader = () -> myMockedInput.poll();

    AdventureView av;

    @BeforeEach
    void setUp() {
        myMockedOutput.setLength(0);
        myMockedInput.clear();
        av = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void sendMessage() {
        av.sendMessage("Hello World!");
        assertEquals("\033[3mHello World!\033[0m\n", myMockedOutput.toString());
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

    @Test
    void printRoom() {
    }

    @Test
    void printDungeon() {
    }
}