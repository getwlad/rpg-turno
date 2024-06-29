package com.proway.app.effects;

import com.proway.app.effects.interfaces.Effect;
import com.proway.app.characters.interfaces.Character;

public class Poison implements Effect {
    private final int damagePerRound;
    private int duration;

    public Poison(int damagePerRound, int duration) {
        this.damagePerRound = damagePerRound;
        this.duration = duration;
    }

    public void applyEffect(Character character) {
        character.receiveDamage(this.getDamagePerRound());
    }

    public boolean isActive() {
        return this.getDuration() > 0;
    }

    public void reduceDuration() {
        this.setDuration(this.getDuration() - 1);
    }

    public int getDamagePerRound() {
        return damagePerRound;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}