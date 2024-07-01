package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.player.Player;
import com.proway.views.menu.Home;
import com.proway.views.menu.SelectCharacter;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BattleView {
    private final Player character;
    private final List<Enemy> enemies;

    public BattleView(Player character, List<Enemy> enemies) {
        this.character = character;
        this.enemies = enemies;
    }

    public void begin(Scanner scanner) throws SQLException {
        System.out.println();
        Enemy enemy = this.getEnemies().getFirst();
        System.out.println(enemy.getCharacterInfo(false));
        while (true) {
            PlayerView.turn(this.getCharacter(), scanner, enemy);

            System.out.println();

            checkResult(enemy, scanner);

            EnemiesView.turn(enemy, this.getCharacter(), this.getEnemies());

            checkResult(enemy, scanner);
        }
    }

    public void checkResult(Enemy enemy, Scanner scanner) throws SQLException {
        verificarSeRestaInimigos(scanner);

        if (enemy.getLifePoints() <= 0) {
            System.out.println(enemy.getName() + " foi derrotado.");
            character.gainExperience(enemy);
            enemy.dropItem(this.getCharacter());
            this.enemies.remove(enemy);

            verificarSeRestaInimigos(scanner);

            enemy = this.getEnemies().getFirst();
            System.out.println(enemy.getCharacterInfo(false));
        }

        if (this.getCharacter().getLifePoints() <= 0) {
            System.out.println("Monstros venceram a batalha!");
            this.getCharacter().setLifePoints(this.getCharacter().getLife());
            SelectCharacter.show(scanner);
        }

    }

    public void verificarSeRestaInimigos(Scanner scanner) throws SQLException {
        if (this.getEnemies().isEmpty()) {
            System.out.println("Jogador venceu a batalha!");
            this.getCharacter().setLifePoints(this.getCharacter().getLife());
            Home.characterDAO.updateCharacter(character);
            SelectCharacter.show(scanner);
        }
    }

    public Player getCharacter() {
        return character;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
