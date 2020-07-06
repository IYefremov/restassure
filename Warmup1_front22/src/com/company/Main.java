//Given a string, take the first 2 chars and return the string with the 2 chars added at both the front and back,
// so "kitten" yields"kikittenki". If the string length is less than 2, use whatever chars are there.
//
//
//        front22("kitten") → "kikittenki"
//        front22("Ha") → "HaHaHa"
//        front22("abc") → "ababcab"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here.
        System.out.println(front22("abc"));
    }
    public static  String front22(String str) {
        if (str.length() <= 2 ) return str + str + str;
        String str1 = str.substring(0,2);
        System.out.println("--->" + str1);
        return str1 + str + str1;

    }

}
