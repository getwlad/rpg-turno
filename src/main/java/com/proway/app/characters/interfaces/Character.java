package com.proway.app.characters.interfaces;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.effects.Burn;
import com.proway.app.effects.Poison;
import com.proway.app.effects.Sleep;
import com.proway.app.effects.Stun;
import com.proway.app.effects.interfaces.Effect;

import java.util.ArrayList;
import java.util.List;

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

    private List<Effect> effects = new ArrayList<>();

    private static final double LEVEL_UP_MULTIPLIER = 1.2;
    private static final double STAT_UP_MULTIPLIER = 1.01;
    protected static final int BASE_STAT_INCREASE = 2;

    public Character(String name, int lifePoints, int strength, int defense, int magic, int magicPoints,
                     int magicDefense, int criticalDamage, int level, int experience,
                     int experienceToLevelUp) {
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
        this.experience = experience + (level * BASE_STAT_INCREASE);
        this.experienceToLevelUp = experienceToLevelUp + (level * BASE_STAT_INCREASE);
    }


    public void receiveDamage(int damage) {
        int remainingLifePoints = this.getLifePoints() - damage;
        this.setLifePoints(Math.max(remainingLifePoints, 0));
    }

    public void attack(Character target) {
        int damage = calcDamage(target);
        target.receiveDamage(damage);
        System.out.println(this.getName() + " ataca " + target.getName() + " causando " + damage + " de dano.");
        System.out.println(target.getName() + ", pontos de vida restantes: " + target.getLifePoints());
    }

    protected int calcDamage(Character target) {
        return Math.max(this.getStrength() - target.getDefense(), 0);
    }

    public void defend() {
        this.defense += 5;
    }

    public void castSpell(String spellName, Character target) {
        int spellCost = 0;
        Effect spellEffect = null;

        switch (spellName.toLowerCase()) {
            case "burn":
                spellCost = 10;
                spellEffect = new Burn(this.getMagic(), 3);
                break;
            case "poison":
                spellCost = 10;
                spellEffect = new Poison(this.getMagic(), 4);
                break;
            case "stun":
                spellCost = 20;
                spellEffect = new Stun(this.getMagic(), 1);
                break;
            case "sleep":
                spellCost = 30;
                spellEffect = new Sleep(this.getMagic(), 2);
                break;
            default:
                System.out.println("Magia desconhecida.");
                return;
        }

        if (this.getMagicPoints() >= spellCost) {
            this.setMagicPoints(this.getMagicPoints() - spellCost);
            if (spellEffect.getDamagePerRound() <= target.getMagicDefense() ){
                System.out.println(target.getName() + " resistiu a mágia.");
                return;
            }
            target.addEffect(spellEffect);
            System.out.println(this.getName() + " lançou " + spellName + " em " + target.getName() + ".");
        } else {
            System.out.println("Pontos de magia insuficientes.");
        }
        System.out.println("Pontos de magia restantes: " + this.getMagicPoints());
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

    public void gainExperience(Enemy enemy) {
        System.out.println("Você ganhou: " + enemy.getExperience() + " pontos de experiência");
        int expReward = enemy.getExpReward();
        this.experience += expReward;
        if (this.experience >= this.experienceToLevelUp) {
            levelUp();
        }
    }

    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(0);
        this.experienceToLevelUp *= LEVEL_UP_MULTIPLIER;
        System.out.println();
        System.out.println("---Parabéns---");
        System.out.println("Você subiu de level, level atual: " + this.getLevel());
        System.out.println();
        increaseStats();
    }

    private void increaseStats() {
        this.life = (int) (this.life * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.strength = (int) (this.strength * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.defense = (int) (this.defense * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.magicPoints = (int) (this.magicPoints * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.magicDefense = (int) (this.magicDefense * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.criticalDamage = (int) (this.criticalDamage * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE);
        this.setLifePoints(this.life);
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

    public String getCharacterInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(name).append("\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("Experiência atual: ").append(experience).append("\n");
        sb.append("Experiência necessária para próximo nível: ").append(experienceToLevelUp).append("\n");
        sb.append("Vida: ").append(lifePoints).append("\n");
        sb.append("Força: ").append(strength).append("\n");
        sb.append("Defesa: ").append(defense).append("\n");
        sb.append("Pontos Mágicos: ").append(magicPoints).append("\n");
        sb.append("Defesa Mágica: ").append(magicDefense).append("\n");
        sb.append("Dano Crítico: ").append(criticalDamage).append("\n");

        return sb.toString();
    }

    public String getEnemyInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Classe: ").append(name).append("\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("Vida: ").append(lifePoints).append("\n");
        sb.append("Força: ").append(strength).append("\n");
        sb.append("Defesa: ").append(defense).append("\n");
        sb.append("Pontos Mágicos: ").append(magicPoints).append("\n");
        sb.append("Defesa Mágica: ").append(magicDefense).append("\n");
        sb.append("Dano Crítico: ").append(criticalDamage).append("\n");

        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getMagicPoints() {
        return this.magicPoints;
    }

    public int getMagicDefense() {
        return this.magicDefense;
    }

    public int getCriticalDamage() {
        return this.criticalDamage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getExperienceToLevelUp() {
        return this.experienceToLevelUp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMagicPoints(int magicPoints) {
        this.magicPoints = magicPoints;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public void setCriticalDamage(int criticalDamage) {
        this.criticalDamage = criticalDamage;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setExperienceToLevelUp(int experienceToLevelUp) {
        this.experienceToLevelUp = experienceToLevelUp;
    }

    public List<Effect> getEffects() {
        return this.effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
