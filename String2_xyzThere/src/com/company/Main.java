//Return true if the given string contains an appearance of "xyz" where the xyz is not directly preceeded by a period (.).
// So "xxyz" counts but "x.xyz" does not.
//
//
//        xyzThere("abcxyz") → true
//        xyzThere("abc.xyz") → false
//        xyzThere("xyz.abc") → true
// cv.xyz.xyzxyz

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(xyzThere("cv.xyz.xyzxyz"));
    }

    public static boolean xyzThere(String str) {
        if (str.length() < 3) return false;

        if (str.indexOf("xyz") == 0) return true;

        while (str.contains("xyz")) {
            if (str.charAt(str.indexOf("xyz") - 1) == '.') {
                str = str.substring(0, str.indexOf("xyz") - 1) + "XXXX" + str.substring(str.indexOf("xyz")+3);
            } else return true;
        }

        return false;
    }
}
