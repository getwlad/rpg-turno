package com.proway.views.menu;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import com.proway.util.ScanValidation;

import java.sql.SQLException;
import java.util.Scanner;

public class InventoryMenu {
    public static void show(Scanner scanner, Player selectedCharacter) throws SQLException {
        while (true) {
            System.out.println("\nMenu de Inventário:");
            System.out.println("1. Ver todos os itens");
            System.out.println("2. Ver itens equipados");
            System.out.println("3. Equipar item");
            System.out.println("4. Desequipar item");
            System.out.println("9. Voltar");

            int option = ScanValidation.getValidIntInput(scanner);

            switch (option) {
                case 1:
                    selectedCharacter.getInventory().print();
                    break;
                case 2:
                    showEquippedItems(selectedCharacter);
                    break;
                case 3:
                    equipItem(scanner, selectedCharacter);
                    break;
                case 4:
                    unequipItem(scanner, selectedCharacter);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void showEquippedItems(Player selectedCharacter) {
        System.out.println("\nItens Equipados:");
        for (Item item : selectedCharacter.getInventory().getEquippedItems()) {
            item.print();
        }
    }

    private static void equipItem(Scanner scanner, Player selectedCharacter) throws SQLException {
        System.out.println("\nEquipar Item:");
        for (Item item : selectedCharacter.getInventory().getUnequippedItems()) {
            item.print();
        }
        System.out.print("Digite o ID do item que deseja equipar: ");
        int itemId = ScanValidation.getValidIntInput(scanner);

        Item itemToEquip = selectedCharacter.getInventory().getItemById(itemId);
        if (itemToEquip != null) {
            if (selectedCharacter.getInventory().equipItem(itemToEquip)) {
                System.out.println("Item " + itemToEquip.getName() + " equipado com sucesso!");
                Home.characterDAO.updateCharacter(selectedCharacter);
            }
        } else {
            System.out.println("Item com ID " + itemId + " não encontrado no inventário.");
        }
    }

    private static void unequipItem(Scanner scanner, Player selectedCharacter) throws SQLException {
        if (selectedCharacter.getInventory().getEquippedItems().size() <= 0) {
            System.out.println("Nenhum item equipado.");
            return;
        }
        System.out.println("\nDesequipar Item:");
        showEquippedItems(selectedCharacter);
        System.out.print("Digite o ID do item que deseja desequipar: ");
        int itemId = ScanValidation.getValidIntInput(scanner);

        Item itemToUnequip = selectedCharacter.getInventory().getEquippedItemById(itemId);
        if (itemToUnequip != null) {
            selectedCharacter.getInventory().unequipItem(itemToUnequip);
            System.out.println("Item " + itemToUnequip.getName() + " desequipado com sucesso!");
            Home.characterDAO.updateCharacter(selectedCharacter);
        } else {
            System.out.println("Item com ID " + itemId + " não encontrado entre os itens equipados.");
        }
    }
}
