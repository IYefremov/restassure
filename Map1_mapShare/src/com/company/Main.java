//Modify and return the given map as follows: if the key "a" has a value, set the key "b" to have that same value.
// In all cases remove the key "c", leaving the rest of the map unchanged.
//
//
//        mapShare({"a": "aaa", "b": "bbb", "c": "ccc"}) → {"a": "aaa", "b": "aaa"}
//        mapShare({"b": "xyz", "c": "ccc"}) → {"b": "xyz"}
//        mapShare({"a": "aaa", "c": "meh", "d": "hi"}) → {"a": "aaa", "b": "aaa", "d": "hi"}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Map<String, String> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("c", "meh");
        map.put("d", "hi");

        for (Map.Entry<String, String> m : map.entrySet()){
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }
        System.out.println();
        for (Map.Entry<String, String> m : mapShare(map).entrySet()){
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }
    }
    //mapShare({"a": "aaa", "c": "meh", "d": "hi"}) → {"a": "aaa", "b": "aaa", "d": "hi"}
    public static Map<String, String> mapShare(Map<String, String> map) {
        if (map.get("c") != null){
            map.remove("c");
        }
        if (map.get("a") != null){
            if (map.get("b") != null) {
                map.replace("b", map.get("a"));
            } else {
                map.put("b", map.get("a") );
            }
        }
        return map;
    }

}
