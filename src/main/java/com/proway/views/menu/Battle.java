package com.proway.views.menu;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.enemies.Goblin;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.effects.Sleep;
import com.proway.app.effects.Stun;
import com.proway.dao.CharacterDAO;
import com.proway.dao.EnemyDAO;
import com.proway.util.ScanValidation;
import com.proway.views.battle.Start;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    private final static EnemyDAO enemyDAO = new EnemyDAO();
    public static void show(Scanner scanner, Character selectedCharacter, List<Character> characters, CharacterDAO characterDAO) {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Procurar inimigo(s)");
            System.out.println("2. Descansar - Salvar jogo");
            System.out.println("9. Voltar");

            int option = ScanValidation.getValidIntInput(scanner);

            switch (option) {
                case 1:
                    List<Enemy> enemies = new ArrayList<>();
                    int monsterSize = new Random().nextInt(4) + 1;
                    for (int i = 0; i < monsterSize; i++) {
                        Enemy enemy = enemyDAO.generateRandomEnemy(selectedCharacter.getLevel());
                        enemies.add(enemy);
                        System.out.println("Você encontrou um(a) " + enemy.getName() + "!");
                    }

                    // Iniciar batalha com o personagem selecionado e o inimigo aleatório
                    Start battle = new Start(selectedCharacter, enemies);

                    battle.begin(scanner);

                    if (!enemies.isEmpty()) {
                        show(scanner, selectedCharacter, characters, characterDAO);
                    }
                    break;
                case 2:
                    // Descansar (salvar o jogo e retornar à tela de seleção de personagem)
                    characterDAO.updateCharacter(selectedCharacter);
                    System.out.println("Jogo salvo. Descansando...");
                    return;
                case 9:
                    return; // Voltar para o menu anterior
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }


}
