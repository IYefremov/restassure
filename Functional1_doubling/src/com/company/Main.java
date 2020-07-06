// 1
//Given a list of integers, return a list where each integer is multiplied by 2.
//
//
//        doubling([1, 2, 3]) → [2, 4, 6]
//        doubling([6, 8, 6, 8, -1]) → [12, 16, 12, 16, -2]
//        doubling([]) → []

// 2 Given a list of integers, return a list where each integer is multiplied with itself.
//
//
//square([1, 2, 3]) → [1, 4, 9]
//square([6, 8, -6, -8, 1]) → [36, 64, 36, 64, 1]
//square([]) → []

package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        List<Integer> myList = new ArrayList<>();

        myList.add(1);
        myList.add(2);
        myList.add(3);

        // 22
        for (Integer i : square(myList)) {
            System.out.println(i);
        }

        // 1
//        for (Integer i : doubling(myList)) {
//            System.out.println(i);
//        }

    }

    //// 1
//    public static List<Integer> doubling(List<Integer> nums) {
//        nums.replaceAll(my -> my * 2);
//        return nums;
//    }

    public static List<Integer> square(List<Integer> nums) {
        nums.replaceAll(myNum -> myNum * myNum);
        return nums;
    }
}
