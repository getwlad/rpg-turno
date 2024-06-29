package com.proway.views.menu;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Archer;
import com.proway.app.characters.player.Mage;
import com.proway.app.characters.player.Warrior;
import com.proway.dao.CharacterDAO;
import com.proway.util.ScanValidation;

import java.util.List;
import java.util.Scanner;

public class NewGame {
    public static void show(Scanner scanner, CharacterDAO characterDAO) {
        System.out.print("Digite o nome do seu personagem: ");
        String name =  ScanValidation.getValidStringInput(scanner);
        System.out.println("Escolha uma classe:");
        System.out.println("1. Guerreiro");
        System.out.println("2. Mago");
        System.out.println("3. Arqueiro");

        int classChoice = ScanValidation.getValidIntInput(scanner);

        Character character = null;
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
        characterDAO.saveCharacter(character);
        System.out.println("Personagem criado: " + character.getName());

        List<Character> characters = characterDAO.loadCharacters();

        SelectCharacter.show(scanner, characters, characterDAO);
    }
}
