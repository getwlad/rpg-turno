package com.proway.app.characters.player;

import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.interfaces.SkillType;
import com.proway.app.miscellany.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Archer extends Player {
    public Archer(String nome) {
        super(nome, 20, 15, 8, 15, 100, 8, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null, 0,
                SkillType.FIRE, new ArrayList<>(List.of(new Corrupt())));
    }
}
