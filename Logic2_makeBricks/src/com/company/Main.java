//We want to make a row of bricks that is goal inches long.
// We have a number of small bricks (1 inch each) and big bricks (5 inches each).
// Return true if it is possible to make the goal by choosing from the given bricks.
// This is a little harder than it looks and can be done without any loops.
// See also: Introduction to MakeBricks
//
//
//        makeBricks(3, 1, 8) → true
//        makeBricks(3, 1, 9) → false
//        makeBricks(3, 2, 10) → true

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(makeBricks(1, 4, 21));
    }

    public static boolean makeBricks(int small, int big, int goal) {
       if ((small + big * 5) == goal) return true;
        if (small + (big * 5) > goal) {
            int numOfBigInGoal = goal / 5;
            int ost = goal-numOfBigInGoal*5;
            if (small >= ost) return true;
        }
        return false;


    }

}
