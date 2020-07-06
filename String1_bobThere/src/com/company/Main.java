//Return true if the given string contains a "bob" string, but where the middle 'o' char can be any char.
//
//
//        bobThere("abcbob") → true
//        bobThere("b9b") → true
//        bobThere("bac") → false

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(bobThere(""));
    }

    public static boolean bobThere(String str) {
        if (str.length() <3) return false;
        for (int i = 0; i < str.length() - 2; i++) {
            if (str.charAt(i) == 'b' && str.charAt(i + 2) == 'b') return true;
        }
        return false;

    }
}
