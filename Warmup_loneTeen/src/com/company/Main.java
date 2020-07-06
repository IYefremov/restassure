//We'll say that a number is "teen" if it is in the range 13..19 inclusive.
// Given 2 int values, return true if one or the other is teen, but not both.
//
//
//        loneTeen(13, 99) → true
//        loneTeen(21, 19) → true
//        loneTeen(13, 13) → false


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(loneTeen(21, 19));
    }

    public static boolean loneTeen(int a, int b) {
       if ((a >= 13 && a <= 19) || (b >= 13 && b <= 19)) {
           if ((a >= 13 && a <= 19) && (b >= 13 && b <= 19)) return false;
           return true;
       }
        return false;
    }
}
