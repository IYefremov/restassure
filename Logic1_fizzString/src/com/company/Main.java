//Given a string str, if the string starts with "f" return "Fizz".
// If the string ends with "b" return "Buzz". If both the "f" and "b" conditions are true, return "FizzBuzz".
// In all other cases, return the string unchanged. (See also: FizzBuzz Code)
//
//
//        fizzString("fig") → "Fizz"
//        fizzString("dib") → "Buzz"
//        fizzString("fib") → "FizzBuzz"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(fizzString("dfid"));
    }

    public static String fizzString(String str) {
        if (str.substring(0, 1).equals("f") && str.substring(str.length()-1).equals("b")) return "FizzBuzz";
        if (str.substring(0, 1).equals("f")) return "Fizz";
        if (str.substring(str.length()-1).equals("b")) return "Buzz";
        return str;
    }

}
