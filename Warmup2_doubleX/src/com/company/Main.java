//Given a string, return true if the first instance of "x" in the string is immediately followed by another "x".
//
//
//        doubleX("axxbb") → true
//        doubleX("axaxax") → false
//        doubleX("xxxxx") → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(doubleX("xaxx"));
    }

    static boolean doubleX(String str) {

//        if (str.length() < 1) return false;
//        for (int i = 0; i < str.length() - 1; i++) {
//            if (str.charAt(i) == 'x') {
//                if (str.charAt(i + 1) == 'x') {
//                    return true;
//                }
//                return false;
//            }
//        }
//        return false;
//    }
        int i = str.indexOf("x");
        if (i == -1) return false; // no "x" at all

        // Is char at i+1 also an "x"?
        if (i + 1 >= str.length()) return false; // check i+1 in bounds?
        String sss = str.substring(i + 1, i + 2);
        return str.substring(i + 1, i + 2).equals("x");
    }
}
