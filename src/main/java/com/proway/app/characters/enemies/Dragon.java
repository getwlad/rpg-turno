package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.skills.Corrupt;
import com.proway.app.characters.skills.Freeze;
import com.proway.app.characters.skills.Spark;
import com.proway.app.characters.skills.interfaces.SkillType;

import java.util.ArrayList;
import java.util.List;

public class Dragon extends Enemy {

    public Dragon(int level) {
        super("Dragon", MonsterType.BOSS, 50, 20, 15, 20, 2000, 15,
                20, level, 15, 10, 10, SkillType.POISON,
                new ArrayList<>(List.of(new Spark(), new Corrupt(), new Freeze())));
    }
}