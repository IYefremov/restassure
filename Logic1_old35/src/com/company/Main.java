//Return true if the given non-negative number is a multiple of 3 or 5,
// but not both. Use the % "mod" operator -- see Introduction to Mod
//
//
//        old35(3) → true
//        old35(10) → true
//        old35(15) → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println( old35(1));
    }
    public static boolean old35(int n) {
    if (n % 3 == 0 && n % 5 == 0) return false;
    if (n % 3 == 0) return true;
    return (n % 5 == 0);
    }
}
