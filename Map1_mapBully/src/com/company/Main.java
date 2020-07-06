//Modify and return the given map as follows: if the key "a" has a value,
// set the key "b" to have that value, and set the key "a" to have the value "".
// Basically "b" is a bully, taking the value and replacing it with the empty string.
//
//
//        mapBully({"a": "candy", "b": "dirt"}) → {"a": "", "b": "candy"}
//        mapBully({"a": "candy"}) → {"a": "", "b": "candy"}
//        mapBully({"a": "candy", "b": "carrot", "c": "meh"}) → {"a": "", "b": "candy", "c": "meh"}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Map<String, String> map = new HashMap<>();
        map.put("a", "candy");
        map.put("b", "dirt");

        for (Map.Entry<String, String> pair : map.entrySet()) {
            System.out.print(pair.getKey() + " " + pair.getValue() + " ");
        }
        System.out.println();
        for (Map.Entry<String, String> pair : mapBully(map).entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public static Map<String, String> mapBully(Map<String, String> map) {
        if (map.get("a") != null ){
            String tmp = map.get("a");
            map.put("a", "");
            map.put("b", tmp);
        }
        return map;
    }

}
