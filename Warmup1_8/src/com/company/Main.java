//Given 2 int values, return true if one is negative and one is positive. Except
// if the parameter "negative" is true, then return true only if both are negative.
//
//
//        posNeg(1, -1, false) → true
//        posNeg(-1, 1, false) → true
//        posNeg(-4, -5, true) → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(posNeg(-4, -5, true));
    }
    public static boolean posNeg(int a, int b, boolean negative) {
     if (negative){
         return (a < 0 && b < 0) ? true : false;
     }
     if (!negative){
          if ( a >= 0 && b < 0) return true;
          if ( a < 0 && b >= 0) return true;
     }
     return false;
    }

}
