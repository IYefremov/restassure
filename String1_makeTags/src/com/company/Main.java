//The web is built with HTML strings like "<i>Yay</i>" which draws Yay as italic text.
// In this example, the "i" tag makes <i> and </i> which surround the word "Yay".
// Given tag and word strings, create the HTML string with tags around the word, e.g. "<i>Yay</i>".
//
//
//        makeTags("i", "Yay") → "<i>Yay</i>"
//        makeTags("i", "Hello") → "<i>Hello</i>"
//        makeTags("cite", "Yay") → "<cite>Yay</cite>"

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(makeTags("i", "Yay"));
    }

    public static String makeTags(String tag, String word) {
    return "<" + tag + ">" + word + "</" + tag + ">";
    }

}
