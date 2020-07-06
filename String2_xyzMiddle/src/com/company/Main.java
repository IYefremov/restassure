//Given a string, does "xyz" appear in the middle of the string? To define middle,
// we'll say that the number of chars to the left and right of the "xyz" must differ by at most one.
//
// This problem is harder than it looks.
//
//
//        xyzMiddle("AAxyzBB") → true
//        xyzMiddle("AxyzBB") → true
//        xyzMiddle("AxyzBBB") → false
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println( xyzMiddle("AxyzBBB"));
    }
    public static boolean xyzMiddle(String str) {
        if (str.length() < 3) return false;
        int offset1 = (str.length()-3)/2;
        int offset2 = str.length()-3-offset1;

        return (str.substring(offset1,str.length() - offset2).equals("xyz") || str.substring(offset2, str.length() - offset1).equals("xyz"));



    }
}
