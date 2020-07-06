//
//Given two strings, word and a separator sep, return a big string made of count occurrences of the word,
// separated by the separator string.
//
//
//        repeatSeparator("Word", "X", 3) → "WordXWordXWord"
//        repeatSeparator("This", "And", 2) → "ThisAndThis"
//        repeatSeparator("This", "And", 1) → "This"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(repeatSeparator("This", "And", 2));
    }

    public static String repeatSeparator(String word, String sep, int count) {
        String strRes = "";
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                strRes += word;
            } else {
                strRes += word + sep;
            }
        }
        return strRes;
    }

}
