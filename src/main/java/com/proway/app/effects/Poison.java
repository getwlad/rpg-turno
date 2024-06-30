package com.proway.app.effects;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.effects.interfaces.Effect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Poison implements Effect {
    private int damagePerRound;
    private int duration;

    public Poison(int damagePerRound, int duration) {
        this.damagePerRound = damagePerRound;
        this.duration = duration;
    }

    @Override
    public void applyEffect(Character character) {
        character.receiveDamage(this.getDamagePerRound());
        System.out.println(character.getName() + " recebebeu efeito: " + this.getClass().getSimpleName() + ", causando: " +
                +this.getDamagePerRound() + " de dano");
        System.out.println(character.getName() + " pontos de vida restantes: " + character.getLifePoints());
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