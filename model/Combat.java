package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a combat encounter between the player and the opponent (monster).
 * It manages the flow of the combat, including handling attacks,
 * updating the action log, and retrieving the action log.
 *
 * @author Chelsea Dacones
 */
public class Combat {
    List<String> myActionLog;

    /**
     * Constructs a Combat object and initializes instance fields.
     */
    public Combat() {
        myActionLog = new ArrayList<>();
    }

    /**
     * Initiates the combat between the player and opponent (monster).
     * The fight continues until either character has fainted (loses all hit points).
     * During each turn, the active character performs an attack on the other.
     *
     * @param thePlayer   the player participating in the combat
     * @param theOpponent the monster participating in the combat
     */
    public void initiateCombat(final DungeonCharacter thePlayer, final DungeonCharacter theOpponent) {
        boolean isPlayersTurn = true;
        while (!thePlayer.isFainted() && !theOpponent.isFainted()) {
            if (isPlayersTurn) {
                handleAttack(thePlayer, theOpponent, myActionLog);
            } else {
                handleAttack(theOpponent, thePlayer, myActionLog);
            }
            isPlayersTurn = !isPlayersTurn;
        }
    }

    /**
     * Handles an attack action between two combatants.
     * Action log is updated as one combatant attacks the other.
     *
     * @param theCombatant1 the character initiating the attack
     * @param theCombatant2 the character being attacked
     * @param theActionLog  the list to store the action log messages.
     */
    private void handleAttack(final DungeonCharacter theCombatant1, final DungeonCharacter theCombatant2, final List<String> theActionLog) {
        theCombatant1.attack(theCombatant2);
        if (theCombatant2.isAttacked()) {
            theActionLog.add(theCombatant1.getName() + " attacked " + theCombatant2.getName() + " with " + theCombatant1.getTotalDamage() + " damage!");
            if (theCombatant2 instanceof Healable) {
                ((Healable) theCombatant2).heal();
                theActionLog.add(theCombatant2.getName() + " healed by " + ((Healable) theCombatant2).healAmount() + " hit points");
            }
        } else {
            theActionLog.add(theCombatant1.getName() + " failed to attack " + theCombatant2.getName());
        }
    }

    /**
     * Retrieves the action log of the combat.
     *
     * @return the list of action log messages recorded during the combat.
     */
    public List<String> getActionLog() {
        return myActionLog;
    }
}
