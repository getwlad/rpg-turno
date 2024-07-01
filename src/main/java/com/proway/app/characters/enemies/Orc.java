package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.Spark;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Orc extends Enemy {
    public Orc(int level) {
        super("Orc", MonsterType.MINI_BOSS, 30, 10, 7, 8, 8, 5,
                15, level, 15, 10, 10, SkillType.ICE,
                new ArrayList<>(List.of(new Spark(), new Corrupt())));
    }
}
