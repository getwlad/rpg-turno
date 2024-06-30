package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;

import java.util.List;

public class Enemies {
    public static void turn(Enemy enemy, Character target) {
        enemy.applyEffect();
        if (enemy.getLifePoints() > 0) {
            if (!enemy.hasStunEffect() && !enemy.hasSleepEffect()) {
                double randomChoice = Math.random();
                if (randomChoice < 0.5 && enemy.getMagicPoints() >= 30) {
                    int spellChoice = (int) (Math.random() * 4);
                    switch (spellChoice) {
                        case 0:
                            enemy.castSpell("burn", target);
                            break;
                        case 1:
                            enemy.castSpell("poison", target);
                            break;
                        case 2:
                            enemy.castSpell("stun", target);
                            break;
                        case 3:
                            enemy.castSpell("sleep", target);
                            break;
                    }

                } else {
                    enemy.attack(target);
                }

                if (target.getLifePoints() <= 0) {
                    System.out.println(target.getName() + " foi derrotado.");
                }
            } else {
                System.out.println(enemy.getName() + " está impedido de atacar.");
                System.out.println("Alterando para o próximo inimigo (se houver)");
            }
        }
    }
}
