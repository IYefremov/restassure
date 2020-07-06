//Given 2 int arrays, a and b, return a new array length 2 containing,
// as much as will fit, the elements from a followed by the elements from b.
// The arrays may be any length, including 0, but there will be 2 or more elements available between the 2 arrays.
//
//
//        make2([4, 5], [1, 2, 3]) → [4, 5]
//        make2([4], [1, 2, 3]) → [4, 1]
//        make2([], [1, 2]) → [1, 2]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        for (int i : make2(array1, array2)) {
            System.out.print(i + " ");
        }
    }

    public static int[] make2(int[] a, int[] b) {
        int[] count = new int[2];
        if (a.length == 0) {
            count[0] = b[0];
            count[1] = b[1];
            return count;
        }
        if (a.length == 1) {
            count[0] = a[0];
            count[1] = b[0];
            return count;
        }
        if (a.length == 2) return a;
        count[0] = a[0];
        count[1] = a[1];
        return count;


    }

}
