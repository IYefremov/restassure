//Given 2 int arrays, each length 2, return a new array length 4 containing all their elements.
//
//
//        plusTwo([1, 2], [3, 4]) → [1, 2, 3, 4]
//        plusTwo([4, 4], [2, 2]) → [4, 4, 2, 2]
//        plusTwo([9, 2], [3, 4]) → [9, 2, 3, 4]
//
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {7, 1};
        int[] array2 = {7, 4};


        for (int i : plusTwo(array1, array2)) {
            System.out.print(i + " ");
        }
    }

        public static int[] plusTwo(int[] a, int[] b) {
        int [] arrRes = new int [a.length + b.length];

            arrRes[0] = a[0];
            arrRes[1] = a[1];
            arrRes[2] = b[0];
            arrRes[3] = b[1];

        return arrRes;
    }
}
