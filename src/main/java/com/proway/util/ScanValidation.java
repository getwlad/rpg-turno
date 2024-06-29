package com.proway.util;

import java.util.Scanner;

public class ScanValidation {
    public static int getValidIntInput(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Entrada inválida. Digite um número inteiro: ");
                scanner.nextLine();
            }
        }
    }

    public static String getValidStringInput(Scanner scanner) {
        int minLength = 3;
        while (true) {
            String input = scanner.nextLine();
            if (input.length() >= minLength) {
                return input;
            } else {
                System.out.printf("Entrada inválida. Digite uma palavra com pelo menos %d caracteres: ", minLength);
            }
        }
    }

}
