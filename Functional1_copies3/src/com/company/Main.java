
//Given a list of strings, return a list where each string is replaced by 3 copies of the string concatenated together.
//
//
//        copies3(["a", "bb", "ccc"]) → ["aaa", "bbbbbb", "ccccccccc"]
//        copies3(["24", "a", ""]) → ["242424", "aaa", ""]
//        copies3(["hello", "there"]) → ["hellohellohello", "theretherethere"]

package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> myString = new ArrayList<>();
        myString.add("a");
        myString.add("bb");
        myString.add("ccc");
        for (String s : copies3(myString)){
            System.out.println(s);
        }

    }

    public static List<String> copies3(List<String> strings) {
    strings.replaceAll(s -> s.concat(s).concat(s));
    return strings;

    }
}
