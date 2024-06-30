package com.proway.dao;

import com.proway.app.items.Item;
import com.proway.app.items.enums.Rarity;
import com.proway.app.items.enums.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final Connection conn;

    public ItemDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM Item";

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Type type = Type.valueOf(rs.getString("type").toUpperCase());
                    Rarity rarity = Rarity.valueOf(rs.getString("rarity").toUpperCase());
                    int level = rs.getInt("level");
                    int strengthBonus = rs.getInt("strengthBonus");
                    int magicBonus = rs.getInt("magicBonus");
                    int lifePointsBonus = rs.getInt("lifePointsBonus");
                    int defenseBonus = rs.getInt("defenseBonus");
                    int criticalDamageBonus = rs.getInt("criticalDamageBonus");
                    int magicDefenseBonus = rs.getInt("magicDefenseBonus");

                    // Create Item object
                    Item item = new Item(id, name, type, rarity, level, strengthBonus, magicBonus, lifePointsBonus,
                            criticalDamageBonus, defenseBonus, magicDefenseBonus, null);

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar todos os itens: " + e.getMessage());
        }

        return items;
    }

    public List<Item> loadItemsByCharacterId(int characterId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.* " +
                "FROM Item i " +
                "JOIN Inventory inv ON i.id = inv.itemId AND inv.characterId = ? " +
                "JOIN EquippedItems eq ON i.id = eq.itemId AND eq.characterId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, characterId);
            pstmt.setInt(2, characterId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Type type = Type.valueOf(rs.getString("type"));
                    Rarity rarity = Rarity.valueOf(rs.getString("rarity"));
                    int level = rs.getInt("level");
                    int strengthBonus = rs.getInt("strengthBonus");
                    int magicBonus = rs.getInt("magicBonus");
                    int lifePointsBonus = rs.getInt("lifePointsBonus");
                    int criticalDamageBonus = rs.getInt("criticalDamageBonus");
                    int inventoryId = rs.getInt("inventoryId");
                    int equippedId = rs.getInt("equippedId");
                    int defenseBonus = rs.getInt("defenseBonus");
                    int magicDefenseBonus = rs.getInt("magicDefenseBonus");

                    Item item = new Item(id, name, type, rarity, level, strengthBonus, magicBonus, lifePointsBonus,
                            criticalDamageBonus, defenseBonus, magicDefenseBonus, null);

                    if (equippedId > 0) {
                        item.setEquipped(true);
                    }

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar itens por ID do personagem: " + e.getMessage());
        }

        return items;
    }

    public Item loadItemById(int idItem) throws SQLException {
        String sql = "SELECT * FROM Item WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idItem);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Type type = Type.valueOf(rs.getString("type"));
                Rarity rarity = Rarity.valueOf(rs.getString("rarity"));
                int level = rs.getInt("level");
                int strengthBonus = rs.getInt("strengthBonus");
                int magicBonus = rs.getInt("magicBonus");
                int lifePointsBonus = rs.getInt("lifePointsBonus");
                int criticalDamageBonus = rs.getInt("criticalDamageBonus");
                int defenseBonus = rs.getInt("defenseBonus");
                int magicDefenseBonus = rs.getInt("magicDefenseBonus");
                return new Item(id, name, type, rarity, level, strengthBonus, magicBonus, lifePointsBonus,
                        criticalDamageBonus, defenseBonus, magicDefenseBonus, null);
            }
            return null;

        } catch (SQLException e) {
            throw new SQLException(("Ocorreu um erro ao carregar o inventário"));
        }
    }
}
