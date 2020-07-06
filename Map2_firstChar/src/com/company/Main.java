//
//Given an array of non-empty strings, return a Map<String, String> with a key for every different first
//        character seen, with the value of all the strings starting with that character appended together in
//        the order they appear in the array.
//
//
//        firstChar(["salt", "tea", "soda", "toast"]) → {"s": "saltsoda", "t": "teatoast"}
//        firstChar(["aa", "bb", "cc", "aAA", "cCC", "d"]) → {"a": "aaaAA", "b": "bb", "c": "cccCC", "d": "d"}
//        firstChar([]) → {}.

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String[] str = {"aa", "bb", "cc", "aAA", "cCC", "d"};

        for (Map.Entry<String, String> m : firstChar(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, String> firstChar(String[] strings) {
        Map<String, String> myMap = new HashMap<>();
        for (String s : strings) {
            if (!myMap.containsKey(s.substring(0, 1))) {
                myMap.put(s.substring(0, 1), s);
            } else {
                myMap.replace(s.substring(0, 1), myMap.get(s.substring(0, 1)).concat(s));
            }
        }
        return myMap;
    }

}
