package com.proway.app.items;

import com.proway.app.characters.interfaces.Character;
import com.proway.app.items.enums.Rarity;
import com.proway.app.items.enums.Type;

public class Armor extends Item {
    public Armor(int id, String name, Type type, Rarity rarity, int level, int strengthBonus, int magicBonus,
                 int lifePointsBonus, int criticalDamageBonus, int defenseBonus, int magicDefenseBonus, Character owner) {
        super(id, name, type, rarity, level, strengthBonus, magicBonus, lifePointsBonus,
                criticalDamageBonus, defenseBonus, magicDefenseBonus, owner);
    }

    public Armor(Item item) {
        super(item.getId(), item.getName(), item.getType(), item.getRarity(), item.getLevel(),
                item.getStrengthBonus(), item.getMagicBonus(),
                item.getLifePointsBonus(), item.getCriticalDamageBonus(), item.getDefenseBonus(),
                item.getMagicDefenseBonus(), item.getOwner());
    }

}
