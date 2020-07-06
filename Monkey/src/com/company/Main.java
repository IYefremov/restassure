//We have two monkeys, a and b, and the parameters aSmile
//        and bSmile indicate
//        if each is smiling. We are in trouble
//        if they are both smiling or
//        if neither of them is smiling.
//
//        Return true if we are in trouble.
//

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        boolean isTrouble = monkeyTrouble(false, false);
        System.out.println("Monkey is in trouble - " + isTrouble);

    }

    public static boolean monkeyTrouble(boolean aSmile, boolean bSmile) {

//        if (aSmile && bSmile) {
//            return true;
//        }
//        if (!aSmile && !bSmile) {
//            return true;
//        }
//
//        return false;
        if ((aSmile && bSmile) || (!aSmile && !bSmile)){
            return true;
        }

        return false;
    }
}