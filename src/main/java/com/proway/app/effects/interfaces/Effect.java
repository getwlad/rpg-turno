package com.proway.app.effects.interfaces;

import com.proway.app.characters.interfaces.Character;

public interface Effect {
    void applyEffect(Character character);

    boolean isActive();

    void reduceDuration();

    public int getDamagePerRound();

}