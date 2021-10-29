package com.company.task4;

import java.util.HashMap;
import java.util.Map;

public class CharsConfig {
    private static final Map<String, String> mapWithChars = new HashMap<>();
    private static final Map<Integer, String> mapWithCode = new HashMap<>();

    static {
        mapWithChars.put("а", "a");
        mapWithChars.put("е", "e");
        mapWithChars.put("о", "o");
        mapWithChars.put("р", "p");
        mapWithChars.put("с", "c");
        mapWithChars.put("х", "x");
        mapWithChars.put("у", "y");
        mapWithChars.put("А", "A");
        mapWithChars.put("Е", "E");
        mapWithChars.put("О", "O");
        mapWithChars.put("Р", "P");
        mapWithChars.put("С", "C");
        mapWithChars.put("Х", "X");
        mapWithChars.put("Н", "H");
        mapWithChars.put("М", "M");
        int code = 192;
        for (int i = 1040; i<1072;i++){
            mapWithCode.put(code, String.valueOf((char)i));
            mapWithCode.put(code + 32, String.valueOf((char)(i + 32)));
            code++;
        }
    }
    public static String getCharEng(String charRus){
        return mapWithChars.get(charRus);
    }

    public static String getCharRus(String charEng){
        return mapWithChars.keySet()
                .stream()
                .filter(key -> mapWithChars.get(key).equals(charEng))
                .findFirst()
                .orElse("");
    }

    public static String getCharByCode(int code){
        return mapWithCode.get(code);
    }
}