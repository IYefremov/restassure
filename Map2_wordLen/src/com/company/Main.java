//
//Given an array of strings, return a Map<String, Integer> containing a key for every different string in the array,
//        and the value is that string's length.
//
//
//        wordLen(["a", "bb", "a", "bb"]) → {"bb": 2, "a": 1}
//        wordLen(["this", "and", "that", "and"]) → {"that": 4, "and": 3, "this": 4}
//        wordLen(["code", "code", "code", "bug"]) → {"code": 4, "bug": 3}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String [] str = {"this", "and", "that", "and"};

        for (Map.Entry<String, Integer> m : wordLen(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, Integer> wordLen(String[] strings) {
        Map<String, Integer> myMap = new HashMap<>();
        for (String s : strings){
            if (!myMap.containsKey(s)){
                myMap.put(s, s.length());
            }
        }

        return myMap;
    }
}
