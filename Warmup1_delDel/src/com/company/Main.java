//Given a string, if the string "del" appears starting at index 1,
// return a string where that "del" has been deleted. Otherwise, return the string unchanged.
//
//
//        delDel("adelbc") → "abc"
//        delDel("adelHello") → "aHello"
//        delDel("adedbc") → "adedbc"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(delDel("1del"));
    }

    public static String delDel(String str) {
        if (str.length() <= 3) return str;
     if(str.substring(1,4).equals("del")){
         return str.substring(0, 1) + str.substring(4, str.length());
     };
     return str;
    }
}
