package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Wolf extends Enemy {
    public Wolf(int level) {
        super("Wolf", MonsterType.MONSTER, 25, 8, 4, 10, 10, 10,
                10, level, 10, 10, 10, SkillType.POISON,
                new ArrayList<>(List.of(new Freeze())));
    }
}