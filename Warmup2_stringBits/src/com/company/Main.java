//Given a string, return a new string made of every other char starting with the first, so "Hello" yields "Hlo".
//
//
//        stringBits("Hello") → "Hlo"
//        stringBits("Hi") → "H"
//        stringBits("Heeololeo") → "Hello"
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(stringBits("H"));
    }

    static public String stringBits(String str) {


        if (str.length() < 1) return str;
        String strRes =  String.valueOf(str.charAt(0));
        for (int i = 0; i < str.length(); i++) {
            if ((i != 0 ) && (i % 2 == 0)) {
                strRes += String.valueOf(str.charAt(i));
            }
        }

        return strRes;
    }

}
