
//
//Return a version of the given string, where for every star (*) in the string the star and the chars immediately
// to its left and right are gone. So "ab*cd" yields "ad" and "ab**cd" also yields "ad".
//
//
//        starOut("ab*cd") → "ad"
//        starOut("ab**cd") → "ad"
//        starOut("sm*eilly") → ""
//
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("\n" + starOut("stringy*"));
    }

    public static String starOut(String str) {

        StringBuilder string = new StringBuilder(str);

        // without multistars
        while (string.indexOf("**") != -1) {
            string.delete(string.indexOf("**"), string.indexOf("**") + 1);
        }

        while (string.indexOf("*") != -1) {
            if (string.indexOf("*") == 0) {
                string.delete(0, 2);
            }
            if (string.indexOf("*") == string.length() - 1) {
                string.delete(string.length() - 2, string.length());
            }
            if (string.indexOf("*") != 0 && string.indexOf("*") != string.length() - 1 && string.indexOf("**") != -1) {
                string.delete(string.indexOf("*") - 1, string.indexOf("*") + 2);
            }
        }
        return string.toString();
    }
}
