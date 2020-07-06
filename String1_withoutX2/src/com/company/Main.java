//Given a string, if one or both of the first 2 chars is 'x', return the string without those 'x' chars,
// and otherwise return the string unchanged. This is a little harder than it looks.
//
//
//        withoutX2("xHi") → "Hi"
//        withoutX2("Hxi") → "Hi"
//        withoutX2("Hi") → "Hi"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(withoutX2(""));
    }

    public static String withoutX2(String str) {
        if (str.length() == 0) return str;
        if (str.length() == 1){
            if (str.charAt(0) == 'x'){
                return str.substring(1);
            } else
                return str;
        }
        if (str.charAt(0) == 'x' && str.charAt(1) == 'x') return str.substring(2);
        if (str.charAt(0) == 'x') return str.substring(1);
        if (str.charAt(1) == 'x') return String.valueOf(str.charAt(0)) + str.substring(2);
        return str;
    }
}
