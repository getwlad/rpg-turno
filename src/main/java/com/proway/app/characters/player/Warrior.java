package com.proway.app.characters.player;

import com.proway.app.miscellany.Inventory;

public class Warrior extends Player {
    public Warrior(String nome) {
        super(nome, 30, 20, 15, 5, 30, 5, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null, 0);
    }
}
