//Given two int values, return their sum. Unless the two values are the same, then return double their sum.
//
//
//        sumDouble(1, 2) → 3
//        sumDouble(3, 2) → 5
//        sumDouble(2, 2) → 8



package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(sumDouble(5,5));
    }

    public static int sumDouble(int a, int b) {

        return (a == b) ?  2*(a+b) : a+b;
        //логическое_условие ? выражение1 : выражение2
    }


}
