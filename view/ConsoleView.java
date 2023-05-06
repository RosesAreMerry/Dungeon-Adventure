package view;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

abstract class ConsoleView {

    private Scanner myReader;

    private Consumer<String> myCustomWriter;

    private Supplier<String> myCustomReader;

    protected ConsoleView() {
        myReader = new Scanner(System.in);
        myReader.useLocale(Locale.US);

        myCustomWriter = System.out::print;
    }

    protected ConsoleView(Consumer<String> theCustomWriter, Supplier<String> theCustomReader) {
        myCustomWriter = theCustomWriter;
        myCustomReader = theCustomReader;
    }

    protected String read() {
        if (myCustomReader != null) {
            return myCustomReader.get();
        }
        return myReader.next();
    }

    protected String readLine() {
        if (myCustomReader != null) {
            return myCustomReader.get();
        }
        return myReader.nextLine();
    }

    protected void write(String theMessage) {
        if (myCustomWriter != null) {
            myCustomWriter.accept(theMessage);
        }
    }

    protected void writeLine(String theMessage) {
        write(theMessage + "\n");
    }

    // Eventually this will be different from writeLine because it will
    // have different formatting.
    public void sendMessage(String theMessage) {
        write("\033[3m" + theMessage + "\033[0m\n");
    }

    protected int askForOption(String[] theOptions) {
        return askForOption(theOptions, "Enter your choice: ");
    }

    protected int askForOption(String[] theOptions, String thePrompt) {
        return askForOption(theOptions, thePrompt, "Invalid choice. Please enter a number or the name of an option: ");
    }

    protected int askForOption(String[] theOptions, String thePrompt, String theReprompt) {
        Function<String, Integer> isOption = (String s) -> {
            for (int i = 0; i < theOptions.length; i++) {
                if (theOptions[i].equalsIgnoreCase(s)) {
                    return i;
                }
            }
            return null;
        };

        Function<String, Integer> isNumber = (String s) -> {
            try {
                int choice = Integer.parseInt(s);
                if (choice > 0 && choice <= theOptions.length) {
                    return choice - 1;
                }
            } catch (NumberFormatException ignored) { }
            return null;
        };

        return askForOption(thePrompt, theReprompt, isOption, isNumber);
    }

    protected int askForOption(String prompt, String theReprompt, Function<String, Integer>... theValidators) {
        write(prompt);
        while (true) {
            String stringChoice = readLine();

            for (Function<String, Integer> validator : theValidators) {
                Integer choice = validator.apply(stringChoice);
                if (choice != null) {
                    return choice;
                }
            }

            write("\n" + theReprompt);
        }
    }

    public String promptUserChoice(String[] theOptions) throws IOException {
        return promptUserChoice(theOptions, true);
    }

    public String promptUserChoice(String[] theOptions, boolean theShowNumbers) throws IOException {
        if (theOptions.length == 0) {
            throw new IllegalArgumentException("Must have at least one option.");
        }

        for (int i = 0; i < theOptions.length; i++) {
            if (theShowNumbers) {
                write((i + 1) + ". ");
            }
            write(theOptions[i] + "\n");
        }

        return theOptions[askForOption(theOptions) - 1];
    }

    public void close() {
        myReader.close();
    }

}
