package model;

import model.*;
import view.AdventureView;

import java.text.DecimalFormat;

public enum DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

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

    public void adjustRoomEntities(final Room theRoom) {
        final double itemPercentage = switch (this) {
            case EASY -> 3.0; // triple the chances
            case MEDIUM -> 1.0; // remain unchanged
            case HARD -> 0.8; // decrease by 20%
        };
        final double hindrancePercentage = switch (this) {
            case EASY -> 1; // remain unchanged
            case MEDIUM -> 1.2; // increase my 20%
            case HARD -> 1.4; // increase by 40&
        };
        theRoom.setMyHealthPotionProbability(0.1 * itemPercentage);
        theRoom.setMyVisionPotionProbability(0.1 * itemPercentage);
        theRoom.setMyPitProbability(0.1 * hindrancePercentage);
        theRoom.setMyMonsterProbability(0.1 * hindrancePercentage);
    }

    public Dungeon createDungeon() {
        AdventureView av = new AdventureView();
        return switch (this) {
            case EASY -> {
                av.sendMessage("Generating dungeon with 10 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(10);
            } // small dungeon
            case MEDIUM -> {
                av.sendMessage("Generating dungeon with 25 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(25);
            } // medium dungeon
            case HARD -> {
                av.sendMessage("Generating dungeon with 50 rooms...");
                yield DungeonBuilder.INSTANCE.buildDungeon(50);
            } // large dungeon
        };
    }

    public void adjustCharacterStatistics(final DungeonCharacter theCharacter, final double thePercentage) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        theCharacter.setHitPoints((int) (theCharacter.getHitPoints() * thePercentage));
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
            adjustRoomEntities(room);
        }
    }
}
