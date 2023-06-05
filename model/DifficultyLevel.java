package model;

import view.AdventureView;

import java.text.DecimalFormat;

public enum DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

    public void adjustMonsterStatistics(final Monster theMonster) {
        final double percentage = switch (this) {
            case EASY -> 0.3; // decrease stats by 20%, decrease stats by 70% for demo
            case MEDIUM -> 1; // keep stats the same
            case HARD -> 1.2; // increase stats by 20%
        };
        adjustCharacterStatistics(theMonster, percentage);
        theMonster.setMyMinHeal((int) (theMonster.getMinHeal() * percentage));
        theMonster.setMyMaxHeal((int) (theMonster.getMaxHeal() * percentage));
    }

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

    public double adjustHindrances() {
        return switch (this) {
            case EASY -> 0.1 * 1; // remain unchanged
            case MEDIUM -> 0.1 * 2.0; // double the chances
            case HARD -> 0.1 * 4.0; // quadruple the chances
        };
    }

    public double adjustPotions() {
        return switch (this) {
            case EASY -> 0.1 * 3.0; // triple the chances
            case MEDIUM -> 0.1; // remain unchanged
            case HARD -> 0.1 * 0.8; // decrease by 20%
        };
    }

    public Dungeon createDungeon() {
        AdventureView av = new AdventureView();
        return switch (this) {
            case EASY -> {
                av.sendMessage("Generating dungeon with 10 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(10, adjustHindrances(), adjustPotions());
            } // small dungeon
            case MEDIUM -> {
                av.sendMessage("Generating dungeon with 25 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(25, adjustHindrances(), adjustPotions());
            } // medium dungeon
            case HARD -> {
                av.sendMessage("Generating dungeon with 50 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(50, adjustHindrances(), adjustPotions());
            } // large dungeon
        };
    }

    public void adjustCharacterStatistics(final DungeonCharacter theCharacter, final double thePercentage) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        theCharacter.setHitPoints((int) (theCharacter.getHitPoints() * thePercentage));
        theCharacter.setMyMaxHitPoints((int) (theCharacter.getHitPoints() * thePercentage));
        theCharacter.setHitChance(Double.parseDouble(decimalFormat.format(theCharacter.getHitChance() * thePercentage)));
        theCharacter.setMyAttackSpeed((int) (theCharacter.getAttackSpeed() * thePercentage));
        theCharacter.setMyDamageMin((int) (theCharacter.getDamageMin() * thePercentage));
        theCharacter.setMyDamageMax((int) (theCharacter.getDamageMax() * thePercentage));
    }

    public void adjustGameLevel(final Dungeon theDungeon, final Hero theHero) {
        adjustHeroStatistics(theHero);
        for (final Room room : theDungeon.getAllRooms().values()) {
            if (room.getMonster() != null) {
                adjustMonsterStatistics(room.getMonster());
            }
        }
    }
}
