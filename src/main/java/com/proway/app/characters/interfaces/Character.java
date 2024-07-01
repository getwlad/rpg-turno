package com.proway.app.characters.interfaces;

import com.proway.app.characters.skills.interfaces.Skill;
import com.proway.app.characters.skills.interfaces.SkillType;
import com.proway.app.effects.*;
import com.proway.app.effects.interfaces.Effect;
import com.proway.app.items.Armor;
import com.proway.app.items.Weapon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Character {
    private String name;
    private int id;
    private int lifePoints;
    private int strength;
    private int defense;
    private int magic;
    private int magicPoints;
    private int magicDefense;
    private int criticalDamage;
    private int level;
    private int experience;
    private int experienceToLevelUp;
    private int life;
    private SkillType weakness;
    private List<Skill> skills;
    private Weapon weapon;
    private Armor armor;
    private int strengthBonus = 0;
    private int magicBonus = 0;
    private int lifePointsBonus = 0;
    private int criticalDamageBonus = 0;
    private int defenseBonus = 0;
    private int magicDefenseBonus = 0;

    private List<Effect> effects = new ArrayList<>();

    protected static final int initialXPRequired = 100;
    protected static final double STAT_UP_MULTIPLIER = 1.1;
    protected static final int BASE_STAT_INCREASE = 2;

    public Character(String name, int lifePoints, int strength, int defense, int magic, int magicPoints,
                     int magicDefense, int criticalDamage, int level, int experience,
                     int experienceToLevelUp, Armor armor, Weapon weapon, SkillType weakness, List<Skill> skills) {
        this.name = name;
        this.magic = magic + (level * BASE_STAT_INCREASE);
        this.life = lifePoints + (level * BASE_STAT_INCREASE);
        this.lifePoints = lifePoints + (level * BASE_STAT_INCREASE);
        this.strength = strength + (level * BASE_STAT_INCREASE);
        this.defense = defense + (level * BASE_STAT_INCREASE);
        this.magicPoints = magicPoints + (level * BASE_STAT_INCREASE);
        this.magicDefense = magicDefense + (level * BASE_STAT_INCREASE);
        this.criticalDamage = criticalDamage + (level * BASE_STAT_INCREASE);
        this.level = level;
        this.experience = experience + (level * 2);
        this.experienceToLevelUp = experienceToLevelUp;
        this.armor = null;
        this.weapon = null;
        if (armor != null) {
            armor.equip(this);
            this.armor = armor;
        }
        if (weapon != null) {
            weapon.equip(this);
            this.weapon = weapon;
        }
        this.weakness = weakness;
        this.skills = skills;
    }


    public void receiveDamage(int damage) {
        int remainingLifePoints = this.getLifePoints() - damage;
        this.setLifePoints(Math.max(remainingLifePoints, 0));
    }

    public void attack(Character target, Skill skill) {
        int damage = calcDamage(target, skill);
        if (isCriticalHit()) {
            damage *= (int) getCriticalDamageMultiplier();
            System.out.println("Dano crítico!");
        }
        if (damage > 0) {
            target.receiveDamage(damage);
            System.out.println(this.getName() + " ataca " + target.getName() + " com " + skill.getName() + " causando " + damage + " de dano.");
            System.out.println(target.getName() + ", pontos de vida restantes: " + target.getLifePoints());
            return;
        }
        System.out.println("O ataque de " + this.getName() + " não teve efeito! " + target.getName() + " resistiu.");
    }

    public void attack(Character target) {
        int damage = calctStrengthDamage(target);
        if (isCriticalHit()) {
            damage *= (int) getCriticalDamageMultiplier();
            System.out.println("Dano crítico!");
        }
        if (damage > 0) {
            target.receiveDamage(damage);
            System.out.println(this.getName() + " ataca " + target.getName() + " causando " + damage + " de dano.");
            System.out.println(target.getName() + ", pontos de vida restantes: " + target.getLifePoints());
        }
        System.out.println("O ataque de " + this.getName() + " não teve efeito! " + target.getName() + " resistiu.");
    }

    protected int calcDamage(Character target, Skill skill) {
        int baseDamage = skill.getDamage();
        baseDamage += (calcMagicDamage(target) / 4);
        double damageModifier = 1.0;

        if (skill.getSkillType() == target.getWeakness() && baseDamage > 0) {
            damageModifier *= 1.3;
            System.out.println(this.getName() + " explorou a fraqueza de " + target.getName() + "!");
        }

        baseDamage = Math.max((int) (baseDamage * damageModifier), 0);

        return baseDamage;
    }

    public void heal(int points) {
        this.lifePoints = Math.min(this.getLifePoints() + points, this.getLife());
    }

    private boolean isCriticalHit() {
        return Math.random() < getCriticalHitChance();
    }

    private double getCriticalHitChance() {
        return 0.1 + (double) getCriticalDamage() / 100.0;
    }

    private double getCriticalDamageMultiplier() {
        return 1.0 + (double) getCriticalDamageBonus() / 100.0;
    }

    protected int calctStrengthDamage(Character target) {
        return Math.max((this.getStrength() + this.getStrengthBonus()) - (target.getDefense() + target.getDefenseBonus()), 0);
    }

    protected int calcMagicDamage(Character target) {
        return Math.max((this.getMagic() + this.getMagicBonus()) - (target.getMagicDefenseBonus() + target.getMagicDefenseBonus()), 0);
    }


    public boolean castSpell(String spellName, Character target) {
        int spellCost = 0;
        Effect spellEffect = null;
        int damage = calcMagicDamage(target);
        if (isCriticalHit()) {
            damage *= (int) getCriticalDamageMultiplier();
            System.out.println("Se conjurada, esta magia elevará com nível crítico!");
        }

        switch (spellName.toLowerCase()) {
            case "burn":
                spellCost = 10;
                spellEffect = new Burn(damage, 3);
                break;
            case "poison":
                spellCost = 10;
                spellEffect = new Poison(damage, 4);
                break;
            case "heal":
                spellCost = 15;
                spellEffect = new Heal((int) (5 + (level * 1.1)), 1);
                break;
            case "stun":
                spellCost = 20;
                spellEffect = new Stun(1);
                break;
            case "sleep":
                spellCost = 30;
                spellEffect = new Sleep(2);
                break;

            default:
                System.out.println("Magia desconhecida.");
                return false;
        }

        if (this.getMagicPoints() >= spellCost) {
            this.setMagicPoints(this.getMagicPoints() - spellCost);
            String message = damage <= 0 ? target.getName() + " resistiu a mágia." : this.getName() + " lançou "
                    + spellName + " em " + target.getName() + ".";
            System.out.println(message);
            if (damage > 0) {
                target.addEffect(spellEffect);
            }
            System.out.println("Pontos de magia restantes: " + this.getMagicPoints());
            return true;
        } else {
            System.out.println("Pontos de magia insuficientes.");
            return false;
        }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void applyEffect() {
        List<Effect> effectsToRemove = new ArrayList<>();
        for (Effect effect : effects) {
            effect.applyEffect(this);
            effect.reduceDuration();
            if (!effect.isActive()) {
                effectsToRemove.add(effect);
            }

        }
        effects.removeAll(effectsToRemove);
    }


    public boolean hasStunEffect() {
        for (Effect effect : this.getEffects()) {
            if (effect instanceof Stun && effect.isActive()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSleepEffect() {
        for (Effect effect : this.getEffects()) {
            if (effect instanceof Sleep && effect.isActive()) {
                return true;
            }
        }
        return false;
    }

    public String getCharacterInfo(boolean player) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(name).append("\n");
        if (player) {
            sb.append("Experiência atual: ").append(experience).append("\n");
            sb.append("Experiência necessária para próximo nível: ").append(experienceToLevelUp).append("\n");
        }
        sb.append("Level: ").append(level).append("\n");
        sb.append("Vida: ").append(lifePoints).append(" (Bonus: ").append(getLifePointsBonus()).append(")\n");
        sb.append("Força: ").append(strength).append(" (Bonus: ").append(getStrengthBonus()).append(")\n");
        sb.append("Magia: ").append(magic).append(" (Bonus: ").append(getMagicBonus()).append(")\n");
        sb.append("MP: ").append(magicPoints).append(" (Bonus: ").append(getMagicPoints()).append(")\n");
        sb.append("Defesa Mágica: ").append(magicDefenseBonus).append(" (Bonus: ").append(getMagicDefenseBonus()).append(")\n");
        sb.append("Dano Crítico: ").append(criticalDamage).append(" (Bonus: ").append(getCriticalDamageBonus()).append(")\n");
        sb.append("Defesa: ").append(defense).append(" (Bonus: ").append(getDefenseBonus()).append("\n");
        sb.append("Armor: ").append(this.getArmor() != null ? this.getArmor().getName() : "").append("\n");
        sb.append("Weapon: ").append(this.getWeapon() != null ? this.getWeapon().getName() : "").append("\n");
        return sb.toString();
    }


}
