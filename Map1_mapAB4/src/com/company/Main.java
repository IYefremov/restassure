
package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //
//mapAB4({"a": "aaa", "b": "bb", "c": "cake"}) → {"a": "aaa", "b": "bb", "c": "aaa"}
//        mapAB4({"a": "aa", "b": "bbb", "c": "cake"}) → {"a": "aa", "b": "bbb", "c": "bbb"}
//        mapAB4({"a": "aa", "b": "bbb"}) → {"a": "aa", "b": "bbb", "c": "bbb"}
        Map<String, String> map = new HashMap<>();

        map.put("a", "aaa");
        map.put("b", "bb");
        map.put("c", "cake");

        for (Map.Entry<String, String> m : map.entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }

        System.out.println();

        for (Map.Entry<String, String> m : mapAB4(map).entrySet()) {
            System.out.print(m.getKey() + " " + m.getValue() + " ");
        }
    }
    public static Map<String, String> mapAB4(Map<String, String> map) {
        String maxValStr= "";
        if (map.get("a") != null && map.get("b") != null ) {
            if (map.get("a").length() != map.get("b").length()){
                // find max
                if(map.get("a").length() > map.get("b").length()){
                    maxValStr = map.get("a");
                } else {
                    maxValStr = map.get("b");
                }

                // check if c exist
                if (map.get("c") != null){

                    map.replace("c", maxValStr);
                } else {
                    map.put("c",  maxValStr);
                }
            }
        }
        if (map.get("a") != null && map.get("b") != null ) {
            if (map.get("a").length() == map.get("b").length()){
                map.replace("a", "");
                map.replace("b", "");
            }
        }
        return map;

    }

}
