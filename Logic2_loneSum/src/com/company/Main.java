//Given 3 int values, a b c, return their sum. However, if one of the values is the same as another of the values,
// it does not count towards the sum.
//
//
//        loneSum(1, 2, 3) → 6
//        loneSum(3, 2, 3) → 2
//        loneSum(3, 3, 3) → 0

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(loneSum(1, 3, 3));
    }
    public static int loneSum(int a, int b, int c) {
    if (a == b && b == c) return 0;
    if (a == b && a != c) return c;
    if (a == c && a != b) return b;
    if (b == c && b != a) return a;
    return a + b + c;

    }
}
