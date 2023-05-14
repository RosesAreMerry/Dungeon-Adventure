package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatViewTest extends ConsoleViewTestAbstract {

    CombatView myCombatView;

    @BeforeEach
    void setUp() {
        myCombatView = new CombatView(myCustomWriter, myCustomReader);
    }

    @Test
    void showCombatTest() {
        myCombatView.showCombat("Adventurer",
                "Monster",
                10,
                10,
                new String[]{"Monster Attacked with Claws!\nClaws hit for 23 Damage!",
                        "Adventurer Attacked with Sword!\nSword hit for 10 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!"});

        assertEquals("""
                ----------------------------------------
                Adventurer is fighting a Monster!
                Your health: 10
                Monster's health: 10


                Action Log:
                Monster Attacked with Claws!
                Claws hit for 23 Damage!

                Adventurer Attacked with Sword!
                Sword hit for 10 Damage!

                Adventurer Attacked with Bite!
                Bite hit for 12 Damage!


                """, myMockedOutput.toString());
    }

    @Test
    void showCombatTestNoActionLog() {
        myCombatView.showCombat("Adventurer",
                "Monster",
                10,
                10,
                new String[]{});

        assertEquals("""
                ----------------------------------------
                Adventurer is fighting a Monster!
                Your health: 10
                Monster's health: 10
                
                """, myMockedOutput.toString());
    }

    @Test
    void showCombatTestNoActionLog2() {
        myCombatView.showCombat("Adventurer",
                "Monster",
                10,
                10,
                null);

        assertEquals("""
                ----------------------------------------
                Adventurer is fighting a Monster!
                Your health: 10
                Monster's health: 10
                
                """, myMockedOutput.toString());
    }

    @Test
    void showCombatTestActionLogCutoff() {
        myCombatView.showCombat("Adventurer",
                "Monster",
                10,
                10,
                new String[]{"Monster Attacked with Claws!\nClaws hit for 23 Damage!",
                        "Adventurer Attacked with Sword!\nSword hit for 10 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!",
                        "Adventurer Attacked with Bite!\nBite hit for 12 Damage!"});

        assertEquals("""
                ----------------------------------------
                Adventurer is fighting a Monster!
                Your health: 10
                Monster's health: 10
                
                
                Action Log:
                Monster Attacked with Claws!
                Claws hit for 23 Damage!
                
                Adventurer Attacked with Sword!
                Sword hit for 10 Damage!
                
                Adventurer Attacked with Bite!
                Bite hit for 12 Damage!
                
                Adventurer Attacked with Bite!
                Bite hit for 12 Damage!
                
                Adventurer Attacked with Bite!
                Bite hit for 12 Damage!
                
                
                """, myMockedOutput.toString());
    }

}