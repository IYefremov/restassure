
//Given a list of strings, return a list where each string has "*" added at its end.
//
//
//        addStar(["a", "bb", "ccc"]) → ["a*", "bb*", "ccc*"]
//        addStar(["hello", "there"]) → ["hello*", "there*"]
//        addStar(["*"]) → ["**"]

package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> myList = new ArrayList<>();
        myList.add("a");
        myList.add("bb");
        myList.add("ccc");


        for (String s: addStar(myList)){
            System.out.println(s);
        }

    }
    public static List<String> addStar(List<String> strings) {
    strings.replaceAll(mStr -> mStr.concat("*"));
    return strings;
    }
}
