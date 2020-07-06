//Given an array of strings, return a Map<String, Integer> containing a key for every different string in the array,
// always with the value 0. For example the string "hello" makes the pair "hello":0. We'll do more complicated counting later,
// but for this problem the value is simply 0.
//
//
//        word0(["a", "b", "a", "b"]) → {"a": 0, "b": 0}
//        word0(["a", "b", "a", "c", "b"]) → {"a": 0, "b": 0, "c": 0}
//        word0(["c", "b", "a"]) → {"a": 0, "b": 0, "c": 0}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
//        Map<String, Integer> map = new HashMap<>();
//        map.put("a", 1);
//        map.put("b", 1);
//        map.put("a", 21);
//        map.put("b", 31);
        String [] str = {"a", "b", "a", "c", "b"};

        for (Map.Entry<String, Integer> m : word0(str).entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue() + " ");
        }

    }

    public static Map<String, Integer> word0(String[] strings) {
        Map<String, Integer> myNewMap = new HashMap<>();
        for (String s : strings){
            if (!myNewMap.containsKey(s)){
                myNewMap.put(s, 0);
            }
        }
        return myNewMap;
    }

}
