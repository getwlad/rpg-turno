package com.proway.app.characters.skills.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Skill {
    private String name;
    private SkillType skillType;
    private int damage;

    public Skill(String name, SkillType skillType, int damage) {
        this.name = name;
        this.skillType = skillType;
        this.damage = damage;
    }
}
