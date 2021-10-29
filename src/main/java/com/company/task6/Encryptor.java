package com.company.task6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encryptor {
    private static final List<String> alphabet;
    private static final String[][] table;

    static {
        alphabet = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split(""));
        table = new String[alphabet.size()][alphabet.size()];
        for (int i = 0, j = 0; i < alphabet.size(); i++, j++) {
            List<String> list = new ArrayList<>();
            list.addAll(alphabet.subList(j, alphabet.size()));
            list.addAll(alphabet.subList(0, j));
            list.toArray(table[i]);
        }
    }

    public static void encrypt(String directory, String output, String key) throws IOException {
        StringBuilder directoryString = new StringBuilder();
        readDirectory(new File(directory), "", directoryString);
        String fullKey = createFullKey(directoryString.length(), key);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < directoryString.length(); i++) {
            char textChar = directoryString.charAt(i);
            String keyChar = String.valueOf(fullKey.charAt(i));
            if (Character.isLetter(textChar)) {
                encryptedText.append(table[alphabet.indexOf(String.valueOf(textChar))][alphabet.indexOf(keyChar)]);
            } else {
                encryptedText.append(textChar);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.append(encryptedText.toString());
        }
        deleteDirectory(directory);
    }

    public static void decrypt(String input, String directory, String key) throws IOException {
        String decryptedText = decryptText(input, key);
        String[] directories = decryptedText.split("NOT_EMP");
        for (String dir : directories) {
            createDirectory(directory, dir);
        }
    }

    private static void createDirectory(String dirPath, String directory) throws IOException {
        StringBuilder resultPath = new StringBuilder(dirPath);
        String[] dirParts = directory.split("&");
        for (int i = 0; i < dirParts.length; i++) {
            String type = dirParts[i].startsWith("FOLD") ? "folder" : dirParts[i].startsWith("FILE") ? "file" : "";
            if ("folder".equals(type)) {
                new File(resultPath + "\\" + dirParts[i].substring(4)).mkdirs();
                resultPath.append("\\").append(dirParts[i].substring(4));
            } else if ("file".equals(type)) {
                new File(resultPath + "\\" + dirParts[i].substring(4));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultPath + "\\" + dirParts[i].substring(4), StandardCharsets.UTF_8))) {
                    writer.append(dirParts[i+1]);
                }
            }
        }
    }

    private static void readDirectory(File dir, String curDir, StringBuilder dirStr) throws IOException {
        curDir = curDir + dir.getName() + "/";
        if (!dirStr.isEmpty()) {
            dirStr.append("NOT_EMP");
        }

        for (String dirName : curDir.split("/")) {
            dirStr.append("FOLD").append(dirName).append("&");
        }

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    dirStr.append("FILE").append(file.getName()).append("&")
                            .append(Files.readString(Paths.get(file.getAbsolutePath()))).append("&");
                }
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    readDirectory(file, curDir, dirStr);
                }
            }
        }
    }

    private static void deleteDirectory(String dirPath) {
        File directory = new File(dirPath);
        for (File f : directory.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else {
                deleteDirectory(f.getPath());
            }
        }
        directory.delete();
    }

    private static String createFullKey(int length, String key) {
        StringBuilder result = new StringBuilder(key);
        while (result.length() < length) {
            result.append(key);
        }

        return result.substring(0, length);
    }

    private static String decryptText(String input, String key) throws IOException {
        StringBuilder result = new StringBuilder();
        String encryptedText = Files.readString(Paths.get(input));
        String fullKey = createFullKey(encryptedText.length(), key);

        for (int i = 0; i < fullKey.length(); i++) {
            int index = alphabet.indexOf(String.valueOf(fullKey.charAt(i)));
            if (!Character.isLetter(encryptedText.charAt(i))) {
                result.append(encryptedText.charAt(i));
                continue;
            }
            for (int j = 0; j < alphabet.size(); j++) {
                if (table[index][j].equals(String.valueOf(encryptedText.charAt(i)))) {
                    result.append(alphabet.get(j));
                }
            }
        }
        return result.toString();
    }
}