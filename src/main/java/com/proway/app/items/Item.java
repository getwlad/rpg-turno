package com.proway.app.items;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.player.Archer;
import com.proway.app.characters.player.Mage;
import com.proway.app.characters.player.Player;
import com.proway.app.characters.player.Warrior;
import com.proway.app.items.enums.Rarity;
import com.proway.app.items.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private int id;
    private int itemInvId = 0;
    private String name;
    private Type type;
    private Rarity rarity;
    private int level;
    private int price;
    private int strengthBonus;
    private int magicBonus;
    private int lifePointsBonus;
    private int criticalDamageBonus;
    private int defenseBonus;
    private int magicDefenseBonus;
    private boolean equipped;
    private Character owner;

    public Item(int id, String name, Type type, Rarity rarity, int level,
                int strengthBonus, int magicBonus, int lifePointsBonus, int criticalDamageBonus, int defenseBonus,
                int magicDefenseBonus, Character owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.level = level;
        this.strengthBonus = strengthBonus;
        this.magicBonus = magicBonus;
        this.lifePointsBonus = lifePointsBonus;
        this.criticalDamageBonus = criticalDamageBonus;
        this.defenseBonus = defenseBonus;
        this.magicDefenseBonus = magicDefenseBonus;
        this.equipped = false;
        this.owner = owner;
    }


    public boolean canEquipByClass(Character character) {
        if (this.type.equals(Type.ARMOR)) {
            return true;
        } else {
            return switch (this.type.toString().toLowerCase()) {
                case "sword" -> character instanceof Warrior || isMonterClass(character);
                case "staff" -> character instanceof Mage || isMonterClass(character);
                case "bow" -> character instanceof Archer || isMonterClass(character);
                case "special" -> true;
                default -> false;
            };
        }
    }

    private boolean isMonterClass(Character character) {
        return character instanceof Enemy;
    }

    public boolean equip(Character character) {
        boolean isArmor = this instanceof Armor;
        boolean canEquip = isArmor ? character.getArmor() == null : character.getWeapon() == null;
        if (!canEquip) {
            System.out.println("Você já possuí um item do mesmo tipo equipado");
            return false;
        }
        if (!canEquipByClass(character)) {
            System.out.println("Sua classe não permite equipar este item");
            return false;
        }
        this.equipped = true;


        character.setStrengthBonus(character.getStrengthBonus() + this.strengthBonus);
        character.setMagicBonus(character.getMagicBonus() + this.magicBonus);
        character.setLifePointsBonus(character.getLifePointsBonus() + this.lifePointsBonus);
        character.setCriticalDamageBonus(character.getCriticalDamageBonus() + this.criticalDamageBonus);
        character.setDefenseBonus(character.getDefenseBonus() + this.defenseBonus);
        character.setMagicDefenseBonus(character.getMagicDefenseBonus() + this.magicDefenseBonus);
        return true;
    }

    public boolean unequip(Player character) {
        if (!this.equipped || this.owner != character) {
            return false;
        }
        boolean isArmor = this instanceof Armor;


        this.equipped = false;
        this.owner = null;

        character.setStrengthBonus(character.getStrengthBonus() - this.strengthBonus);
        character.setMagicBonus(character.getMagicBonus() - this.magicBonus);
        character.setLifePointsBonus(character.getLifePointsBonus() - this.lifePointsBonus);
        character.setCriticalDamageBonus(character.getCriticalDamageBonus() - this.criticalDamageBonus);
        character.setDefenseBonus(character.getDefenseBonus() - this.defenseBonus);
        character.setMagicDefenseBonus(character.getMagicDefenseBonus() - this.magicDefenseBonus);

        if (isArmor) {
            character.setArmor(null);
        } else {
            character.setWeapon(null);
        }
        return true;
    }

    public void print() {
        System.out.println(" - Nome: " + name);
        System.out.println("    id: " + itemInvId);
        System.out.println("    Tipo: " + type);
        System.out.println("    Raridade: " + rarity);
        System.out.println("    Level: " + level);
        if (this.getOwner() != null) {
            System.out.println("    Equipado: " + (equipped ? "Sim" : "Não"));
        }

        if (!type.equals(Type.ARMOR)) {
            System.out.println("    Bonus de força: +" + strengthBonus);
            System.out.println("    Bonus mágico: +" + magicBonus);
        }
        System.out.println("    Bonus de vida: +" + lifePointsBonus);
        System.out.println("    Bonus de defesa: +" + defenseBonus);
        System.out.println("    Bonus de  mágica: +" + magicDefenseBonus);
        System.out.println("    Bonus de dano crítico: +" + criticalDamageBonus);

    }

}
