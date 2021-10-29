package com.company.task1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //C:\Users\dputiatinskii\Desktop\file1.txt
    public static void main(String[] args) throws IOException {
        File file;
        int offset;
        String directoryPath;

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Укажите путь к файлу: ");
            file = new File(scanner.nextLine());

            System.out.println("Укажите смещение: ");
            offset = Integer.parseInt(scanner.nextLine());

            System.out.println("Укажите путь к нужной директории: ");
            directoryPath = scanner.nextLine();
        }

        File directory = new File(directoryPath);
        String signature = readSignature(file, offset);
        System.out.println("Сигнатура: " + signature);
        System.out.println("Файлы:");
        List<String> filePaths = searchFiles(directory, signature);
        filePaths.forEach(System.out::println);
    }

    public static String readSignature(File f, int offset) throws IOException {
        StringBuilder str = new StringBuilder();
        byte[] arr = Files.readAllBytes(f.toPath());
        for(int i = offset; i < offset+16; i++){
            str.append(arr[i]).append(" ");
        }
        return str.toString();
    }

    public static List<String> searchFiles(File directory, String signature) throws IOException {
        return Files.walk(Paths.get(directory.getPath()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> Objects.requireNonNull(readBytes(file)).contains(signature))
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    public static String readBytes(File f) {

        try (FileInputStream stream = new FileInputStream(f)) {
            byte[] arr = Files.readAllBytes(f.toPath());
            StringBuilder str = new StringBuilder();
            for (Byte b : arr) {
                str.append(b).append(" ");
            }
            return str.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении");
        }
        return null;
    }
}