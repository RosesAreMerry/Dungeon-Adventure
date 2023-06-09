package model;

import java.text.DecimalFormat;

/**
 * Represents the difficulty levels of the game.
 *
 * @author Chelsea Dacones
 */
public enum DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

    /**
     * Adjust the statistics of a monster based on the difficulty level.
     *
     * @param theMonster the monster to adjust the statistics for
     */
    public void adjustMonsterStatistics(final Monster theMonster) {
        final double percentage = switch (this) {
            case EASY -> 0.8; // decrease stats by 20%
            case MEDIUM -> 1; // keep stats the same
            case HARD -> 1.2; // increase stats by 20%
        };
        adjustCharacterStatistics(theMonster, percentage);
        theMonster.setMyMinHeal((int) (theMonster.getMinHeal() * percentage));
        theMonster.setMyMaxHeal((int) (theMonster.getMaxHeal() * percentage));
    }

    /**
     * Adjust the statistics of a Hero (player) based on the difficulty level.
     *
     * @param theHero the Hero to adjust the statistics for
     */
    public void adjustHeroStatistics(final Hero theHero) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        final double percentage = switch (this) {
            case EASY -> 1.2; // increase stats by 20%
            case MEDIUM -> 1; // keep stats the same
            case HARD -> 0.8; // decrease stats by 20%
        };
        adjustCharacterStatistics(theHero, percentage);
        theHero.setBlockChance(Double.parseDouble(decimalFormat.format(theHero.getBlockChance() * percentage)));
    }

    /**
     * Adjust the probability of hindrances (monsters and pits) being in a room based on difficulty level.
     */
    public double adjustHindrances() {
        return switch (this) {
            case EASY -> 0.1 * 1; // remain unchanged
            case MEDIUM -> 0.1 * 2.0; // double the chances
            case HARD -> 0.1 * 4.0; // quadruple the chances
        };
    }

    /**
     * Adjust the probability of potions being in a room based on difficulty level.
     */
    public double adjustPotions() {
        return switch (this) {
            case EASY -> 0.1 * 3.0; // triple the chances
            case MEDIUM -> 0.1; // remain unchanged
            case HARD -> 0.1 * 0.8; // decrease by 20%
        };
    }

    /**
     * Creates a dungeon based on difficulty level.
     *
     * @return the dungeon
     */
    public Dungeon createDungeon() {
        return switch (this) {
            case EASY -> // small dungeon
                    DungeonBuilder.INSTANCE.buildDungeon(10, adjustHindrances(), adjustPotions());
            case MEDIUM -> // medium dungeon
                    DungeonBuilder.INSTANCE.buildDungeon(25, adjustHindrances(), adjustPotions());
            case HARD -> // large dungeon
                    DungeonBuilder.INSTANCE.buildDungeon(50, adjustHindrances(), adjustPotions());
        };
    }

    /**
     * Adjusts the characters (monsters or hero) statistics based on difficulty level.
     *
     * @param theCharacter  the character to adjust the statistics for
     * @param thePercentage the amount to adjust the statistics by
     */
    private void adjustCharacterStatistics(final DungeonCharacter theCharacter, final double thePercentage) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        theCharacter.setHitPoints((int) (theCharacter.getHitPoints() * thePercentage));
        theCharacter.setMaxHitPoints((int) (theCharacter.getHitPoints() * thePercentage));
        theCharacter.setHitChance(Double.parseDouble(decimalFormat.format(theCharacter.getHitChance() * thePercentage)));
        theCharacter.setAttackSpeed((int) (theCharacter.getAttackSpeed() * thePercentage));
        theCharacter.setDamageMin((int) (theCharacter.getDamageMin() * thePercentage));
        theCharacter.setDamageMax((int) (theCharacter.getDamageMax() * thePercentage));
    }

    /**
     * Adjusts the game level by adjusting hero statistics and monster statistics.
     *
     * @param theDungeon the dungeon that contains the monsters to adjust the stats for
     * @param theHero    the hero to adjust the stats for
     */
    public void adjustGameLevel(final Dungeon theDungeon, final Hero theHero) {
        adjustHeroStatistics(theHero);
        for (final Room room : theDungeon.getAllRooms().values()) {
            if (room.getMonster() != null) {
                adjustMonsterStatistics(room.getMonster());
            }
        }
    }
}
