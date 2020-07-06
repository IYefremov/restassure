//Given three ints, a b c, return true if two or more of them have the same rightmost digit.
// The ints are non-negative. Note: the % "mod" operator computes the remainder, e.g. 17 % 10 is 7.
//
//
//        lastDigit(23, 19, 13) → true
//        lastDigit(23, 19, 12) → false
//        lastDigit(23, 19, 3) → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(lastDigit(23, 19, 3));
    }

    public static boolean lastDigit(int a, int b, int c) {

        if (a%10 == b%10 && a%10 == c%10 && b%10 == c%10) return true;
        if (a%10 == b%10 || a%10 == c%10) return true;
        return (a%10 == c%10 || b%10 == c%10);

    }

}
