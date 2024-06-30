package com.proway.app.characters.player;

import com.proway.app.miscellany.Inventory;

public class Archer extends Player {
    public Archer(String nome) {
        super(nome, 20, 15, 8, 15, 100, 8, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null);
    }
}
