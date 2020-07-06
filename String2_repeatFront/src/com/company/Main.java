//Given a string and an int n, return a string made of the first n characters of the string,
// followed by the first n-1 characters of the string, and so on. You may assume that n is between 0
// and the length of the string, inclusive (i.e. n >= 0 and n <= str.length()).
//
//
//        repeatFront("Chocolate", 4) → "ChocChoChC"
//        repeatFront("Chocolate", 3) → "ChoChC"
//        repeatFront("Ice Cream", 2) → "IcI"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(repeatFront("Chocolate", 3));
    }

    public static String repeatFront(String str, int n) {
        String strRes = "";
        int chCount = n;

        for (int i = 0; i < n; i++){
            strRes += str.substring(0, chCount--);
        }
        return strRes;
    }
}
