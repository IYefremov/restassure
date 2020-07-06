//Return true if the string "cat" and "dog" appear the same number of times in the given string.
//
//
//        catDog("catdog") → true
//        catDog("catcat") → false
//        catDog("1cat1cadodog") → true

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(catDog("c"));
    }
    public static boolean catDog(String str) {
        int countDog = 0, countCat = 0, index;
        String td;

        String tmpStrDog = str;
        String tmpStrCat = str;

        while (tmpStrDog.contains("dog")){
            index = tmpStrDog.indexOf("dog");
            td = tmpStrDog.substring(0, index) + "XXX" +  tmpStrDog.substring(index + 3);
            tmpStrDog = td;
            countDog ++;
        }

        while (tmpStrCat.contains("cat")){
            index = tmpStrCat.indexOf("cat");
            td = tmpStrCat.substring(0, index) + "XXX" +  tmpStrCat.substring(index + 3);
            tmpStrCat = td;
            countCat ++;
        }

        return (countCat == countDog);
    }
}
