//Suppose the string "yak" is unlucky. Given a string, return a version where all the "yak" are removed,
// but the "a" can be any char. The "yak" strings will not overlap.
//
//
//        stringYak("yakpak") → "pak"
//        stringYak("pakyak") → "pak"
//        stringYak("yak123ya") → "123ya"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(stringYak(""));
    }
    public static String stringYak(String str) {
       // int indexJava = str.indexOf("java"); // Нахождение слова в строке
        int firstLett = str.indexOf("yak");

        while (str.indexOf("yak") != -1){
            firstLett = str.indexOf("yak");
            str = str.substring(0, firstLett) + str.substring(firstLett+3, str.length());

        }

        return str;
    }
}
