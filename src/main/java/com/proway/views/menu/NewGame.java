package com.proway.views.menu;

import com.proway.app.characters.player.Archer;
import com.proway.app.characters.player.Mage;
import com.proway.app.characters.player.Player;
import com.proway.app.characters.player.Warrior;
import com.proway.util.ScanValidation;

import java.sql.SQLException;
import java.util.Scanner;

import static com.proway.views.menu.Home.characterDAO;

public class NewGame {
    public static void show(Scanner scanner, boolean newGame) throws SQLException {
        if (newGame && !(Home.getCharacters().isEmpty())) {
            System.out.println("Todo seu progresso será perdido, tem certeza? Digite SIM caso tenha certeza");
            String choice = ScanValidation.getValidStringInput(scanner);
            if (choice.equalsIgnoreCase("sim")) {
                characterDAO.deleteAll();
            } else {
                return;
            }
        }

        System.out.print("Digite o nome do seu personagem: ");
        String name = ScanValidation.getValidStringInput(scanner);
        System.out.println("Escolha uma classe:");
        System.out.println("1. Guerreiro");
        System.out.println("2. Mago");
        System.out.println("3. Arqueiro");

        int classChoice = ScanValidation.getValidIntInput(scanner);

        Player character = null;
        switch (classChoice) {
            case 1:
                character = new Warrior(name);
                break;
            case 2:
                character = new Mage(name);
                break;
            case 3:
                character = new Archer(name);
                break;
            default:
                System.out.println("Escolha inválida!");
                return;
        }
        character.setExperience(0);
        characterDAO.saveCharacter(character);
        System.out.println("Personagem criado: " + character.getName());
        Home.updateCharacters();
        SelectCharacter.show(scanner);
    }
}
