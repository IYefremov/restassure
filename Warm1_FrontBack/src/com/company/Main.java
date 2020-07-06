//Given a string, return a new string where the first and last chars have been exchanged.
//
//
//        frontBack("code") → "eodc"
//        frontBack("a") → "a"
//        frontBack("ab") → "ba"


package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(frontBack(""));

    }
    public static String frontBack(String str) {
        if (str.length() == 1 || str.length() == 0) return str;
        char firstCh = str.charAt(0);
        char lastCh = str.charAt(str.length()-1);
        String strRes = str.substring(1,str.length()-1);
        StringBuffer sb = new StringBuffer(strRes);
        sb.insert(0, lastCh);
        sb.insert(sb.length(), firstCh);
        return sb.toString();

    }
//    public static void main(String args[]) {
//        StringBuffer sb = new StringBuffer("абвгдеёжз");
//        sb.insert(3,"123");
//        System.out.println(sb);
    }
