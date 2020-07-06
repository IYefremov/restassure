
//Loop over the given array of strings to build a result string like this: when a string appears the 2nd, 4th, 6th,
//        etc. time in the array, append the string to the result. Return the empty string if no string appears a 2nd time.
//
//
//        wordAppend(["a", "b", "a"]) → "a"
//        wordAppend(["a", "b", "a", "c", "a", "d", "a"]) → "aa"
//        wordAppend(["a", "", "a"]) → "a"

package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String[] str = { "a", "b", "b", "a", "a"};


        System.out.println(wordAppend(str));

    }
// "a", "b", "b", "b", "a", "c", "a", "a", "a", "b", "a"]) → "baaba"
//    ["a", "b", "b", "a", "a"]) → "ba"

    public static String wordAppend(String[] strings) {
        String strRes = "";
        Map<String, Integer> myMap = new HashMap<>();
        for (String s : strings){
            if (!myMap.containsKey(s)){
                myMap.put(s, 1);
            } else {
                int count = myMap.get(s);
                myMap.replace(s, ++ count);
            }
        }
        for (Map.Entry<String, Integer> m : myMap.entrySet()) {

                for (int i = 0; i < m.getValue()/2; i++){
                    strRes += m.getKey();
                }

        }
        return strRes;
    }

}
