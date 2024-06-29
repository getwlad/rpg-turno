package com.proway.views.menu;

import com.proway.app.characters.interfaces.Character;
import com.proway.dao.CharacterDAO;
import com.proway.util.ScanValidation;

import java.util.List;
import java.util.Scanner;

public class SelectCharacter {


    public static void show(Scanner scanner, List<Character> characters, CharacterDAO characterDAO) {
        while (true) {
            System.out.println("Escolha seu personagem:");
            for (Character character : characters) {
                System.out.println(character.getId() + ". " + character.getName()
                        + " (" + character.getClass().getSimpleName() + ")"
                        + " Lvl: " + character.getLevel());
            }
            System.out.println("0. Adicionar novo personagem");
            System.out.println("9. Voltar");

            int choice = ScanValidation.getValidIntInput(scanner);

            switch (choice) {
                case 0:
                    NewGame.show(scanner, characterDAO);
                    break;
                case 9:
                    return;
                default:
                    Character selectedCharacter = characters.stream()
                            .filter(c -> c.getId() == choice)
                            .findFirst()
                            .orElse(null);
                    if (selectedCharacter == null) {
                        System.out.println("Personagem não encontrado.");
                        break;
                    }

                    System.out.println("Você selecionou: " + selectedCharacter.getName());
                    System.out.println(selectedCharacter.getCharacterInfo());

                    Battle.show(scanner, selectedCharacter, characters, characterDAO);
                    break;
            }
        }
    }


}
