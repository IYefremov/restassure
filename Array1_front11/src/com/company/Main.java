//Given 2 int arrays, a and b, of any length, return a new array with the first element of each array.
// If either array is length 0, ignore that array.
//
//
//        front11([1, 2, 3], [7, 9, 8]) → [1, 7]
//        front11([1], [2]) → [1, 2]
//        front11([1, 7], []) → [1]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {};
        int[] array2 = {};
        for (int i : front11(array1, array2)) {
            System.out.print(i + " ");
        }
    }

    public static int[] front11(int[] a, int[] b) {
        int counter = 0;
        if (a.length > 0) {
            counter++;
        }
        if (b.length > 0) {
            counter++;
        }
        int[] arrRes = new int[counter];

        if (a.length > 0 && b.length > 0) {
            arrRes[0] = a[0];
            arrRes[1] = b[0];
        }
        if (a.length > 0 && b.length == 0) {
            arrRes[0] = a[0];
        }
        if (a.length == 0 && b.length > 0) {
            arrRes[0] = b[0];
        }
        return arrRes;

    }

}
