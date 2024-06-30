package com.proway.service;

import com.proway.app.items.Armor;
import com.proway.app.items.Item;
import com.proway.app.items.Weapon;
import com.proway.app.items.enums.Type;
import com.proway.views.menu.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemService {
    private final List<Item> items = Home.items;

    public List<Weapon> getWeapons() {
        List<Weapon> weapons = items.stream()
                .filter(item -> !(item.getType().equals(Type.ARMOR)))
                .map(Weapon::new)
                .toList();
        return new ArrayList<>(weapons);
    }

    public List<Armor> getArmors() {
        List<Armor> armors = items.stream()
                .filter(item -> item.getType().equals(Type.ARMOR))
                .map(Armor::new)
                .toList();
        return new ArrayList<>(armors);
    }

    public Weapon getRandomWeapon() {
        List<Weapon> weapons = getWeapons();
        if (Math.random() < 0.7) {
            return weapons.get(new Random().nextInt(weapons.size()));
        } else {
            return null;
        }
    }

    public Armor getRandomArmor() {
        List<Armor> armors = getArmors();
        if (Math.random() < 0.5) {
            return armors.get(new Random().nextInt(armors.size()));
        } else {
            return null;
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
