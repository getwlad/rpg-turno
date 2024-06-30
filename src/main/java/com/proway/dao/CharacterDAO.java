package com.proway.dao;

import com.proway.app.characters.player.Archer;
import com.proway.app.characters.player.Mage;
import com.proway.app.characters.player.Player;
import com.proway.app.characters.player.Warrior;
import com.proway.app.items.Armor;
import com.proway.app.items.Item;
import com.proway.app.items.Weapon;
import com.proway.app.miscellany.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {
    private final Connection conn;
    private final InventoryDAO inventoryDAO;

    public CharacterDAO(Connection conn, InventoryDAO inventoryDAO) {
        this.conn = conn;
        this.inventoryDAO = inventoryDAO;
    }

    public void saveCharacter(Player character) {
        String sql = "INSERT INTO Character(name, class, lifePoints, strength, defense, magicPoints, magicDefense, " +
                "criticalDamage, level, experience, experienceToLevelUp, magic) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, character.getName());
            pstmt.setString(2, character.getClass().getSimpleName());
            pstmt.setInt(3, character.getLife());
            pstmt.setInt(4, character.getStrength());
            pstmt.setInt(5, character.getDefense());
            pstmt.setInt(6, character.getMagicPoints());
            pstmt.setInt(7, character.getMagicDefense());
            pstmt.setInt(8, character.getCriticalDamage());
            pstmt.setInt(9, character.getLevel());
            pstmt.setInt(10, character.getExperience());
            pstmt.setInt(11, character.getExperienceToLevelUp());
            pstmt.setInt(12, character.getMagic());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o personagem, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    character.setId(id);
                    character.setInventory(inventoryDAO.createInventory(id));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o personagem.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar personagem: " + e.getMessage());
        }
    }

    public Player updateCharacter(Player character) throws SQLException {
        String sql = "UPDATE Character " +
                "SET name = ?, class = ?, lifePoints = ?, strength = ?, defense = ?, magicPoints = ?, magicDefense = ?," +
                "criticalDamage = ?, level = ?, experience = ?, experienceToLevelUp = ?, magic = ? " +
                "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, character.getName());
            pstmt.setString(2, character.getClass().getSimpleName());
            pstmt.setInt(3, character.getLife());
            pstmt.setInt(4, character.getStrength());
            pstmt.setInt(5, character.getDefense());
            pstmt.setInt(6, character.getMagicPoints());
            pstmt.setInt(7, character.getMagicDefense());
            pstmt.setInt(8, character.getCriticalDamage());
            pstmt.setInt(9, character.getLevel());
            pstmt.setInt(10, character.getExperience());
            pstmt.setInt(11, character.getExperienceToLevelUp());
            pstmt.setInt(12, character.getMagic());
            pstmt.setInt(13, character.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o personagem, nenhuma linha afetada.");
            }
            inventoryDAO.updateInventory(character.getInventory());
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar personagem: " + e.getMessage());
        }

        return loadCharacterById(character.getId());
    }

    public List<Player> loadCharacters() {
        List<Player> characters = new ArrayList<>();
        String sql = "SELECT * FROM Character";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Player character = getCharacterFromRs(rs);
                if (character != null) {
                    characters.add(character);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar personagens: " + e.getMessage());
        }

        return characters;
    }

    public Player loadCharacterById(int id) {
        String sql = "SELECT * FROM Character WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return getCharacterFromRs(rs);
                }

            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar personagem por ID: " + e.getMessage());
        }

        return null;
    }

    private Player getCharacterFromRs(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String className = rs.getString("class");
        int lifePoints = rs.getInt("lifePoints");
        int strength = rs.getInt("strength");
        int defense = rs.getInt("defense");
        int magic = rs.getInt("magic");
        int magicPoints = rs.getInt("magicPoints");
        int magicDefense = rs.getInt("magicDefense");
        int criticalDamage = rs.getInt("criticalDamage");
        int level = rs.getInt("level");
        int experience = rs.getInt("experience");
        int experienceToLevelUp = rs.getInt("experienceToLevelUp");
        Inventory inventory = inventoryDAO.loadByCharacterId(id);
        Player character = createCharacter(className, name);
        if (character != null) {
            initializeCharacter(character, id, lifePoints, strength, defense,
                    magicPoints, magicDefense, criticalDamage, level,
                    experience, experienceToLevelUp, magic, inventory);
        } else {
            System.out.println("Unknown character class: " + className);
        }

        return character;
    }

    private Player createCharacter(String className, String name) {
        return switch (className) {
            case "Warrior" -> new Warrior(name);
            case "Mage" -> new Mage(name);
            case "Archer" -> new Archer(name);
            default -> null;
        };
    }


    private Player initializeCharacter(Player character, int id, int lifePoints, int strength, int defense,
                                       int magicPoints, int magicDefense, int criticalDamage, int level,
                                       int experience, int experienceToLevelUp, int magic, Inventory inventory) {
        character.setId(id);
        character.setLife(lifePoints);
        character.setStrength(strength);
        character.setDefense(defense);
        character.setMagicPoints(magicPoints);
        character.setMagicDefense(magicDefense);
        character.setCriticalDamage(criticalDamage);
        character.setLevel(level);
        character.setExperience(experience);
        character.setMagic(magic);
        character.setExperienceToLevelUp(experienceToLevelUp);
        inventory.setItems(inventory.getItems().stream().map(item -> {
            item.setOwner(character);
            return item;
        }).toList());
        inventory.setOwner(character);
        character.setInventory(inventory);
        for (Item item : inventory.getEquippedItems()) {
            character.getInventory().equipItem(item);
            if (item instanceof Armor) {
                character.setArmor(new Armor(item));
            } else {
                character.setWeapon(new Weapon(item));
            }
        }
        return character;
    }

    public void deleteAll() throws SQLException {
                String sql = "DELETE FROM Character WHERE 1 = 1";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM Character WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw e;
        }
    }
}
