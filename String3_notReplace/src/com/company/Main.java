//Given a string, return a string where every appearance of the lowercase word "is" has been replaced with "is not".
//        The word "is" should not be immediately preceeded or followed by a letter -- so for example the "is" in "this"
//        does not count. (Note: Character.isLetter(char) tests if a char is a letter.)
//
//
//        notReplace("is test") → "is not test"
//        notReplace("is-is") → "is not-is not"
//        notReplace("This is right") → "This is not right"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
      String [] string = {"This is isabell",
        "This is right",
        "is-is",
        "is test",
        "isis",
        "Dis is bliss is",
        "is his"};

        System.out.println(notReplace(string));
    }

    public static String [] notReplace(String[] str) {
        for (String s: str) {
            System.out.print(s);
            s = s.replaceAll(("(?<=\\s|^|[^\\w])is(?=\\s|$|[^\\w])"), "is not");
           // s = s.replaceAll(("((^is)\\s?)|(\\sis\\s)|(\\sis$)"), " is not ");
            System.out.print("       ---->" +  s);
            System.out.println();
        }
        return str;

       // !(([a-zA-z0-9]*)?is?)(is)
    }

}
