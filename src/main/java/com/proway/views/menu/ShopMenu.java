package com.proway.views.menu;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import com.proway.app.miscellany.Shop;
import com.proway.util.ScanValidation;

import java.sql.SQLException;
import java.util.Scanner;

public class ShopMenu {

    public static void show(Scanner scanner, Player player) throws SQLException {
        Shop shop = new Shop();

        while (true) {
            System.out.println("-------");
            System.out.println("Loja");
            System.out.println("-------");
            System.out.println("1. Ver itens à venda");
            System.out.println("2. Comprar item");
            System.out.println("3. Vender item");
            System.out.println("9. Sair");
            System.out.println("-------");

            int option = ScanValidation.getValidIntInput(scanner);

            switch (option) {
                case 1:
                    shop.browseShop();
                    break;
                case 2:
                    purchaseItem(scanner, player, shop);
                    break;
                case 3:
                    sellItem(scanner, player, shop);
                    break;
                case 9:
                    System.out.println("Saindo da loja...");
                    CharacterMenu.show(scanner, player.getId());
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void purchaseItem(Scanner scanner, Player player, Shop shop) throws SQLException {
        mostrarSaldo(player);
        shop.browseShop();

        System.out.println("Digite o ID do item que deseja comprar:");
        int id = ScanValidation.getValidIntInput(scanner);

        Item item = shop.getItemsForSale().stream()
                .filter(itemSale -> itemSale.getId() == id)
                .findFirst()
                .orElse(null);

        if (item != null) {
            if (shop.purchaseItem(item, player)) {
                System.out.println("Comprado com sucesso!");
                mostrarSaldo(player);
                Home.characterDAO.updateCharacter(player);
            }
        } else {
            System.out.println("Item não encontrado na loja.");
        }
    }

    private static void mostrarSaldo(Player player) {
        System.out.println("Saldo atual: " + player.getGold());
    }

    private static void sellItem(Scanner scanner, Player player, Shop shop) throws SQLException {
        for (Item item : player.getInventory().getUnequippedItems()){
            item.print(true, false);
        }

        System.out.println("Digite o ID do item que deseja vender:");
        int id = ScanValidation.getValidIntInput(scanner);

        Item item = player.getInventory().getUnequippedItems().stream()
                .filter(itemSale -> itemSale.getItemInvId() == id)
                .findFirst()
                .orElse(null);

        if(item != null){
            shop.sellItem(item, player);
            System.out.println("Vendido com sucesso!");
            mostrarSaldo(player);
            Home.characterDAO.updateCharacter(player);
        }
        else{
            System.out.println("Item não encontrado no inventário.");
        }

    }
}

