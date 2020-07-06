//Given a list of integers, return a list of the integers, omitting any that are less than 0.
//
//
//        noNeg([1, -2]) → [1]
//        noNeg([-3, -3, 3, 3]) → [3, 3]
//        noNeg([-1, -1, -1]) → []

package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//public class Main {
//
//    public static void main(String[] args) {
//        // write your code here
//        List<Integer> lst = new ArrayList<>();
//        lst.add(1);
//        lst.add(-2);
//        for (Integer i : noNeg(lst)) {
//            System.out.print(" " + i);
//        }
//
//    }
//
//    public static List<Integer> noNeg(List<Integer> nums) {
//        nums.removeIf(n -> n < 0);
//        return nums;
//    }
//
//}

//    Given a list of non-negative integers, return a list of those numbers except omitting any that end with 9. (Note: % by 10)
//
//
//        no9([1, 2, 19]) → [1, 2]
//        no9([9, 19, 29, 3]) → [3]
//        no9([1, 2, 3]) → [1, 2, 3]


//Given a list of integers, return a list of those numbers, omitting any that are between 13 and 19 inclusive.
//
//
//        noTeen([12, 13, 19, 20]) → [12, 20]
//        noTeen([1, 14, 1]) → [1, 1]
//        noTeen([15]) → []


//public class Main {
//
//    public static void main(String[] args) {
//        // write your code here
//        List<Integer> lst = new ArrayList<>();
//        lst.add(12);
//        lst.add(13);
//        lst.add(19);
//        lst.add(20);
//        for (Integer i : noTeen(lst)) {
//            System.out.print(" " + i);
//        }
//
//    }
//
//    public static List<Integer> noTeen(List<Integer> nums) {
//        nums.removeIf(n -> (n >= 13) && (n <= 19));
//        return nums;
//    }
//
//}


//Given a list of strings, return a list of the strings, omitting any string that contains a "z".
// (Note: the str.contains(x) method returns a boolean)
//
//
//        noZ(["aaa", "bbb", "aza"]) → ["aaa", "bbb"]
//        noZ(["hziz", "hzello", "hi"]) → ["hi"]
//        noZ(["hello", "howz", "are", "youz"]) → ["hello", "are"]

//public class Main {
//
//    public static void main(String[] args) {
//        // write your code here
//        List<String> lst = new ArrayList<>();
//        lst.add("aaa");
//        lst.add("bbb");
//        lst.add("aza");
//        //     lst.add(20);
//        for (String i : noZ(lst)) {
//            System.out.print(" " + i);
//        }
//
//    }
//
//    public static List<String> noZ(List<String> strings) {
//        strings.removeIf(s -> s.contains("z"));
//        return strings;
//    }
//
//
//}


//Given a list of strings, return a list of the strings, omitting any string length 4 or more.
//
//
//        noLong(["this", "not", "too", "long"]) → ["not", "too"]
//        noLong(["a", "bbb", "cccc"]) → ["a", "bbb"]
//        noLong(["cccc", "cccc", "cccc"]) → []


//public class Main {
//
//    public static void main(String[] args) {
//        // write your code here
//        List<String> lst = new ArrayList<>();
//        lst.add("this");
//        lst.add("not");
//        lst.add("too");
//        lst.add("long");
//
//        for (String i : noLong(lst)) {
//            System.out.print(" " + i);
//        }
//
//    }
//
//    public static List<String> noLong(List<String> strings) {
//        strings.removeIf(s -> s.length() >= 4);
//        return strings;
//    }
//}

//
//Given a list of strings, return a list where each string has "y" added at its end, omitting any
//        resulting strings that contain "yy" as a substring anywhere.
//
//
//        noYY(["a", "b", "c"]) → ["ay", "by", "cy"]
//        noYY(["a", "b", "cy"]) → ["ay", "by"]
//        noYY(["xx", "ya", "zz"]) → ["xxy", "yay", "zzy"]

//public class Main {
//
//    public static void main(String[] args) {
//        // write your code here
//        List<String> lst = new ArrayList<>();
//        lst.add("a");
//        lst.add("b");
//        lst.add("cy");
//        //  lst.add("long");
//        System.out.println();
//        for (String i : noYY(lst)) {
//            System.out.print(" " + i);
//        }
//
//    }
//
//    public static List<String> noYY(List<String> strings) {
//
//
//
//        strings.replaceAll(s -> s + "y");
//        strings.removeIf(s -> s.contains("yy"));
//        return strings;
//
//
//
//    }
//
//
//}
public class Main {

    public static void main(String[] args) {
        // write your code here
        List<Integer> lst = new ArrayList<>();
        // 3, 1, 4, 1, 6, 99, 0
        lst.add(3);
        lst.add(1);
        lst.add(4);
//        lst.add(1);
//        lst.add(6);
//        lst.add(99);
//        lst.add(0);
        //  lst.add("long");
        System.out.println();
        for (Integer i : two2(lst)) {
            System.out.print(" " + i);
        }

    }

    public static List<Integer> two2(List<Integer> nums) {

//        Given a list of integers, return a list of those numbers squared and the product added to 10,
//        omitting any of the resulting numbers that end in 5 or 6.
//
//
//        square56([3, 1, 4]) → [19, 11]
//        square56([1]) → [11]
//        square56([2]) → [14]


        nums.replaceAll(n -> n * n + 10);
        nums.removeIf(n -> n.toString().substring(n.toString().length() - 1).equals("5")
                || n.toString().substring(n.toString().length() - 1).equals("6"));
        return nums;

    }


}










