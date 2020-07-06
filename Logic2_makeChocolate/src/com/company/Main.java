//We want make a package of goal kilos of chocolate. We have small bars (1 kilo each) and big bars (5 kilos each).
// Return the number of small bars to use, assuming we always use big bars before small bars. Return -1 if it can't be done.
//
//
//        makeChocolate(4, 1, 9) → 4
//        makeChocolate(4, 1, 10) → -1
//        makeChocolate(4, 1, 7) → 2
//
package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(makeChocolate(6, 8, 11));
    }
    public static int makeChocolate(int small, int big, int goal) {
        int needBig = 0;
        if ((small + big*5) < goal) return -1;
        needBig = goal/5;

        if (needBig > big) needBig = big;

        if (small >= goal - needBig*5)
            return goal - needBig*5;
        return -1;
    }

}
