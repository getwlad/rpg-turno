package com.proway.dao;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Archer;
import com.proway.app.characters.player.Mage;
import com.proway.app.characters.player.Warrior;
import com.proway.config.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {

    public void saveCharacter(Character character) {
        String sql = "INSERT INTO Character(name, class, lifePoints, strength, defense, magicPoints, magicDefense, criticalDamage, level, experience, magic) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            pstmt.setInt(11, character.getMagic()); // Add magic field to be saved

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o personagem, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    character.setId(id);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o personagem.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar personagem: " + e.getMessage());
        }
    }

    public void updateCharacter(Character character) {
        String sql = "UPDATE Character " +
                "SET name = ?, class = ?, lifePoints = ?, strength = ?, defense = ?, magicPoints = ?, magicDefense = ?, criticalDamage = ?, level = ?, experience = ?, magic = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
            pstmt.setInt(11, character.getMagic()); // Add magic field to be updated
            pstmt.setInt(12, character.getId()); // Update based on the character's ID

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o personagem, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar personagem: " + e.getMessage());
        }
    }

    public List<Character> loadCharacters() {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM Character";

        try (Connection conn = DatabaseUtil.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

                Character character = createCharacter(className, name);
                if (character != null) {
                    character.setId(id);
                    character.setLifePoints(lifePoints);
                    character.setLife(lifePoints);
                    character.setStrength(strength);
                    character.setDefense(defense);
                    character.setMagicPoints(magicPoints);
                    character.setMagicDefense(magicDefense);
                    character.setCriticalDamage(criticalDamage);
                    character.setLevel(level);
                    character.setExperience(experience);
                    character.setMagic(magic);

                    characters.add(character);
                } else {
                    System.out.println("Unknown character class: " + className);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar personagens: " + e.getMessage());
        }

        return characters;
    }

    public Character loadCharacterById(int id) {
        String sql = "SELECT * FROM Character WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String className = rs.getString("class");
                    int lifePoints = rs.getInt("lifePoints");
                    int strength = rs.getInt("strength");
                    int defense = rs.getInt("defense");
                    int magicPoints = rs.getInt("magicPoints");
                    int magicDefense = rs.getInt("magicDefense");
                    int criticalDamage = rs.getInt("criticalDamage");
                    int level = rs.getInt("level");
                    int experience = rs.getInt("experience");
                    int magic = rs.getInt("magic");

                    Character character = createCharacter(className, name);
                    if (character != null) {
                        character.setId(id);
                        character.setLife(lifePoints);
                        character.setLifePoints(lifePoints);
                        character.setStrength(strength);
                        character.setDefense(defense);
                        character.setMagicPoints(magicPoints);
                        character.setMagicDefense(magicDefense);
                        character.setCriticalDamage(criticalDamage);
                        character.setLevel(level);
                        character.setExperience(experience);
                        character.setMagic(magic);

                        return character;
                    } else {
                        System.out.println("Unknown character class: " + className);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar personagem por ID: " + e.getMessage());
        }

        return null;
    }

    private Character createCharacter(String className, String name) {
        switch (className) {
            case "Warrior":
                return new Warrior(name);
            case "Mage":
                return new Mage(name);
            case "Archer":
                return new Archer(name);
            default:
                return null;
        }
    }
}
