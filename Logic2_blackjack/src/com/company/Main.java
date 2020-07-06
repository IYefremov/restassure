//Given 2 int values greater than 0, return whichever value is nearest to 21 without going over.
// Return 0 if they both go over.
//
//
//        blackjack(19, 21) → 21
//        blackjack(21, 19) → 21
//        blackjack(19, 22) → 19
//
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(blackjack(19, 19));
    }

    public static int blackjack(int a, int b) {
        if (a > 21 && b > 21) return 0;
        if (a > 21 && b <= 21) return b;
        if (a <= 21 && b > 21) return a;

        if (Math.abs(a-21) < Math.abs(b-21)) return a;
        return b;
    }
}
