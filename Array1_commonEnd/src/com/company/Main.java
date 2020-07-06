//Given 2 arrays of ints, a and b, return true if they have the same first element or they have the same last element.
// Both arrays will be length 1 or more.
//
//
//        commonEnd([1, 2, 3], [7, 3]) → true
//        commonEnd([1, 2, 3], [7, 3, 2]) → false
//        commonEnd([1, 2, 3], [1, 3]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [] arr1 = {1,2,3};
        int [] arr2 = {1, 3};

        System.out.println(commonEnd(arr1, arr2));
    }

    public static boolean commonEnd(int[] a, int[] b) {
        return (a[0] == b[0] || a[a.length-1] == b[b.length-1]);

    }
}
