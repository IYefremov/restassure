//The classic word-count algorithm: given an array of strings, return a Map<String, Integer> with a key
//        for each different string, with the value the number of times that string appears in the array.
//
//
//        wordCount(["a", "b", "a", "c", "b"]) → {"a": 2, "b": 2, "c": 1}
//        wordCount(["c", "b", "a"]) → {"a": 1, "b": 1, "c": 1}
//        wordCount(["c", "c", "c", "c"]) → {"c": 4}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String[] str = {"a", "b", "a", "c", "b"};

        for (Map.Entry<String, Integer> m : wordCount(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, Integer> wordCount(String[] strings) {
        Map<String, Integer> myMap = new HashMap<>();
        for (String s : strings){
            if (!myMap.containsKey(s)){
                myMap.put(s, 1);
            } else {
                int count = myMap.get(s);
                myMap.replace(s, ++count);
            }
        }

        return myMap;

    }
}
