//Count the number of "xx" in the given string. We'll say that overlapping is allowed, so "xxx" contains 2 "xx".
//
//
//        countXX("abcxx") → 1
//        countXX("xxx") → 2
//        countXX("xxxx") → 3

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(countXX("xxxx"));
    }

    static int  countXX(String str) {
        int count = 0;
        for (int i = 0; i < str.length()-1; i++){
            if (str.charAt(i) == 'x' && str.charAt(i+1) == 'x') {
                count++;
            }
        }
        return count;

    }

}
