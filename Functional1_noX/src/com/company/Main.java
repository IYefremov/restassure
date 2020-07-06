//
//Given a list of strings, return a list where each string has all its "x" removed.
//
//
//        noX(["ax", "bb", "cx"]) → ["a", "bb", "c"]
//        noX(["xxax", "xbxbx", "xxcx"]) → ["a", "bb", "c"]
//        noX(["x"]) → [""]
//
package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> myList = new ArrayList<String>();
        myList.add("ax");
        myList.add("bb");
        myList.add("cx");

        for (String s : noX(myList)){
            System.out.println(s);
        }

    }

    public static List<String> noX(List<String> strings) {
        strings.replaceAll(str -> str.replaceAll("x", ""));
        return  strings;
    }

}
