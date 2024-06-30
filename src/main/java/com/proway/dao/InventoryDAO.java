package com.proway.dao;

import com.proway.app.items.Item;
import com.proway.app.miscellany.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private final InventoryXItemDAO inventoryXItemDAO;
    private final Connection conn;

    public InventoryDAO(Connection conn, InventoryXItemDAO inventoryXItemDAO) {
        this.conn = conn;
        this.inventoryXItemDAO = inventoryXItemDAO;
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        List<Item> existingItems = inventoryXItemDAO.loadItemsInventory(inventory.getId());
        List<Item> itemsToAdd = new ArrayList<>();
        List<Item> itemsToRemove = new ArrayList<>();

        for (Item newItem : inventory.getItems()) {
            boolean found = false;
            for (Item existingItem : existingItems) {
                if (newItem.getItemInvId() == existingItem.getItemInvId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                itemsToAdd.add(newItem);
            }
        }

        if (!(existingItems.isEmpty())) {
            for (Item existingItem : existingItems) {
                boolean found = false;
                for (Item newItem : inventory.getItems()) {
                    if (existingItem.getItemInvId() == newItem.getItemInvId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    itemsToRemove.add(existingItem);
                }
            }
        }

        for (Item item : itemsToAdd) {
            item = inventoryXItemDAO.addInvXItem(item, inventory.getId());
        }

        for (Item item : itemsToRemove) {
            inventoryXItemDAO.deleteInvXItem(item, inventory.getId());
        }

        inventoryXItemDAO.updateEquipped(inventory.getItems());
    }

    public Inventory createInventory(int characterId) throws SQLException {
        String sql = "INSERT INTO Inventory(characterId) " +
                "VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, characterId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o inventário, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    Inventory inventory = new Inventory(200);
                    inventory.setId(id);
                    return inventory;

                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o inventário.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ocorreu um erro ao criar o inventário." + e.getMessage());
        }
    }

    public Inventory loadByCharacterId(int characterId) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE characterId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, characterId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int maxCapacity = rs.getInt("maxCapacity");
                Inventory inventory = new Inventory(maxCapacity);
                inventory.setId(id);
                inventory.setItems(inventoryXItemDAO.loadItemsInventory(id));
                return inventory;
            }
            throw new SQLException(("Ocorreu um erro ao carregar o inventário"));

        } catch (SQLException e) {
            throw new SQLException(("Ocorreu um erro ao carregar o inventário"));
        }

    }

}
