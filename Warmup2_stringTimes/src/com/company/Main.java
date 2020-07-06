//Given a string and a non-negative int n, return a larger string that is n copies of the original string.
//
//
//        stringTimes("Hi", 2) → "HiHi"
//        stringTimes("Hi", 3) → "HiHiHi"
//        stringTimes("Hi", 1) → "Hi"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(stringTimes("Hi", 10));
    }
    public static String stringTimes(String str, int n) {
        String strRes  = "";
        for (int i = 0; i < n; i++) {
            strRes +=  str;
            System.out.println(" i = " + i + " str = " + strRes + " n = " + n);
        }
        return strRes;
    }


}
