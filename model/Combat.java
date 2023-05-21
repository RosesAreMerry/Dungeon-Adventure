package model;


import view.CombatView;

import java.util.ArrayList;
import java.util.List;

public class Combat {

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
                thePlayer.attack(theOpponent);
                actionLog.add(thePlayer.getName() + " attacked " + theOpponent.getName() + " with " + thePlayer.getTotalDamage() + " damage!");
            } else {
                theOpponent.attack(thePlayer);
                actionLog.add(theOpponent.getName() + " attacked " + thePlayer.getName() + " with " + theOpponent.getTotalDamage() + " damage!");
            }
            isPlayersTurn = !isPlayersTurn;
        }
        myCombatView.showCombat(thePlayer.getName(), theOpponent.getName(), theOpponent.getHitPoints(),
                thePlayer.getHitPoints(), actionLog.toArray(new String[0]));
    }
}
