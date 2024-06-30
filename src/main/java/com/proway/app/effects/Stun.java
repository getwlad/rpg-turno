package com.proway.app.effects;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.effects.interfaces.Effect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stun implements Effect {
    private int damagePerRound;
    private int duration;

    public Stun(int duration) {
        this.damagePerRound = 0;
        this.duration = duration;
    }

    @Override
    public void applyEffect(Character character) {
    }

    @Override
    public boolean isActive() {
        return this.getDuration() > 0;
    }

    @Override
    public void reduceDuration() {
        this.setDuration(this.getDuration() - 1);
    }


}

