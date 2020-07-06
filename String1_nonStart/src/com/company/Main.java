//Given 2 strings, return their concatenation, except omit the first char of each. The strings will be at least length 1.
//
//
//        nonStart("Hello", "There") → "ellohere"
//        nonStart("java", "code") → "avaode"
//        nonStart("shotl", "java") → "hotlava"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(nonStart("j", "c"));
    }

    public static String nonStart(String a, String b) {
        return a.substring(1) + b.substring(1);
    }

}
