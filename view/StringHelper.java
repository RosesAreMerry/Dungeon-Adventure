package view;

import java.util.Locale;

/**
 * A helper class for the view package that deals with various string formatting.
 *
 * @author Rosemary
 * @version May 7th 2023\
 * */
final class StringHelper {
    private StringHelper() { }

    /**
     * Returns the indefinite article for the given word.
     *
     * @param theWord The word to get the indefinite article for.
     * @return The indefinite article for the given word.
     * */
    static String getIA(final String theWord) {
        final String theWordLower = theWord.toLowerCase(Locale.ROOT);
        if (theWordLower.startsWith("a") || theWordLower.startsWith("e") || theWordLower.startsWith("i") || theWordLower.startsWith("o") || theWordLower.startsWith("u")) {
            return "an";
        } else {
            return "a";
        }
    }

    /**
     * Returns a properly formatted list of items. Uses the Oxford comma.
     *
     * @param theItems The items to list.
     *
     * @return A properly formatted list of items.
     * */
    static String getList(final String[] theItems, final boolean theUseIA) {
        final StringBuilder sb = new StringBuilder();

        if (theItems.length == 0) {
            return "";
        }

        if (theItems.length == 1) {
            return theUseIA ? getIA(theItems[0]) + ' ' + theItems[0] : theItems[0];
        }

        for (int i = 0; i < theItems.length; i++) {
            if (i == theItems.length - 1) {
                sb.append("and ");
            }

            if (theUseIA) {
                sb.append(getIA(theItems[i])).append(' ');
            }

            sb.append(theItems[i]);

            if (i < theItems.length - 1 && theItems.length > 2) {
                sb.append(", ");
            } else if (i < theItems.length - 1) {
                sb.append(' ');
            }
        }

        return sb.toString();

    }

}
