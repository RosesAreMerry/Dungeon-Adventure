package view;

import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A view that uses the console to show information to the user and get input from the user.
 * This class is abstract because it is not meant to be instantiated directly.
 *
 * @author Rosemary
 * @version May 7th 2023
 * @see view.AdventureView
 * @see view.InventoryView
 * @see view.CombatView
 */
abstract class ConsoleView {

    private static Scanner myDefaultReader;

    private static Consumer<String> myWriter;

    private static Supplier<String> myReader;

    protected ConsoleView() {
        myDefaultReader = new Scanner(System.in);
        myDefaultReader.useLocale(Locale.US);

        myWriter = System.out::print;
    }

    /**
     * A constructor that allows for custom input and output.
     * This is useful for testing.
     *
     * @param theCustomWriter A custom output method.
     * @param theCustomReader A custom input method.
     * */
    protected ConsoleView(Consumer<String> theCustomWriter, Supplier<String> theCustomReader) {
        myWriter = theCustomWriter;
        myReader = theCustomReader;
    }

    /**
     * Writes a bold message to the output, followed by a newline.
     * */
    public void sendMessage(String theMessage) {
        write("\033[1m" + theMessage + "\033[0m\n");
    }

    /**
     * Shows a list of options to the user and asks for their choice.
     *
     * @param theOptions The options to show to the user.
     *                   The options are numbered automatically.
     *
     * @return The name of the option chosen by the user.
     * */
    public String promptUserChoice(String[] theOptions) {
        return promptUserChoice(theOptions, true);
    }

    /**
     * Shows a list of options to the user and asks for their choice.
     *
     * @param theOptions The options to show to the user.
     *
     * @param theShowNumbers Whether to show numbers next to the options.
     *
     * @return The name of the option chosen by the user.
     * */
    public String promptUserChoice(String[] theOptions, boolean theShowNumbers) {
        if (theOptions.length == 0) {
            throw new IllegalArgumentException("Must have at least one option.");
        }

        for (int i = 0; i < theOptions.length; i++) {
            if (theShowNumbers) {
                write((i + 1) + ". ");
            }
            write(theOptions[i] + "\n");
        }

        return theOptions[askForOption(theOptions)];
    }

    /**
     * Shows a message to the user and asks for their input.
     * Uses the given validators to validate the input, and reprompts the user if the input is invalid.
     *
     * @param thePrompt The message to show to the user.
     *
     * @param theReprompt The message to show to the user if their input is invalid.
     *
     * @param theValidators The validators to use to validate the input.
     *
     * @return The user's input.
     * */
    @SafeVarargs
    public final String promptUserInput(String thePrompt, String theReprompt, Predicate<String>... theValidators) {
        write(thePrompt);
        while (true) {
            String choice = readLine();

            for (Predicate<String> validator : theValidators) {
                if (validator.test(choice)) {
                    return choice;
                }
            }

            write("\n" + theReprompt);
        }
    }

    /**
     * Close the input.
     * */
    public void close() {
        myDefaultReader.close();
    }

    /**
     * Reads a token from the input.
     * */
    protected String read() {
        if (myReader != null) {
            return myReader.get();
        }
        return myDefaultReader.next();
    }

    /**
     * Reads a line from the input.
     * */
    protected String readLine() {
        if (myReader != null) {
            return myReader.get();
        }
        return myDefaultReader.nextLine();
    }

    /**
     * Writes a message to the output.
     * */
    protected static void write(String theMessage) {
        if (myWriter != null) {
            myWriter.accept(theMessage);
        }
    }

    /**
     * Writes a message to the output, followed by a newline.
     * */
    protected static void writeLine(String theMessage) {
        write(theMessage + "\n");
    }

    protected int askForOption(final String[] theOptions) {
        return askForOption(theOptions, "Enter your choice: ");
    }

    protected int askForOption(final String[] theOptions, final String thePrompt) {
        return askForOption(theOptions, thePrompt, "Invalid choice. Please enter a number or the name of an option: ");
    }

    protected int askForOption(final String[] theOptions, final String thePrompt, final String theReprompt) {
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

    @SafeVarargs
    protected final int askForOption(final String prompt, final String theReprompt, final Function<String, Integer>... theValidators) {
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

}
