package com.proway.app.characters.skills;

import com.proway.app.characters.skills.interfaces.Skill;
import com.proway.app.characters.skills.interfaces.SkillType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Corrupt extends Skill {
    public Corrupt() {
        super("Corrupt", SkillType.POISON, 5);
    }
}