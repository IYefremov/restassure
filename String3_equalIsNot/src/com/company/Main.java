//Given a string, return true if the number of appearances of "is" anywhere in the string is equal
// to the number of appearances of "not" anywhere in the string (case sensitive).
//
//
//        equalIsNot("This is not") → false
//        equalIsNot("This is notnot") → true
//        equalIsNot("noisxxnotyynotxisi") → true
//
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(equalIsNot("noisxxnotyynotxisi"));
    }

    public static boolean equalIsNot(String str) {
        int countIS = 0, countNot = 0;
        for (int i = 0; i < str.length()-1; i++ ){
            if (str.charAt(i) == 'i' && str.charAt(i+1) == 's'){
                countIS ++;
            }
        }
        for (int i = 0; i < str.length()-2; i++ ){
            if (str.charAt(i) == 'n' && str.charAt(i+1) == 'o' && str.charAt(i+2) == 't'){
                countNot ++;
            }
        }
        return (countIS == countNot);
    }

}
