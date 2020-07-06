//Given a string, return the count of the number of times that a substring length 2 appears in the string
// and also as the last 2 chars of the string, so "hixxxhi" yields 1 (we won't count the end substring).
//
//
//        last2("hixxhi") → 1
//        last2("xaxxaxaxx") → 1
//        last2("axxxaaxx") → 2

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(last2("xxxx"));
    }

    public static int last2(String str) {
        int count = 0;
        String strConc = "";
        if (str.length() <= 3) return 0;
        String strSub = str.substring(str.length() - 2);
        for (int i = 0; i < str.length() - 3; i++) {
            strConc = String.valueOf(str.charAt(i)) + String.valueOf(str.charAt(i + 1));
            if (strSub.equals(strConc)) {
                count++;
            }
        }
        return count;
    }


}
