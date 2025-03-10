package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;

import java.util.List;
import java.util.Random;

public class EnemiesView {
    public static void turn(Enemy enemy, Character target, List<Enemy> enemies) {
        enemy.applyEffect();
        if (enemy.getLifePoints() > 0) {
            if (!enemy.hasStunEffect() && !enemy.hasSleepEffect()) {
                double randomChoice = Math.random();

                if (randomChoice < 0.5 &&
                        enemy.getLifePoints() <= (enemy.getLife() * 0.3) && enemy.getMagicPoints() >= 15) {
                    enemy.castSpell("heal", enemy);
                    return;
                }

                if (randomChoice < 0.3 && enemy.getMagicPoints() >= 30) {
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
                }
                else if(randomChoice >= 0.3 && randomChoice <= 0.6 ){
                    enemy.attack(target,
                            enemy.getSkills().get(Math.max(new Random().nextInt(enemy.getSkills().size()), 0)));
                }
                else {
                    enemy.attack(target);
                }

                if (enemy.getLifePoints() <= (enemy.getLife() * 0.2) && Math.random() < 0.3) {
                    System.out.println(enemy.getName() + " fugiu da batalha!");
                    enemies.remove(enemy);
                    if (enemies.isEmpty()) {
                        return;
                    }
                    enemy = enemies.getFirst();
                    System.out.println(enemy.getName() + " entrou na batalha!");
                    System.out.println(enemy.getCharacterInfo(false));
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
