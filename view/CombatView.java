package view;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static view.StringHelper.getIA;

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
    public CombatView(Consumer<String> theCustomWriter, Supplier<String> theCustomReader) {
        super(theCustomWriter, theCustomReader);
    }

    public void showCombat(String thePlayerName, String theMonsterName, int theMonsterHealth, int thePlayerHealth, String[] theActionLog) {
        StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------------\n");

        sb.append(thePlayerName).append(" is fighting ").append(getIA(theMonsterName)).append(" ").append(theMonsterName).append("!\n");

        sb.append("Your health: ").append(thePlayerHealth).append("\n");
        sb.append(theMonsterName).append("'s health: ").append(theMonsterHealth).append("\n");

        if (theActionLog != null && theActionLog.length > 0) {
            sb.append("\n\n").append("Action Log:\n");
            int actionLogLength = Math.min(theActionLog.length, 5);
            for (int i = 0; i < actionLogLength; i++) {
                sb.append(theActionLog[i]).append("\n\n");
            }
        }

        writeLine(sb.toString());
    }
}
