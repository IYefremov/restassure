
//Given a string and a non-empty word string, return a string made of each char just before and just after every
// appearance of the word in the string. Ignore cases where there is no char before or after the word,
// and a char may be included twice if it is between two words.
//
//
//        wordEnds("abcXY123XYijk", "XY") → "c13i"
//        wordEnds("XY123XY", "XY") → "13"
//        wordEnds("XY1XY", "XY") → "11

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(wordEnds("abc1xyz1abc", "b"));
        //abc1xyz1abc", "b") → "acac"
    }

    public static String wordEnds(String str, String word) {
        String strTmp = "";
        if (str.length() <= word.length()) return "";
        for (int i = 0; i < str.length(); i++) {
            if (!str.startsWith(word, i)) {
                System.out.println("Do not start");
            } else {
                if (i != 0 && i != str.length() - word.length()) { // if not start or end of line
                    strTmp += str.substring(i - 1, i) + str.substring(i + word.length(), i + word.length() + 1);
                    i += word.length()-1;
                }
                if (i == 0) // if begin of line
                {
                    strTmp = str.substring(i + word.length(), i + word.length() + 1);
                    i += word.length()-1;
                } else {
                    if (i == str.length() - word.length()) {   // if end of line
                        strTmp += str.substring(i - 1, i);
                        i += word.length()-1;
                    }
                }

            }

        }
        return strTmp;
    }
}
