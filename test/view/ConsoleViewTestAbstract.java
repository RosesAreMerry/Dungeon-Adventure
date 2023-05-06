package test.view;

import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class ConsoleViewTestAbstract {
    protected StringBuffer myMockedOutput = new StringBuffer();
    protected LinkedList<String> myMockedInput = new LinkedList<>();
    protected Consumer<String> myCustomWriter = (String s) -> myMockedOutput.append(s);
    protected Supplier<String> myCustomReader = () -> myMockedInput.poll();

    @BeforeEach
    void setUp() {
        myMockedOutput.setLength(0);
        myMockedInput.clear();
    }
}
