//Given a list of strings, return a list where each string has "y" added at its start and end.
//
//
//        moreY(["a", "b", "c"]) → ["yay", "yby", "ycy"]
//        moreY(["hello", "there"]) → ["yhelloy", "ytherey"]
//        moreY(["yay"]) → ["yyayy"]

package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> myList = new ArrayList<>();
        myList.add("a");
        myList.add("b");
        myList.add("c");

        for (String s : moreY(myList)){
            System.out.println(s);
        }
    }

    public static  List<String> moreY(List<String> strings) {
        strings.replaceAll(str -> "y" +  str + "y");
        return strings;
    }
}
