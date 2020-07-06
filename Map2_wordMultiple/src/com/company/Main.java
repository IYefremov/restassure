//
//Given an array of strings, return a Map<String, Boolean> where each different string is a key and its
//        value is true if that string appears 2 or more times in the array.
//
//
//        wordMultiple(["a", "b", "a", "c", "b"]) → {"a": true, "b": true, "c": false}
//        wordMultiple(["c", "b", "a"]) → {"a": false, "b": false, "c": false}
//        wordMultiple(["c", "c", "c", "c"]) → {"c": true}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String[] str = {"a", "b", "a", "c", "b"};

        for (Map.Entry<String, Boolean> m : wordMultiple(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, Boolean> wordMultiple(String[] strings) {
        Map <String, Boolean> myMap = new HashMap<>();
        for (String s : strings){
            if (!myMap.containsKey(s)){
                myMap.put(s, false);
            } else {
                myMap.replace(s, true);
            }
        }
        return myMap;
    }
}
