package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;
import com.proway.app.characters.skills.interfaces.Skill;
import com.proway.util.ScanValidation;

import java.util.List;
import java.util.Scanner;

public class PlayerView {


    public static void turn(Character character, Scanner scanner, Enemy enemy) {
        character.applyEffect();
        if (character.getLifePoints() > 0) {
            if (!character.hasStunEffect() && !character.hasSleepEffect()) {
                boolean actionSuccessful = false;

                while (!actionSuccessful) {
                    System.out.println("\n" + character.getName() + ", escolha sua ação:");
                    System.out.println("1. Atacar");
                    System.out.println("2. Lançar Magia");
                    System.out.println("3. Usar habilidade");
                    int escolhaAcao = ScanValidation.getValidIntInput(scanner);

                    switch (escolhaAcao) {
                        case 1:
                            character.attack(enemy);
                            actionSuccessful = true;
                            break;
                        case 2:
                            actionSuccessful = castSpell(character, scanner, enemy);
                            break;
                        case 3:
                            actionSuccessful = useSkill(character, scanner, enemy);
                            break;
                        default:
                            System.out.println("Escolha inválida!");
                            break;
                    }
                }
            } else {
                System.out.println(character.getName() + " está impedido de atacar.");
            }
        }
    }

    private static boolean useSkill(Character character, Scanner scanner, Enemy enemy) {
        List<Skill> skills = character.getSkills();

        if (skills.isEmpty()) {
            System.out.println(character.getName() + " não possui habilidades para usar.");
            return false;
        }

        System.out.println("Escolha uma habilidade para usar:");
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            System.out.println((i + 1) + ". " + skill.getName() + " - Dano base: " + skill.getDamage());
        }

        int escolhaHabilidade = ScanValidation.getValidIntInput(scanner);

        Skill chosenSkill = skills.get(escolhaHabilidade - 1);
        character.attack(enemy, chosenSkill);

       return true;
    }


    private static boolean castSpell(Character character, Scanner scanner, Enemy enemy) {
        System.out.println("Escolha uma magia para lançar:");
        System.out.println("1. Burn (10 MP)");
        System.out.println("2. Poison (10 MP)");
        System.out.println("3. Heal (15 MP)");
        System.out.println("4. Stun (15 MP)");
        System.out.println("5. Sleep (20 MP)");

        int escolhaMagia = ScanValidation.getValidIntInput(scanner);

        boolean spellCast = false;

        switch (escolhaMagia) {
            case 1:
                spellCast = character.castSpell("burn", enemy);
                break;
            case 2:
                spellCast = character.castSpell("poison", enemy);
                break;
            case 3:
                spellCast = character.castSpell("heal", character);
                break;
            case 4:
                spellCast = character.castSpell("stun", enemy);
                break;
            case 5:
                spellCast = character.castSpell("sleep", enemy);
                break;
            default:
                System.out.println("Escolha inválida!");
                break;
        }

        if (!spellCast) {
            System.out.println("Você não pode lançar essa magia. Tente novamente:");
        }

        return spellCast;
    }

}
