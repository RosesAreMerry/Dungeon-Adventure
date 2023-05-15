package model;

import java.sql.*;

/**
 * Creates instances of different Monster's based on statistics stored in a SQLite database.
 *
 * @author Chelsea Dacones
 */
public class MonsterFactory {
    private final Connection connection;
    public MonsterFactory() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a Monster based on the statistics from SQLite database.
     *
     * @param theMonsterName the name of the Monster to create
     * @return a Monster
     */
    public Monster createMonster(final String theMonsterName) {
        try {
            // prepare a SQL statement to select the Monster's stats from the database
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM Monster WHERE Name = ?");
            // set parameter in SQL statement to the Monster's name
            statement.setString(1, theMonsterName);
            // execute SQL statement and obtain result set
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // retrieve the Monster's stats from the database
                final int hitPoints = resultSet.getInt("Hit Points");
                final double hitChance = resultSet.getDouble("Chance to Hit");
                final int minDamage = resultSet.getInt("Minimum Damage");
                final int maxDamage = resultSet.getInt("Maximum Damage");
                final int attackSpeed = resultSet.getInt("Attack Speed");
                final double healChance = resultSet.getDouble("Chance to Heal");
                final int minHeal = resultSet.getInt("Minimum Heal Points");
                final int maxHeal = resultSet.getInt("Maximum Heal Points");

                // create Monster object based on statistics stored in database
                return new Monster(theMonsterName, hitPoints, hitChance, minDamage, maxDamage, attackSpeed,
                        healChance, minHeal, maxHeal);
            }
            else { // if no Monster with the provided name was found in the database
                System.out.println("Monster not found: " + theMonsterName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}