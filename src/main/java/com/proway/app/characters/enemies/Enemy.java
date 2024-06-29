package com.proway.app.characters.enemies;

import com.proway.app.characters.interfaces.Character;

public class Enemy extends Character {
    private final String type;
    private final int expReward;

    public Enemy(String name, String type, int lifePoints, int strength, int defense, int magic, int magicPoints,
                 int magicDefense, int criticalDamage, int level, int experience, int experienceToLevelUp, int expReward) {
        super(name, lifePoints, strength, defense, magic, magicPoints, magicDefense, criticalDamage, level, experience, experienceToLevelUp);
        this.type = type;
        this.expReward = expReward;
    }

    public int getExpReward() {
        return expReward;
    }

    public String getType() {
        return type;
    }
}
