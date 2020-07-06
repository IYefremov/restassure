//We'll say that a String is xy-balanced if for all the 'x' chars in the string,
// there exists a 'y' char somewhere later in the string. So "xxy" is balanced,
// but "xyx" is not. One 'y' can balance multiple 'x's. Return true if the given string is xy-balanced.
//
//
//        xyBalance("aaxbby") → true
//        xyBalance("aaxbb") → false
//        xyBalance("yaaxbb") → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(xyBalance("xy"));
    }

    public static boolean xyBalance(String str) {
        int indX = 0;
        int resInd = -1;

        while (str.indexOf('x', indX) != -1) {
            indX = str.indexOf('x', indX);
            if (indX < str.length()) {
                resInd = indX;
                indX++;
            }
        }
        if (resInd == str.length()) return false;

        if (str.indexOf('y', resInd + 1) != -1) return true;

        return false;
    }
}
