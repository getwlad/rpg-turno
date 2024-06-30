package com.proway.views.menu;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.player.Player;
import com.proway.dao.EnemyDAO;
import com.proway.util.ScanValidation;
import com.proway.views.battle.Start;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.proway.views.menu.Home.characterDAO;

public class CharacterMenu {
    private final static EnemyDAO enemyDAO = new EnemyDAO();

    public static void show(Scanner scanner, int id) throws SQLException {
        while (true) {
            Player selectedCharacter = characterDAO.loadCharacterById(id);
            System.out.println(selectedCharacter.getCharacterInfo(true));
            System.out.println("Escolha uma opção:");
            System.out.println("1. Procurar inimigo(s)");
            System.out.println("2. Inventário");
            System.out.println("3. Apagar personagem");
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

                    continue;
                case 2:
                    selectedCharacter = characterDAO.updateCharacter(selectedCharacter);
                    InventoryMenu.show(scanner, selectedCharacter);
                    break;
                case 3:
                    System.out.println("Todo progresso com este personagem será perdido, tem certeza? Digite SIM para SIM");
                    String choice = ScanValidation.getValidStringInput(scanner);
                    if (choice.equalsIgnoreCase("sim")) {
                        characterDAO.deleteById(selectedCharacter.getId());
                        Home.updateCharacters();
                    }
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
