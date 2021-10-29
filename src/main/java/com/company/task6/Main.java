package com.company.task6;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String s;
        do {
            System.out.println("Введите 1 для щифрования директории");
            System.out.println("введите 2 для расшифровки директории");
            System.out.println("нажмите любую другую клавишу для выхода");
            s = scanner.nextLine();
            switch (s) {
                case "1" -> {
                    System.out.println("Введите путь к директории: ");
                    String directory = scanner.nextLine();
                    System.out.println("Введите путь к файлу для шифра: ");
                    String output = scanner.nextLine();
                    System.out.println("Введите ключ: ");
                    String key = scanner.nextLine();
                    Encryptor.encrypt(directory, output, key);
                }
                case "2" -> {
                    System.out.println("Введите путь к зашифрованному файлу: ");
                    String file = scanner.nextLine();
                    System.out.println("Введите путь к новой директории: ");
                    String directory = scanner.nextLine();
                    System.out.println("Введите ключ: ");
                    String key = scanner.nextLine();
                    Encryptor.decrypt(file, directory, key);
                }
                default -> s = "0";
            }
        } while (!"0".equals(s));
    }
}
