package com.company.task4;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String s;
        do {
            System.out.println("Введите 1 для сокрытия строки");
            System.out.println("введите 2 для извлечения строки");
            System.out.println("нажмите любую другую клавишу для выхода");
            s = scanner.nextLine();
            switch (s) {
                case "1" -> {
                    System.out.println("Введите путь к файлу с сообщением: ");
                    File file = new File(scanner.nextLine());
                    System.out.println("Введите путь к файлу-контейнеру: ");
                    String container = scanner.nextLine();
                    System.out.println("Введите путь к выходному файлу: ");
                    String output = scanner.nextLine();

                    String binaryString = HideHelper.readString(file);
                    HideHelper.hideString(binaryString, container, output);
                    System.out.println("Сообщение успешно скрыто.\n");
                }
                case "2" -> {
                    System.out.println("Введите путь к файлу-контейнеру: ");
                    System.out.println(HideHelper.extractString(scanner.nextLine()) + "\n");
                }
                default -> s = "0";
            }
        } while(!"0".equals(s));
    }
}
