package com.company.task4;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HideHelper {

    public static void hideString(String binaryMessage, String container, String output) throws IOException {
        char[] bits = binaryMessage.toCharArray();
        List<String> text = Arrays.asList(new String(Files.readAllBytes(Paths.get(container)), StandardCharsets.UTF_8).split(""));

        for (int i = 0, j = 0; i < text.size(); i++) {
            String charElem = text.get(i);
            if (CharsConfig.getCharEng(charElem) != null && bits[j] == '1') {
                text.set(i, CharsConfig.getCharEng(charElem));
                j++;
            } else if (CharsConfig.getCharEng(charElem) != null && bits[j] == '0') {
                j++;
            }

            if (j == bits.length) {
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, StandardCharsets.UTF_8))) {
            for (String c : text) {
                writer.append(c);
            }
        }
    }

    public static String readString(File file) throws IOException {
        StringBuilder binayString = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
        List<Integer> byteList = new ArrayList<>();

        while (streamReader.ready()) {
            byteList.add(streamReader.read());
        }

        for (int byteElem : byteList) {
            binayString.append(String.format("%8s", Integer.toBinaryString(byteElem)).replace(' ', '0'));
        }

        return binayString.toString();
    }

    public static String extractString(String container) throws IOException {
        StringBuilder binaryString = new StringBuilder();
        Path path = Paths.get(container);

        List<String> charsList = Arrays.asList(new String(Files.readAllBytes(Paths.get(container)), StandardCharsets.UTF_8).split(""));
        String resultBinaryString = "";
        int ctr = 0;
        for (String c : charsList) {
            if (ctr == 8) {
                resultBinaryString = binaryString.substring(0, binaryString.length() - 8);
                break;
            }

            if (!"".equals(CharsConfig.getCharRus(c))) {
                binaryString.append("1");
                ctr = 0;
            } else if (CharsConfig.getCharEng(c) != null) {
                binaryString.append("0");
                ctr++;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < resultBinaryString.length(); i += 8) {
            if (i + 8 > binaryString.length()) {
                break;
            }
            int code = Integer.parseInt(binaryString.substring(i, i + 8), 2);
            result.append(code >= 192 && code <= 257 ? CharsConfig.getCharByCode(code) : String.valueOf((char) code));
        }
        return result.toString();
    }
}