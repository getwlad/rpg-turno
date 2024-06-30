package com.proway;

import com.proway.database.DatabaseInitializer;
import com.proway.database.DatabaseUtil;
import com.proway.views.menu.Home;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseUtil.connect();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connection);
        databaseInitializer.createTables();
        databaseInitializer.insertItems();
        DatabaseUtil.closeConnection(connection);
        Home.run();
    }
}