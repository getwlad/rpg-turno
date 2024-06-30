package com.proway.app.effects.interfaces;

import com.proway.app.characters.interfaces.Character;

public interface Effect {
    public void setDamagePerRound(int damagePerRound);

    void applyEffect(Character character);

    boolean isActive();

    void reduceDuration();

    public int getDamagePerRound();

}