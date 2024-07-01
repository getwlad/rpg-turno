package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Enemy {
    public Skeleton(int level) {
        super("Skeleton", MonsterType.MONSTER, 15, 4, 6, 12, 12, 12,
                5, level, 5, 15, 15, SkillType.FIRE,
                new ArrayList<>(List.of(new Corrupt())));
    }
}
