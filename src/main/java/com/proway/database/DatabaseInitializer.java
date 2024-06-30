package com.proway.database;

import com.proway.app.items.enums.Rarity;
import com.proway.app.items.enums.Type;

import java.sql.*;

public class DatabaseInitializer {
    private final Connection conn;

    public DatabaseInitializer(Connection conn) {
        this.conn = conn;
    }

    public void createTables() throws SQLException {
        String sqlPersonagem = "CREATE TABLE IF NOT EXISTS Character (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "class TEXT NOT NULL, " +
                "lifePoints INTEGER, " +
                "strength INTEGER, " +
                "defense INTEGER, " +
                "magic INTEGER, " +
                "magicPoints INTEGER, " +
                "magicDefense INTEGER, " +
                "criticalDamage INTEGER, " +
                "level INTEGER, " +
                "experience INTEGER, " +
                "experienceToLevelUp INTEGER" +
                ");";

        String sqlItem = "CREATE TABLE IF NOT EXISTS Item (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "type VARCHAR(50) NOT NULL, " +
                "rarity VARCHAR(50) NOT NULL, " +
                "level INT NOT NULL, " +
                "strengthBonus INT, " +
                "magicBonus INT, " +
                "lifePointsBonus INT, " +
                "criticalDamageBonus INT," +
                "defenseBonus INT," +
                "magicDefenseBonus INT" +
                ");";

        String sqlInventory = "CREATE TABLE IF NOT EXISTS Inventory (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "maxCapacity INT NOT NULL DEFAULT 200, " +
                "characterId INT NOT NULL DEFAULT 200, " +
                "FOREIGN KEY (characterId) REFERENCES Character(id)" +
                ");";

        String sqlInventoryxItem = "CREATE TABLE IF NOT EXISTS inventoryXitem (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "itemId INT NOT NULL, " +
                "inventoryId INT NOT NULL, " +
                "equipped BOOLEAN NOT NULL DEFAULT FALSE," +
                "FOREIGN KEY (inventoryId) REFERENCES Inventory(id)," +
                "FOREIGN KEY (itemId) REFERENCES Items(id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlPersonagem);
            stmt.execute(sqlItem);
            stmt.execute(sqlInventory);
            stmt.execute(sqlInventoryxItem);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertItems() {
        String sqlCheckItems = "SELECT COUNT(*) FROM Item";
        String sqlInsertItem = "INSERT INTO Item (name, type, rarity, level, strengthBonus, magicBonus, lifePointsBonus, " +
                "criticalDamageBonus, defenseBonus, magicDefenseBonus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect()) {
            try (PreparedStatement stmtCheckItems = conn.prepareStatement(sqlCheckItems);
                 ResultSet rs = stmtCheckItems.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return;
                }
            } catch (SQLException e) {
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsertItem)) {
                insertSwords(stmt, "Warrior");
                insertStaffs(stmt, "Mage");
                insertBows(stmt, "Archer");
                insertArmor(stmt);

                stmt.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertSwords(PreparedStatement stmt, String classType) throws SQLException {
        insertWeapon(stmt, "Short Sword", Rarity.COMMON.toString(), Type.SWORD.toString(), classType, 10, 0, 0, 2, 10, 0);
        insertWeapon(stmt, "Long Sword", Rarity.COMMON.toString(), Type.SWORD.toString(), classType, 15, 0, 0, 3, 15, 0);
        insertWeapon(stmt, "Master Sword", Rarity.RARE.toString(), Type.SWORD.toString(), classType, 20, 0, 0, 5, 20, 0);
        insertWeapon(stmt, "Legend Sword", Rarity.SUPER_RARE.toString(), Type.SWORD.toString(), classType, 20, 0, 0, 7, 25, 0);
        insertWeapon(stmt, "Sacred Sword", Rarity.EPIC.toString(), Type.SWORD.toString(), classType, 35, 35, 35, 9, 35, 0);
    }

    private void insertStaffs(PreparedStatement stmt, String classType) throws SQLException {
        insertWeapon(stmt, "Common Staff", Rarity.COMMON.toString(), Type.STAFF.toString(), classType, 0, 10, 0, 2, 0, 10);
        insertWeapon(stmt, "Sky Staff", Rarity.COMMON.toString(), Type.STAFF.toString(), classType, 0, 15, 0, 3, 0, 15);
        insertWeapon(stmt, "Master Staff", Rarity.RARE.toString(), Type.STAFF.toString(), classType, 0, 20, 0, 5, 0, 20);
        insertWeapon(stmt, "Legend Staff", Rarity.SUPER_RARE.toString(), Type.STAFF.toString(), classType, 0, 25, 0, 7, 0, 25);
        insertWeapon(stmt, "Sacred Staff", Rarity.EPIC.toString(), Type.STAFF.toString(), classType, 35, 35, 35, 9, 0, 35);
    }

    private void insertBows(PreparedStatement stmt, String classType) throws SQLException {
        insertWeapon(stmt, "Long Bow", Rarity.COMMON.toString(), Type.BOW.toString(), classType, 5, 5, 0, 2, 2, 2);
        insertWeapon(stmt, "Sky Bow", Rarity.COMMON.toString(), Type.BOW.toString(), classType, 8, 8, 0, 3, 4, 4);
        insertWeapon(stmt, "Master Bow", Rarity.RARE.toString(), Type.BOW.toString(), classType, 12, 12, 0, 5, 8, 8);
        insertWeapon(stmt, "Master Bow", Rarity.SUPER_RARE.toString(), Type.BOW.toString(), classType, 14, 14, 0, 7, 16, 16);
        insertWeapon(stmt, "Sacred Bow", Rarity.EPIC.toString(), Type.BOW.toString(), classType, 35, 35, 35, 9, 35, 35);
    }

    private void insertArmor(PreparedStatement stmt) throws SQLException {
        insertArmor(stmt, "Soldier Armor", Rarity.COMMON.toString(), 5, 5, 10, 0, 5, 5);
        insertArmor(stmt, "Silver Armor", Rarity.COMMON.toString(), 8, 8, 15, 0, 8, 10);
        insertArmor(stmt, "Special Armor", Rarity.RARE.toString(), 12, 12, 20, 0, 12, 15);
        insertArmor(stmt, "Legendary Armor", Rarity.SUPER_RARE.toString(), 14, 14, 25, 0, 20, 20);
        insertArmor(stmt, "Sacred Armor", Rarity.EPIC.toString(), 35, 35, 35, 0, 35, 35);
    }

    private void insertArmor(PreparedStatement stmt, String name, String rarity, int strengthBonus,
                             int magicBonus, int lifePointsBonus, int criticalDamageBonus, int defenseBonus, int magicDefenseBonus) throws SQLException {
        insertItem(stmt, name, rarity, Type.ARMOR.toString(), strengthBonus, magicBonus, lifePointsBonus, criticalDamageBonus, defenseBonus, magicDefenseBonus);
    }

    private void insertWeapon(PreparedStatement stmt, String name, String rarity, String type, String classType,
                              int strengthBonus, int magicBonus, int lifePointsBonus,
                              int criticalDamageBonus, int defenseBonus, int magicDefenseBonus) throws SQLException {

        stmt.setString(8, classType);
        insertItem(stmt, name, rarity, type, strengthBonus, magicBonus, lifePointsBonus, criticalDamageBonus, defenseBonus, magicDefenseBonus);
    }

    private void insertItem(PreparedStatement stmt, String name, String type, String rarity, int strengthBonus,
                            int magicBonus, int lifePointsBonus, int criticalDamageBonus, int defenseBonus,
                            int magicDefenseBonus) throws SQLException {
        stmt.setString(1, name);
        stmt.setString(2, rarity.toUpperCase());
        stmt.setString(3, type.toUpperCase());
        stmt.setInt(4, 1);
        stmt.setInt(5, strengthBonus);
        stmt.setInt(6, magicBonus);
        stmt.setInt(7, lifePointsBonus);
        stmt.setInt(8, criticalDamageBonus);
        stmt.setInt(9, defenseBonus);
        stmt.setInt(10, magicDefenseBonus);
        stmt.addBatch();
    }
}
