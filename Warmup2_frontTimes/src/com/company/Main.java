//Given a string and a non-negative int n, we'll say that the front of the string is the first 3 chars,
// or whatever is there if the string is less than length 3. Return n copies of the front;
//
//
//        frontTimes("Chocolate", 2) → "ChoCho"
//        frontTimes("Chocolate", 3) → "ChoChoCho"
//        frontTimes("Abc", 3) → "AbcAbcAbc"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(frontTimes("Abc", 3));
    }

    public static String frontTimes(String str, int n) {
        String strFront, strRes = "";

        if (str.length() < 3) {
            strFront = str;
        } else strFront = str.substring(0, 3);

        for (int i = 0; i < n; i++){
            strRes += strFront;
        }

        System.out.println(strFront);
        return strRes;
    }

}
