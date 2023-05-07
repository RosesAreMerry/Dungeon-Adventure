package model;

import java.sql.*;

public class MonsterFactory {

    private final Connection connection;
    public MonsterFactory() {
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Monster createMonster(String theMonsterName) {
        // String theName, int theHitPoints, double theHitChance, int theMinDamage, int theMaxDamage,
        //                      int theAttackSpeed, double theHealChance, int theMinHeal, int theMaxHeal
        try {
            PreparedStatement statement = null;
            statement = connection.prepareStatement("SELECT * FROM Monster WHERE Name = ?");
            statement.setString(1, theMonsterName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int hitPoints = resultSet.getInt("Hit Points");
                double hitChance = resultSet.getDouble("Chance to Hit");
                int minDamage = resultSet.getInt("Minimum Damage");
                int maxDamage = resultSet.getInt("Maximum Damage");
                int attackSpeed = resultSet.getInt("Attack Speed");
                double healChance = resultSet.getDouble("Chance to Heal");
                int minHeal = resultSet.getInt("Minimum Heal Points");
                int maxHeal = resultSet.getInt("Maximum Heal Points");

                return new Monster(theMonsterName, hitPoints, hitChance, minDamage, maxDamage, attackSpeed, healChance, minHeal, maxHeal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
