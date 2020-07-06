
//Given an array of non-empty strings, create and return a Map<String, String> as follows: for each string add
//        its first character as a key with its last character as the value.
//
//
//        pairs(["code", "bug"]) → {"b": "g", "c": "e"}
//        pairs(["man", "moon", "main"]) → {"m": "n"}
//        pairs(["man", "moon", "good", "night"]) → {"g": "d", "m": "n", "n": "t"}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String[] str = {"man", "moon", "good", "night"};

        for (Map.Entry<String, String> m : pairs(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, String> pairs(String[] strings) {
        Map<String, String> myMap = new HashMap<>();
        for (String s : strings) {
            if (!myMap.containsKey(s)) {
                myMap.put(s.substring(0, 1), s.substring(s.length() - 1));
            }
        }
        return myMap;
    }

}
