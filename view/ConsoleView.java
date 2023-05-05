package view;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;
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

    public void sendMessage(String theMessage) {
        write(theMessage + "\n");
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

        while (true) {
            write("Enter your choice: ");
            String stringChoice = readLine();

            for (String theOption : theOptions) {
                if (stringChoice.equalsIgnoreCase(theOption)) {
                    return theOption;
                }
            }

            try {
                int choice = Integer.parseInt(stringChoice);

                if (choice > 0 && choice < theOptions.length) {
                    return theOptions[choice - 1];
                }

                write("\nNumber out of range. Please enter a number or the name of an option.\n");

            } catch (NumberFormatException e) {

                write("\nInvalid choice. Please enter a number or the name of an option.\n");

            }
        }

    }

    public void close() {
        myReader.close();
    }

}
