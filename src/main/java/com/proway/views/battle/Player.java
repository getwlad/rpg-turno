package com.proway.views.battle;

import com.proway.app.characters.enemies.Enemy;
import com.proway.app.characters.interfaces.Character;
import com.proway.util.ScanValidation;

import java.util.List;
import java.util.Scanner;

public class Player {

    public static void turn(Character character, Scanner scanner, Enemy enemy) {
        character.applyEffect();
        if (character.getLifePoints() > 0) {
            if (!character.hasStunEffect() && !character.hasSleepEffect()) {
                System.out.println("\n"+ character.getName() + ", escolha sua ação:");
                System.out.println("1. Atacar");
                System.out.println("2. Lançar Magia");
                int escolhaAcao = ScanValidation.getValidIntInput(scanner);

                switch (escolhaAcao) {
                    case 1:
                        character.attack(enemy);
                        break;
                    case 2:
                        System.out.println("Escolha uma magia para lançar:");
                        System.out.println("1. Burn (10 MP)");
                        System.out.println("2. Poison (10 MP)");
                        System.out.println("3. Stun (15 MP)");
                        System.out.println("4. Sleep (20 MP)");

                        int escolhaMagia = scanner.nextInt();
                        scanner.nextLine(); // Consumir nova linha

                        switch (escolhaMagia) {
                            case 1:
                                character.castSpell("burn", enemy);
                                break;
                            case 2:
                                character.castSpell("poison", enemy);
                                break;
                            case 3:
                                character.castSpell("stun", enemy);
                                break;
                            case 4:
                                character.castSpell("sleep", enemy);
                                break;
                            default:
                                System.out.println("Escolha inválida!");
                                break;
                        }
                        break;
                    default:
                        System.out.println("Escolha inválida!");
                        break;
                }
            } else {
                System.out.println(character.getName() + " está impedido de atacar.");
            }
        }
    }

}
