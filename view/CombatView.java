package view;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static view.StringHelper.getIA;

/**
 * A view that shows the combat screen.
 *
 * @author Rosemary
 * @version May 7th 2023
 * @see view.ConsoleView
 * */
public class CombatView extends ConsoleView {

    public CombatView() {
        super();
    }

    /**
     * A constructor that allows for custom input and output.
     * This is useful for testing.
     *
     * @param theCustomWriter A custom output method.
     * @param theCustomReader A custom input method.
     * */
    public CombatView(final Consumer<String> theCustomWriter, final Supplier<String> theCustomReader) {
        super(theCustomWriter, theCustomReader);
    }

    /**
     * Shows the combat screen.
     *
     * @param thePlayerName The name of the player.
     * @param theMonsterName The name of the monster.
     * @param theMonsterHealth The health of the monster.
     * @param thePlayerHealth The health of the player.
     * @param theActionLog The action log.
     * */
    public void showCombat(final String thePlayerName, final String theMonsterName, final int theMonsterHealth, final int thePlayerHealth, final String[] theActionLog) {
        final StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------------\n");

        sb.append(thePlayerName).append(" is fighting ").append(getIA(theMonsterName)).append(" ").append(theMonsterName).append("!\n");

        sb.append("Your health: ").append(thePlayerHealth).append("\n");
        sb.append(theMonsterName).append("'s health: ").append(theMonsterHealth).append("\n");

        if (theActionLog != null && theActionLog.length > 0) {
            sb.append("\n\n").append("Action Log:\n");
            final int actionLogLength = Math.min(theActionLog.length, 5); // why?
            for (int i = 0; i < actionLogLength; i++) {
                sb.append(theActionLog[i]).append("\n\n");
            }
        }

        writeLine(sb.toString());
    }
}
