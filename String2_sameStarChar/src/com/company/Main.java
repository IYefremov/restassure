//Returns true if for every '*' (star) in the string, if there are chars both immediately before and after the star,
// they are the same.
//
//
//        sameStarChar("xy*yzz") → true
//        sameStarChar("xy*zzz") → false
//        sameStarChar("*xa*az") → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(sameStarChar(""));
    }

    public static boolean sameStarChar(String str) {
        int starIdx = 0;

        if (str.length() < 3) return false;
        while (str.indexOf("*", starIdx) != -1) {
            starIdx = str.indexOf("*", starIdx);
            if (str.indexOf("*", starIdx) == 0) starIdx++;
            if (str.indexOf("*", starIdx) == str.length() - 1) {
                return false;
            }
            if (str.charAt(starIdx - 1) == str.charAt(starIdx + 1)) return true;
            starIdx++;
        }
        return false;
    }

}
