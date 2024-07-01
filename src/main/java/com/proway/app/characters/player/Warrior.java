package com.proway.app.characters.player;

import com.proway.app.characters.skills.Spark;
import com.proway.app.characters.skills.interfaces.SkillType;
import com.proway.app.miscellany.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player {
    public Warrior(String nome) {
        super(nome, 30, 20, 15, 5, 30, 5, 2,
                1, 0, initialXPRequired, new Inventory(200), null, null, 0,
                SkillType.POISON, new ArrayList<>(List.of(new Spark())));
    }
}
