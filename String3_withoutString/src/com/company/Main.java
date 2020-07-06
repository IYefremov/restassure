
//Given two strings, base and remove, return a version of the base string where all instances of the
//        remove string have been removed (not case sensitive). You may assume that the remove string is length 1 or more.
//        Remove only non-overlapping instances, so with "xxx" removing "xx" leaves "x".
//
//
//        withoutString("Hello there", "llo") → "He there"
//        withoutString("Hello there", "e") → "Hllo thr"
//        withoutString("Hello there", "x") → "Hello there"

package com.company;


import com.sun.xml.internal.ws.util.StringUtils;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String strBase = "Hello there";
        String strRem = "hello";
        System.out.println(withoutString(strBase, strRem));
    }

    public static String withoutString(String base, String remove) {
     //   String processed2 = StringUtils.replaceIgnoreCase(master2, target2, replacement);

      return base.replaceAll(remove, "");

    }

}
