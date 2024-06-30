package com.proway.views.menu;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.enemies.Goblin;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Player;
import com.proway.app.effects.Sleep;
import com.proway.app.effects.Stun;
import com.proway.app.items.Item;
import com.proway.dao.CharacterDAO;
import com.proway.dao.EnemyDAO;
import com.proway.dao.ItemDAO;
import com.proway.util.ScanValidation;
import com.proway.views.battle.Start;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.proway.views.menu.Home.characterDAO;
import static com.proway.views.menu.Home.itemDAO;

public class Battle {
    private final static EnemyDAO enemyDAO = new EnemyDAO();
    public static void show(Scanner scanner, Player selectedCharacter, List<Player> characters) throws SQLException {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Procurar inimigo(s)");
            System.out.println("2. Inventário");
            System.out.println("3. Descansar - Salvar jogo");
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

                    battle.begin(scanner, itemDAO);

                    if (!enemies.isEmpty()) {
                        show(scanner, selectedCharacter, characters);
                    }
                    break;
                case 2:
                    selectedCharacter.getInventory().print();
                    break;
                case 3:
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
