package com.proway.views.menu;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import com.proway.dao.CharacterDAO;
import com.proway.dao.InventoryDAO;
import com.proway.dao.InventoryXItemDAO;
import com.proway.dao.ItemDAO;
import com.proway.database.DatabaseUtil;
import com.proway.util.ScanValidation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Home {
    private final static Connection conn = DatabaseUtil.connect();
    public final static ItemDAO itemDAO = new ItemDAO(conn);
    public final static InventoryXItemDAO inventoryXItemDAO = new InventoryXItemDAO(conn, itemDAO);
    public final static InventoryDAO inventoryDAO = new InventoryDAO(conn, inventoryXItemDAO);
    public final static CharacterDAO characterDAO = new CharacterDAO(conn, inventoryDAO);
    public final static List<Item> items = itemDAO.getAllItems();
    private static List<Player> characters = characterDAO.loadCharacters();

    public static void show() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao RPG WayTSystem World");
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Novo Jogo");
            System.out.println("2. Carregar Jogo");
            System.out.println("3. Listar todos items");
            System.out.println("9. Sair");

            int menuChoice = ScanValidation.getValidIntInput(scanner);

            switch (menuChoice) {
                case 1:
                    NewGame.show(scanner, true);
                    break;
                case 2:
                    loadGame(scanner);
                    break;
                case 3:
                    items.forEach((item -> item.print(false, false)));
                    break;
                case 9:
                    System.out.println("Saindo do jogo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private static void loadGame(Scanner scanner) throws SQLException {
        List<Player> characters = characterDAO.loadCharacters();
        if (characters.isEmpty()) {
            System.out.println("Nenhum progresso salvo encontrado.");
            return;
        }
        SelectCharacter.show(scanner);
    }

    public static List<Player> getCharacters() {
        return characters;
    }

    public static void updateCharacters() {
        characters = characterDAO.loadCharacters();
    }

}
