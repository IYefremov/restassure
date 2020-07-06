//Given a non-empty string like "Code" return a string like "CCoCodCode".
//
//
//        stringSplosion("Code") → "CCoCodCode"
//        stringSplosion("abc") → "aababc"
//        stringSplosion("ab") → "aab"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(stringSplosion("a"));
    }

    public static String stringSplosion(String str) {
        String strRes = "";
        if (str.length() == 0) return str;

        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j <= i; j++) {
                strRes = strRes + str.charAt(j);
            }
        }
        return strRes;
    }


}
