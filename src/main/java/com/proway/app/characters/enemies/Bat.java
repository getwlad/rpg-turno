package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Bat extends Enemy {
    public Bat(int level) {
        super("Bat", MonsterType.MONSTER, 10, 3, 4, 8, 8, 2,
                15, level, 15, 15, 5, SkillType.FIRE,
                new ArrayList<>(List.of(new Corrupt())));
    }
}
