package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Slime extends Enemy {
    public Slime(int level) {
        super("Slime", MonsterType.MONSTER, 25, 5, 2, 10, 10, 10,
                10, level, 10, 10, 10, SkillType.FIRE,
                new ArrayList<>(List.of(new Freeze())));
    }
}