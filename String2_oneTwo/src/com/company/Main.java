//Given a string, compute a new string by moving the first char to come after the next two chars, so "abc" yields "bca".
// Repeat this process for each subsequent group of 3 chars, so "abcdef" yields "bcaefd".
// Ignore any group of fewer than 3 chars at the end.
//
//
//        oneTwo("abc") → "bca"
//        oneTwo("tca") → "cat"
//        oneTwo("tcagdo") → "catdogerw"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(oneTwo("tcagdogd"));
    }
    public static String oneTwo(String str) {

        StringBuilder string = new StringBuilder(str);
        if (string.length() < 2) return "";

        char tmp1;
        char tmp2;
        char tmp3;
        int multOfThree = string.length()/3 * 3;
        for (int i = 0; i < string.length()-2; i+=3){

            tmp1 = string.charAt(i);
            tmp2 = string.charAt(i+1);
            tmp3 = string.charAt(i+2);

            string.setCharAt(i, tmp2);
            string.setCharAt(i+1, tmp3);
            string.setCharAt(i+2, tmp1);
        }
        return string.toString().substring(0, multOfThree);
    }
}
