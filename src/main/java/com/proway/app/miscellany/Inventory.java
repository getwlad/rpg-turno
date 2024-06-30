package com.proway.app.miscellany;

import com.proway.app.characters.player.Player;
import com.proway.app.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Inventory {
    private int id;
    private List<Item> items;
    private int maxCapacity;
    private Player owner;

    public Inventory(int maxCapacity) {
        this.items = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public boolean addItem(Item item) {
        if (items.size() < maxCapacity) {
            this.items = new ArrayList<>(this.items);
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public void print() {
        System.out.println("Items:");
        if (items.isEmpty()) {
            System.out.println("  Sem nenhum item");
        } else {
            for (Item item : items) {
                item.print();
            }
        }

        System.out.println();
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public boolean equipItem(Item item) {
        return item.equip(owner);
    }

    public boolean unequipItem(Item item) {
        return item.unequip(owner);
    }

    public List<Item> getEquippedItems() {
        List<Item> equippedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.isEquipped()) {
                equippedItems.add(item);
            }
        }
        return equippedItems;
    }

    public List<Item> getUnequippedItems() {
        List<Item> unequippedItems = new ArrayList<>();
        for (Item item : items) {
            if (!item.isEquipped()) {
                unequippedItems.add(item);
            }
        }
        return unequippedItems;
    }

    public Item getItemById(int itemId) {
        return items.stream().filter(item -> item.getItemInvId() == itemId).findFirst().orElse(null);
    }

    public Item getEquippedItemById(int itemId) {
        return this.getEquippedItems().stream().filter(item -> item.getItemInvId() == itemId).findFirst().orElse(null);
    }


}