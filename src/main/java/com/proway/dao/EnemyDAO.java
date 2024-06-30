package com.proway.dao;

import com.proway.app.characters.enemies.*;

import java.util.Random;

public class EnemyDAO {
    private final Random random = new Random();

    public Enemy generateRandomEnemy(int charLevel) {
        int levelRange = 5;
        int enemyLevel = charLevel + Math.max(random.nextInt(levelRange * 2) - levelRange, 1);
        Class[] enemyTypes = {
                Goblin.class, Orc.class, Skeleton.class, Slime.class, Bat.class,
                Spider.class, Wolf.class, Troll.class, Wizard.class
        };

        int randomIndex = random.nextInt(enemyTypes.length);
        Class<? extends Enemy> enemyClass = enemyTypes[randomIndex];
        Enemy enemy;
        try {
            enemy = enemyClass.getConstructor(int.class).newInstance(enemyLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return enemy;

    }
}
