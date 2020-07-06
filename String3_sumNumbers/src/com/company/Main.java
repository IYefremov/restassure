
//Given a string, return the sum of the numbers appearing in the string, ignoring all other characters.
//        A number is a series of 1 or more digit chars in a row.
//        (Note: Character.isDigit(char) tests if a char is one of the chars '0', '1', .. '9'.
//        Integer.parseInt(string) converts a string to an int.)
//
//
//        sumNumbers("abc123xyz") → 123
//        sumNumbers("aa11b33") → 44
//        sumNumbers("7 11") → 18

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(sumNumbers("7 11"));
    }

    public static int sumNumbers(String str) {
        String strRes = "";
        int sum = 0;
        Boolean isSeq = false;
        for (int i = 0; i < str.length(); i++) {


            if (Character.isDigit(str.charAt(i))) {
                strRes += str.charAt(i);
                isSeq = true;
                if (i == str.length()-1){
                    sum += Integer.parseInt(strRes);
                }
            } else {
                if (isSeq){
                    sum += Integer.parseInt(strRes);
                    isSeq = false;
                    strRes = "";
                }
            }

        }
        return sum;
    }

}
