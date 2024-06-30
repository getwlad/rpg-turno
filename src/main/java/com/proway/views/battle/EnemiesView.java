package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;

import java.util.List;

public class EnemiesView {
    public static void turn(Enemy enemy, Character target, List<Enemy> enemies) {
        enemy.applyEffect();
        if (enemy.getLifePoints() > 0) {
            if (!enemy.hasStunEffect() && !enemy.hasSleepEffect()) {
                double randomChoice = Math.random();

                if (randomChoice < 0.5 && enemy.getMagicPoints() >= 30) {
                    int spellChoice = (int) (Math.random() * 5);
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
                        case 4:
                            enemy.castSpell("heal", enemy);
                            break;
                    }
                } else {
                    enemy.attack(target);
                }

                if (enemy.getLifePoints() <= (enemy.getLife() * 0.2) && Math.random() < 0.3) {
                    System.out.println(enemy.getName() + " fugiu da batalha!");
                    enemies.remove(enemy);
                } else {
                    if (target.getLifePoints() <= 0) {
                        System.out.println(target.getName() + " foi derrotado.");
                    }
                }
            } else {
                System.out.println(enemy.getName() + " está impedido de atacar.");
                System.out.println("Alterando para o próximo inimigo (se houver)");
            }
        }
    }
}
