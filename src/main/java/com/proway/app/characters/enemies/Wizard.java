package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.Spark;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Wizard extends Enemy {
    public Wizard(int level) {
        super("Wizard", MonsterType.MINI_BOSS, 20, 4, 2, 15, 15, 15,
                5, level, 5, 15, 15, SkillType.POISON,
                new ArrayList<>(List.of(new Spark(), new Freeze())));
    }
}