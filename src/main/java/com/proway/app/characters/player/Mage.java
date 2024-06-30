package com.proway.app.characters.player;

import com.proway.app.miscellany.Inventory;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 15, 5, 5, 20, 200, 10, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null);
    }
}
