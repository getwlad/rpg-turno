package com.proway.app.miscellany;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Shop {

    private List<Item> itemsForSale;

    public Shop() {
        itemsForSale = new ArrayList<>();
    }

    public void browseShop(Character player) {
        System.out.println("Itens à venda:");
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            System.out.println(i + 1 + ". " + item.getName() + " - " + item.getPrice() + " moedas");
        }
    }

    public boolean purchaseItem(int index, Player player) {
        if (index >= 0 && index < itemsForSale.size()) {
            Item item = itemsForSale.get(index);
            if (player.getGold() >= item.getPrice()) {
                player.getInventory().addItem(item);
                player.setGold(player.getGold() - item.getPrice());
                System.out.println("Você comprou um " + item.getName() + "!");
                return true;
            } else {
                System.out.println("Você não tem dinheiro suficiente para comprar este item.");
            }
        } else {
            System.out.println("Índice inválido.");
        }
        return false;
    }
}
