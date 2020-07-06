//Given a non-empty string and an int N, return the string made starting with char 0,
//
// and then every Nth char of the string. So if N is 3, use char 0, 3, 6, ... and so on. N is 1 or more.
//
//
//        everyNth("Miracle", 2) → "Mrce"
//        everyNth("abcdefg", 2) → "aceg"
//        everyNth("abcdefg", 3) → "adg"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(everyNth("abcdefg", 3));
    }

    public static String everyNth(String str, int n) {
        String sRes = "";
        int count = 1;
        sRes +=  str.charAt(0);

        for (int i = 0; i < str.length(); i++) {
           if (i != 0){
               if (count == n){
                   sRes += str.charAt(i);
                   count = 1;
               } else {
               count++;
               }
           }
        }
        return sRes;
    }


}
