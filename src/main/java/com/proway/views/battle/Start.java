package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;

import java.util.List;
import java.util.Scanner;

public class Start {
    private final Character character;
    private final List<Enemy> enemies;

    public Start(Character character, List<Enemy> enemies) {
        this.character = character;
        this.enemies = enemies;
    }

    public void begin(Scanner scanner) {
        System.out.println();
        Enemy enemy = this.getEnemies().getFirst();
        System.out.println(enemy.getEnemyInfo());
        while (true) {
            Player.turn(this.getCharacter(), scanner, enemy);

            System.out.println();

            if (enemy.getLifePoints() <= 0) {
                System.out.println(enemy.getName() + " foi derrotado.");
                character.gainExperience(enemy);
                this.enemies.remove(enemy);

                if (this.getEnemies().isEmpty()) {
                    System.out.println("Jogador venceu a batalha!");
                    this.getCharacter().setLifePoints(this.getCharacter().getLife());
                    break;
                }

                enemy = this.getEnemies().getFirst();
                System.out.println(enemy.getEnemyInfo());
            }


            Enemies.turn(enemy, this.getCharacter());

            if (this.getCharacter().getLifePoints() <= 0) {
                System.out.println("Monstros venceram a batalha!");
                this.getCharacter().setLifePoints(this.getCharacter().getLife());
                break;
            }

        }
    }



    public Character getCharacter() {
        return character;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
