//We'll say that a "triple" in a string is a char appearing three times in a row.
// Return the number of triples in the given string. The triples may overlap.
//
//
//        countTriple("abcXXXabc") → 1
//        countTriple("xxxabyyyycd") → 3
//        countTriple("a") → 0

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(countTriple("xxxabyyyycd"));
    }

    public static int countTriple(String str) {
        int tripleCount = 0;
        int tmpCount = 0;
        for (int i = 0; i < str.length() - 2; i++) {
            for (int j = i + 1; j < str.length()-1; j++) {
                if (str.charAt(i) == str.charAt(j) && str.charAt(i) == str.charAt(j+1) ) {
                    tmpCount++;
                    break;
                } else {
                    break;
                }
            }
            if (tmpCount != 0) {
                tripleCount += tmpCount;
                tmpCount = 0;
            }
        }
        return tripleCount;
    }

}
