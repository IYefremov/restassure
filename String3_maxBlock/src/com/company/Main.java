//
//Given a string, return the length of the largest "block" in the string. A block is a run of adjacent chars that are the same.
//
//
//        maxBlock("hoopla") → 2
//        maxBlock("abbCCCddBBBxx") → 3
//        maxBlock("") → 0

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(maxBlock("xyzz"));
    }
    public static int maxBlock(String str) {
        int counter = 0;
        int tmpCounter = 0;
        if (str.length() == 0) return 0;

        char ch = str.charAt(0);
        for (int i = 1; i < str.length(); i++){
            if (str.charAt(i) == ch){
                counter ++;
            } else {
                ch = str.charAt(i);
                tmpCounter = Math.max(counter, tmpCounter);
                counter = 0;
            }
        }
    return Math.max(counter, tmpCounter)+1;
    }
}
