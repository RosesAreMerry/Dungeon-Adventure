package model;

import java.sql.*;

/**
 * Creates instances of different Monster's based on statistics stored in a SQLite database.
 *
 * @author Chelsea Dacones
 */
public class MonsterFactory {
    private final Connection myConnection;

    public MonsterFactory() {
        try {
            myConnection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (final SQLException theException) {
            throw new RuntimeException(theException);
        }
    }

    /**
     * Creates a random Monster that is in the SQL database.
     *
     * @return a random Monster
     * */
    public Monster createMonsterRandom() {
        try {
            // prepare a SQL statement to select the Monster's stats from the database
            final PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM Monster ORDER BY RANDOM() LIMIT 1");
            // execute SQL statement and obtain result set
            return createMonster(statement);
        } catch (final SQLException theException) {
            throw new RuntimeException(theException);
        }
    }

    /**
     * Creates a Monster based on the Monster's name.
     *
     * @param theMonsterName the Monster's name
     *
     * @return a Monster
     * */
    public Monster createMonsterByName(String theMonsterName) {
        try {
            // prepare a SQL statement to select the Monster's stats from the database
            final PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM Monster WHERE Name = ?");
            // set parameter in SQL statement to the Monster's name
            statement.setString(1, theMonsterName);
            // execute SQL statement and obtain result set
            return createMonster(statement);
        } catch (final SQLException theException) {
            throw new RuntimeException(theException + " " + theMonsterName + " does not exist in the database.");
        }
    }

    /**
     * Creates a Monster based on a sql query.
     *
     * @param theQuery the sql query
     * @return a Monster
     */
    Monster createMonster(final PreparedStatement theQuery) {
        try {
            final ResultSet resultSet = theQuery.executeQuery();

            if (resultSet.next()) {
                // retrieve the Monster's stats from the database
                final String monsterName = resultSet.getString("Name");
                final int hitPoints = resultSet.getInt("Hit Points");
                final double hitChance = resultSet.getDouble("Chance to Hit");
                final int minDamage = resultSet.getInt("Minimum Damage");
                final int maxDamage = resultSet.getInt("Maximum Damage");
                final int attackSpeed = resultSet.getInt("Attack Speed");
                final double healChance = resultSet.getDouble("Chance to Heal");
                final int minHeal = resultSet.getInt("Minimum Heal Points");
                final int maxHeal = resultSet.getInt("Maximum Heal Points");

                // create Monster object based on statistics stored in database
                return new Monster(monsterName, hitPoints, hitChance, minDamage, maxDamage, attackSpeed,
                        healChance, minHeal, maxHeal);
            }
            else { // if no Monster with the provided name was found in the database
                throw new IllegalArgumentException("No Monster with the provided name was found in the database.");
            }
        } catch (final SQLException theException) {
            throw new RuntimeException(theException);
        }
    }
}