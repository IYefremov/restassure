//Given an array of scores sorted in increasing order, return true if the array contains 3 adjacent scores
// that differ from each other by at most 2, such as with {3, 4, 5} or {3, 5, 5}.
//
//
//        scoresClump([3, 4, 5]) → true
//        scoresClump([3, 4, 6]) → false
//        scoresClump([1, 3, 5, 5]) → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] array = {1, 3, 5, 6};
        System.out.println(scoresIncreasing(array));
    }

    public static boolean scoresIncreasing(int[] scores) {
        boolean res = false;
        if (scores.length < 3) return false;
        for (int i = 0; i < scores.length - 2; i++) {
            if (Math.abs(scores[i] - scores[i + 1]) <= 2 && Math.abs(scores[i+1] - scores[i + 2]) <= 2 &&
                    Math.abs(scores[i] - scores[i + 2]) <= 2  ) res = true;
        }
        return res;
    }

}
