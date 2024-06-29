package com.proway;

import com.proway.config.DatabaseInitializer;
import com.proway.views.menu.Home;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.createTables();
        Home.run();
    }
}