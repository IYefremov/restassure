//Given a string and a non-empty word string, return a version of the original String where
// all chars have been replaced by pluses ("+"), except for appearances of the word string which are preserved unchanged.
//
//
//        plusOut("12xy34", "xy") → "++xy++"
//        plusOut("12xy34", "1") → "1+++++"
//        plusOut("12xy34xyabcxy", "xy") → "++xy++xy+++xy"

package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(plusOut("12xy34xyabcxy", "xy"));
    }

    public static String plusOut(String str, String word) {
        StringBuilder out = new StringBuilder(str);

        for (int i = 0; i < out.length(); ) {
            if (!str.startsWith(word, i))
                out.setCharAt(i++, '+');
            else
                i += word.length();
        }

        return out.toString();

    }

}
