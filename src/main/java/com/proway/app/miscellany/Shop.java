package com.proway.app.miscellany;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import com.proway.app.items.enums.Rarity;
import com.proway.views.menu.Home;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Shop {

    private List<Item> itemsForSale;

    public Shop() {
        this.itemsForSale = new ArrayList<>(Home.items.stream()
                .filter(item -> !(item.getRarity().equals(Rarity.EPIC))).toList());
    }

    public void browseShop() {
        System.out.println("Itens à venda:");
        System.out.println("-------");
        for (Item item : itemsForSale) {
            item.print(false, true);
        }
        System.out.println("-------");
    }

    public boolean sellItem(Item item, Player player) {
        System.out.println();
        player.setGold(player.getGold() + (item.getPrice() / 2));
        player.getInventory().getUnequippedItems().remove(item);
        return true;
    }

    public boolean purchaseItem(Item item, Player player) {
        if (player.getGold() >= item.getPrice()) {
            item.setOwner(player);
            player.getInventory().addItem(item);
            player.setGold(player.getGold() - item.getPrice());
            System.out.println("Você comprou um(a) " + item.getName() + "!");
            return true;
        }

        System.out.println("Você não tem dinheiro suficiente para comprar este item.");

        return false;
    }
}
