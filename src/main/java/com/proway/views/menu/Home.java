package com.proway.views.menu;

import com.proway.app.characters.interfaces.Character;
import com.proway.dao.CharacterDAO;
import com.proway.util.ScanValidation;

import java.util.List;
import java.util.Scanner;

public class Home {

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        CharacterDAO characterDAO = new CharacterDAO();

        System.out.println("Bem-vindo ao RPG WayTSystem World");
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Novo Jogo");
            System.out.println("2. Carregar Jogo");
            System.out.println("9. Sair");

            int menuChoice = ScanValidation.getValidIntInput(scanner);

            switch (menuChoice) {
                case 1:
                    NewGame.show(scanner, characterDAO);
                    break;
                case 2:
                    loadGame(scanner, characterDAO);
                    break;
                case 9:
                    System.out.println("Saindo do jogo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private static void loadGame(Scanner scanner, CharacterDAO characterDAO) {
        List<Character> characters = characterDAO.loadCharacters();
        if (characters.isEmpty()) {
            System.out.println("Nenhum progresso salvo encontrado.");
            return;
        }
        SelectCharacter.show(scanner, characters, characterDAO);
    }


}
