//Return the number of times that the string "code" appears anywhere in the given string,
// except we'll accept any letter for the 'd', so "cope" and "cooe" count.
//
//
//        countCode("aaacodebbb") → 1
//        countCode("codexxcode") → 2
//        countCode("cozexxcope") → 2

package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(countCode("cozfxxco e"));
    }

    public static int countCode(String str) {
        int counter = 0;

        Pattern pattern = Pattern.compile("co\\Se");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
    //        System.out.println(str.substring(matcher.start(), matcher.end()));
            counter++;
        }
        return counter;


//        int counter = 0;
//        int index;
//        String tmpStr ="";
//
//        if (str.length() < 4) return 0;
//
//        while (str.contains("co")) {
//
//            index = str.indexOf("co");
//            if (str.length() - index < 4) return counter;
//            if (index != -1) {
//                if (str.charAt(index+3) == 'e'){
//                tmpStr += str.substring(0, index) + "XXXX" + str.substring(index+4);
//                str = tmpStr;
//                counter ++;
//                }else{
//                    str = str.substring(0, index) + "XX" + str.substring(index+2);
//                }
//
//                tmpStr = "";
//
//            }
//        }
//
//        return counter;
    }

}
