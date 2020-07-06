//Given 2 int arrays, a and b, each length 3, return a new array length 2 containing their middle elements.
//
//
//        middleWay([1, 2, 3], [4, 5, 6]) → [2, 5]
//        middleWay([7, 7, 7], [3, 8, 0]) → [7, 8]
//        middleWay([5, 2, 9], [1, 4, 5]) → [2, 4]

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};
        //int[] arrRes = new int[2];

        for (int i : (middleWay(array1, array2))) {
            System.out.println(i);
        }
    }

    public static int[] middleWay(int[] a, int[] b) {
        int[] arrTmp = new int[2];
        arrTmp[0] = a[1];
        arrTmp[1] = b[1];
        return arrTmp;
    }

}
