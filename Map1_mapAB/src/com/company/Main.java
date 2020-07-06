
//Modify and return the given map as follows: for this problem the map may or may not contain the "a" and "b" keys.
//        If both keys are present, append their 2 string values together and store the result under the key "ab".
//
//
//        mapAB({"a": "Hi", "b": "There"}) → {"a": "Hi", "ab": "HiThere", "b": "There"}
//        mapAB({"a": "Hi"}) → {"a": "Hi"}
//        mapAB({"b": "There"}) → {"b": "There"}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Map<String, String> map = new HashMap<>();
        map.put("a", "Hi");
        map.put("b", "There");

        for (Map.Entry<String, String> m : map.entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue()+ " ");
        }
        System.out.println();
        for (Map.Entry<String, String> m : mapAB(map).entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue()+ " ");
        }

    }

    public static Map<String, String> mapAB(Map<String, String> map) {
        if (map.get("a") != null && map.get("b") != null){
            String str = map.get("a").concat(map.get("b"));
            if (map.get("ab") != null) {
                map.replace("ab", str);
            } else {
                map.put("ab", str);
            }
        }
        return map;
    }

}
