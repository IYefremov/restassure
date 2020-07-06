
//Given a string, return a string made of the chars at indexes 0,1, 4,5, 8,9 ... so "kittens" yields "kien".
//
//
//        altPairs("kitten") → "kien"
//        altPairs("Chocolate") → "Chole"
//        altPairs("CodingHorror") → "Congrr"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(altPairs("CodingHorror"));
    }

    public static String altPairs(String str) {
        if (str.length() == 0) return str;
        String strRes = "";
        int flag = 0;

        for (int i = 0; i < str.length(); i++) {
            if (flag <= 1) {
                strRes += str.charAt(i);

            }
            flag++;
            if (flag == 4) flag = 0;
        }
        return strRes;
    }

}

