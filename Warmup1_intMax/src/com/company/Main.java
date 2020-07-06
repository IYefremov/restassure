//Given three int values, a b c, return the largest.
//
//
//        intMax(1, 2, 3) → 3
//        intMax(1, 3, 2) → 3
//        intMax(3, 2, 1) → 3

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(intMax(3, 2, 1));
    }
    public static int intMax(int a, int b, int c) {
        return  Math.max(a, Math.max(b, c));
    }
}
