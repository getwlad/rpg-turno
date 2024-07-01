package com.proway.app.characters.skills;

import com.proway.app.characters.skills.interfaces.Skill;
import com.proway.app.characters.skills.interfaces.SkillType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Freeze extends Skill {
    public Freeze() {
        super("Freeze", SkillType.ICE, 5);
    }
}