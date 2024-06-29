package com.proway.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void createTables() {
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

        try (Connection conn = DatabaseUtil.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlPersonagem);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
