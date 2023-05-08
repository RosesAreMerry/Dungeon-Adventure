package model;

import java.sql.*;

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
    public Monster createMonster(String theMonsterName) {
        try {
            // prepare a SQL statement to select the Monster's stats from the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Monster WHERE Name = ?");
            // set parameter in SQL statement to the Monster's name
            statement.setString(1, theMonsterName);
            // execute SQL statement and obtain result set
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // retrieve the Monster's stats from the database
                int hitPoints = resultSet.getInt("Hit Points");
                double hitChance = resultSet.getDouble("Chance to Hit");
                int minDamage = resultSet.getInt("Minimum Damage");
                int maxDamage = resultSet.getInt("Maximum Damage");
                int attackSpeed = resultSet.getInt("Attack Speed");
                double healChance = resultSet.getDouble("Chance to Heal");
                int minHeal = resultSet.getInt("Minimum Heal Points");
                int maxHeal = resultSet.getInt("Maximum Heal Points");

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