package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(withoutX("xHix"));
    }
    public static String withoutX(String str) {
        if (str.length() == 0) return str;
        if (str.length() == 1 && str.substring(0, 1).equals("x") ) return "";
        if (str.substring(0, 1).equals("x")) {
            str = str.substring(1);
        }

        if (str.substring(str.length()-1).equals("x")) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

}
