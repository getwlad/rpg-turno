package com.proway.app.characters.player;

import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.interfaces.SkillType;
import com.proway.app.miscellany.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 15, 5, 5, 20, 200, 10, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null, 0,
                SkillType.FIRE, new ArrayList<>(List.of(new Freeze())));
    }
}
