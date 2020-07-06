//Return an int array length 3 containing the first 3 digits of pi, {3, 1, 4}.
//
//
//        makePi() → [3, 1, 4]

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
       int [] resArr = new int[3];
       resArr  = makePi();
        for (int i: resArr) {
            System.out.println(i);
        }

    }
    public static int[] makePi() {
    int [] array = {3, 1, 4};
        return array;
    }

}
