package com.proway.app.characters.enemies;

import com.proway.app.characters.enemies.enums.MonsterType;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Player;
import com.proway.app.characters.skills.interfaces.Skill;
import com.proway.app.characters.skills.interfaces.SkillType;
import com.proway.app.items.Armor;
import com.proway.app.items.Item;
import com.proway.app.items.Weapon;
import com.proway.app.items.enums.Rarity;
import com.proway.app.items.enums.Type;
import com.proway.service.ItemService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Enemy extends Character {
    private final MonsterType type;
    private final int expReward;
    private Item equippedItem;
    private static final ItemService itemService = new ItemService();

    public Enemy(String name, MonsterType type, int lifePoints, int strength, int defense, int magic, int magicPoints,
                 int magicDefense, int criticalDamage, int level, int experience, int experienceToLevelUp, int expReward,
        SkillType weakness, List<Skill> skills) {
        super(name, lifePoints, strength, defense, magic, magicPoints, magicDefense, criticalDamage, level, experience, experienceToLevelUp,
                getRandomArmor(), getRandomWeapon(), weakness, skills);
        this.type = type;
        this.expReward = expReward;
    }

    private static Armor getRandomArmor() {
        return itemService.getRandomArmor();
    }

    private static Weapon getRandomWeapon() {
        return itemService.getRandomWeapon();
    }


    public void dropItem(Player player) {
        Random random = new Random();
        List<Item> items = itemService.getItems();
        List<Item> droppableItems = new ArrayList<>();

        for (Item item : items) {
            switch (item.getRarity()) {

                case Rarity.COMMON:
                    if (random.nextInt(100) < 50) {
                        droppableItems.add(item);
                    }
                    break;
                case Rarity.RARE:
                    if (random.nextInt(100) < 30) {
                        droppableItems.add(item);
                    }
                    break;
                case Rarity.SUPER_RARE:
                    if (random.nextInt(100) < 10) {
                        droppableItems.add(item);
                    }
                    break;
                case Rarity.EPIC:
                    if (random.nextInt(100) < 5) {
                        droppableItems.add(item);
                    }
                    break;

            }
        }

        if (!droppableItems.isEmpty()) {
            int randomIndex = Math.max(1, random.nextInt(droppableItems.size()));
            Item droppedItem = droppableItems.get(randomIndex);
            if (droppedItem.getType().equals(Type.ARMOR)) {
                droppedItem = new Armor(droppedItem);
            } else {
                droppedItem = new Weapon(droppedItem);
            }
            droppedItem.setOwner(player);
            if (player.getInventory().addItem(droppedItem)) {
                System.out.println("Você encontrou um " + droppedItem.getName() + "!");
            } else {
                System.out.println("Sua mochila está cheia. O item foi perdido.");
            }
        } else {
            System.out.println("Nenhum item foi encontrado.");
        }
    }

}
