package model;


import view.CombatView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Combat implements Serializable {

    CombatView myCombatView;

    public Combat() {
        myCombatView = new CombatView();
    }

    public void initiateCombat(final DungeonCharacter thePlayer, final DungeonCharacter theOpponent) {
        boolean isPlayersTurn = true;
        final List<String> actionLog = new ArrayList<>();
        myCombatView.showCombat(thePlayer.getName(), theOpponent.getName(), theOpponent.getHitPoints(),
                thePlayer.getHitPoints(), null);
        while (!thePlayer.isFainted() && !theOpponent.isFainted()) {
            if (isPlayersTurn) {
                handleAttack(thePlayer, theOpponent, actionLog);
            } else {
                handleAttack(theOpponent, thePlayer, actionLog);
            }
            isPlayersTurn = !isPlayersTurn;
        }
        myCombatView.showCombat(thePlayer.getName(), theOpponent.getName(), theOpponent.getHitPoints(),
                thePlayer.getHitPoints(), actionLog.toArray(new String[0]));
    }
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
}
