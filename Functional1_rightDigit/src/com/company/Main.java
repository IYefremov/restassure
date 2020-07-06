
//Given a list of non-negative integers, return an integer list of the rightmost digits. (Note: use %)
//
//
//        rightDigit([1, 22, 93]) → [1, 2, 3]
//        rightDigit([16, 8, 886, 8, 1]) → [6, 8, 6, 8, 1]
//        rightDigit([10, 0]) → [0, 0]
//
package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        List<Integer> myList = new ArrayList<>();
        myList.add(1);
        myList.add(22);
        myList.add(93);

        for (Integer i : rightDigit(myList)) {
            System.out.println(i);
        }
    }

    public static List<Integer> rightDigit(List<Integer> nums) {
        nums.replaceAll(n -> n % 10);
        return nums;
    }

}
