//
//A sandwich is two pieces of bread with something in between.
// Return the string that is between the first and last appearance of "bread" in the given string,
// or return the empty string "" if there are not two pieces of bread.
//
//
//        getSandwich("breadjambread") → "jam"
//        getSandwich("xxbreadjambreadyy") → "jam"
//        getSandwich("xxbreadyy") → ""

package com.company;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(getSandwich("xxbreadyy"));
    }
    public static String getSandwich(String str) {
        if (str.length() < 9) return "";
        int firstIdx = str.indexOf("bread",0);
        int secondIdx = str.lastIndexOf("bread");
        if (firstIdx != -1 && secondIdx != -1 && firstIdx != secondIdx ){
            return str.substring(firstIdx+5, secondIdx);
        }
        return "";

    }
}
