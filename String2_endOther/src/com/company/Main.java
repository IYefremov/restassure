//Given two strings, return true if either of the strings appears at the very end of the other string,
// ignoring upper/lower case differences (in other words, the computation should not be "case sensitive").
// Note: str.toLowerCase() returns the lowercase version of a string.
//
//
//        endOther("Hiabc", "abc") → true
//        endOther("AbC", "HiaBc") → true
//        endOther("abc", "abXabc") → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(endOther("Hiabc", "abcd"));
    }

    public static boolean endOther(String a, String b) {
        int lenght;
//    if (a.length() == b.length()){
//        if (a.toLowerCase().equals(b.toLowerCase())) return true;
 //   }

    if (a.length() >= b.length()){
        lenght = b.length();
        return (a.substring(a.length()- lenght).toLowerCase().equals(b.toLowerCase()));
    }
    lenght = a.length();
    return (b.substring(b.length()- lenght).toLowerCase().equals(a.toLowerCase()));


    }

}
