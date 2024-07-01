package com.proway.app.characters.player;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.items.Armor;
import com.proway.app.items.Weapon;
import com.proway.app.miscellany.Inventory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Character {
    private Inventory inventory;
    private int gold;

    public Player(String name, int lifePoints, int strength, int defense, int magic, int magicPoints, int magicDefense,
                  int criticalDamage, int level, int experience, int experienceToLevelUp, Inventory inventory,
                  Armor armor, Weapon weapon, int gold) {
        super(name, lifePoints, strength, defense, magic, magicPoints, magicDefense, criticalDamage, level,
                experience, experienceToLevelUp, armor, weapon);
        this.inventory = inventory;
        this.gold = gold;
    }

    public void gainExperience(Enemy enemy) {
        System.out.println("Você ganhou: " + enemy.getExperience() + " pontos de experiência");
        int expReward = enemy.getExpReward();
        this.setExperience(this.getExperience() + expReward);
        if (this.getExperience() >= this.getExperienceToLevelUp()) {
            levelUp();
        }
    }

    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(Math.max(this.getExperience() - this.getExperienceToLevelUp(), 0));
        this.setExperienceToLevelUp((int) (this.getExperienceToLevelUp() * (1 + Math.log(this.getLevel() + 1) / 10)));
        System.out.println();
        System.out.println("---Parabéns---");
        System.out.println("Você subiu de level, level atual: " + this.getLevel());
        System.out.println();
        increaseStats();
        if (this.getExperience() >= this.getExperienceToLevelUp()) {
            levelUp();
        }
    }


    private void increaseStats() {
        this.setLife((int) (this.getLife() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setStrength((int) (this.getStrength() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setDefense((int) (this.getDefense() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setMagicPoints((int) (this.getMagicPoints() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setMagicDefense((int) (this.getMagicDefense() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setCriticalDamage((int) (this.getCriticalDamage() * STAT_UP_MULTIPLIER + BASE_STAT_INCREASE));
        this.setLifePoints(this.getLife());
    }

}
