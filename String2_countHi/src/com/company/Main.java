//Return the number of times that the string "hi" appears anywhere in the given string.
//
//
//        countHi("abc hi ho") → 1
//        countHi("ABChi hi") → 2
//        countHi("hihi") → 2
//
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(countHi(""));
    }

    public static int countHi(String str) {
        int index;
        int counter = 0;
        String strTmp;
        while (str.contains("hi")) {
            index = str.indexOf("hi");
            strTmp = str.substring(0, index) + str.substring(index+2);
            str = strTmp;
            System.out.println(strTmp);
            counter ++;
        }

        return counter;

    }

}
