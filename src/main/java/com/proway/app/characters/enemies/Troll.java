package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.Spark;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Troll extends Enemy {
    public Troll(int level) {
        super("Troll", MonsterType.MINI_BOSS, 35, 10, 6, 10, 10, 10,
                15, level, 15, 15, 10, SkillType.POISON,
                new ArrayList<>(List.of(new Spark(), new Freeze())));
    }
}