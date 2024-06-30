package com.proway.dao;

import com.proway.app.items.Armor;
import com.proway.app.items.Item;
import com.proway.app.items.Weapon;
import com.proway.app.items.enums.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryXItemDAO {
    private final ItemDAO itemDAO;
    private final Connection conn;

    public InventoryXItemDAO(Connection conn, ItemDAO itemDAO) {
        this.conn = conn;
        this.itemDAO = itemDAO;
    }

    public Item addInvXItem(Item item, int inventoryId) {
        String sql = "INSERT INTO InventoryXItem(inventoryId, itemId, equipped) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inventoryId);
            pstmt.setInt(2, item.getId());
            pstmt.setBoolean(3, item.isEquipped());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int newItemId = rs.getInt(1);
                item.setItemInvId(newItemId);
            } else {
                throw new RuntimeException("Failed to retrieve generated ID");
            }
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteInvXItem(Item item, int inventoryId) {
        String sql = "DELETE FROM InventoryXItem WHERE id = ? inventoryId = ? AND itemId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getItemInvId());
            pstmt.setInt(2, inventoryId);
            pstmt.setInt(3, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Item> loadItemsInventory(int inventoryId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM inventoryXitem WHERE inventoryId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int itemId = rs.getInt("itemId");
                boolean equipped = rs.getBoolean("equipped");

                Item item = itemDAO.loadItemById(itemId);
                if (item.getType().equals(Type.ARMOR)) {
                    item = new Armor(item);
                } else {
                    item = new Weapon(item);
                }
                item.setItemInvId(id);
                item.setEquipped(equipped);
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new SQLException(("Ocorreu um erro ao carregar o inventário"));
        }

    }

    public void updateEquipped(List<Item> items) throws SQLException {

        for (Item item : items) {
            String sql = "UPDATE inventoryXitem SET equipped  = ? WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setBoolean(1, item.isEquipped());
                pstmt.setInt(2, item.getItemInvId());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Falha ao atualizar o itens do inv, nenhuma linha afetada.");
                }
            } catch (SQLException e) {
                throw new SQLException(("Ocorreu um erro ao atualizar os itens do inventário" + e.getMessage()));
            }
        }


    }

}
