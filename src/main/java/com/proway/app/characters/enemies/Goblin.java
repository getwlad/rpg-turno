package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Goblin extends Enemy {
    public Goblin(int level) {
        super("Goblin", MonsterType.MONSTER, 20, 6, 6, 10, 10, 3,
                10, level, 10, 10, 10, SkillType.FIRE,
                new ArrayList<>(List.of(new Freeze())));
    }
}