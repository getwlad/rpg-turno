package com.proway.views.menu;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Player;
import com.proway.util.ScanValidation;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SelectCharacter {
    public static void show(Scanner scanner) throws SQLException {
        List<Player> characters = Home.getCharacters();
        Collections.sort(characters, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        while (true) {
            System.out.println("Escolha seu personagem:");
            int i = 1;
            for (Character character : characters) {
                System.out.println(i + ". " + character.getName()
                        + " (" + character.getClass().getSimpleName() + ")"
                        + " Lvl: " + character.getLevel());
                i++;
            }
            System.out.println(i + ". Adicionar novo personagem");
            System.out.println(0 + ". Voltar");

            int choice = ScanValidation.getValidIntInput(scanner);

            if (choice > 0 && choice <= characters.size()) {
                Player selectedCharacter = characters.get(choice - 1);
                System.out.println("Você selecionou: " + selectedCharacter.getName());
                CharacterMenu.show(scanner, selectedCharacter.getId());
            } else if (choice == 0) {
                return;
            } else if (choice == characters.size() + 1) {
                NewGame.show(scanner, false);
            } else {
                System.out.println("Personagem não encontrado.");
            }
        }
    }


}