package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Spider extends Enemy {
    public Spider(int level) {
        super("Spider", MonsterType.MONSTER, 20, 5, 4, 8, 8, 8,
                10, level, 10, 10, 10, SkillType.FIRE,
        new ArrayList<>(List.of(new Corrupt())));
    }
}