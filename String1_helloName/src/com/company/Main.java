//Given a string name, e.g. "Bob", return a greeting of the form "Hello Bob!".
//
//
//        helloName("Bob") → "Hello Bob!"
//        helloName("Alice") → "Hello Alice!"
//        helloName("X") → "Hello X!"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(helloName("Bob"));
    }

    public static String helloName(String name) {
        return "Hello " + name + "!";
    }
}
