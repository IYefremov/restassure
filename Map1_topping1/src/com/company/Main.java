//Given a map of food keys and topping values, modify and return the map as follows: if the key "ice cream" is present,
//        set its value to "cherry". In all cases, set the key "bread" to have the value "butter".
//
//
//        topping1({"ice cream": "peanuts"}) → {"bread": "butter", "ice cream": "cherry"}
//        topping1({}) → {"bread": "butter"}
//        topping1({"pancake": "syrup"}) → {"bread": "butter", "pancake": "syrup"}

package com.company;



import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Map<String, String> map = new HashMap<>();
        map.put("ice cream", "peanuts");

        for (Map.Entry<String, String> m : map.entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }

        System.out.println();

        for (Map.Entry<String, String> m : topping1(map).entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }
    }

    public static Map<String, String> topping1(Map<String, String> map) {
        if (map.get("ice cream") != null) {
            map.replace("ice cream", "cherry");
        }
        if (map.get("bread") == null) {
            map.put("bread", "butter");

        }

        if (!map.get("bread").equals("butter")) {
            map.replace("bread", "butter");
        }


        return map;
        //  return (MappingChange.Map<String, String>) map;
    }

}
