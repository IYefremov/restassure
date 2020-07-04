
//Given a string, if the first or last chars are 'x', return the string without those 'x' chars, and otherwise
//        return the string unchanged.
//
//
//        withoutX("xHix") → "Hi"
//        withoutX("xHi") → "Hi"
//        withoutX("Hxix") → "Hxi"

package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(withoutX("xxHi"));
    }

    public static String withoutX(String str) {

        String result = "";

        for (int i=0; i<str.length(); i++){
            if ((!str.substring(i,i+1).equals("x") && i != 0 ) ||
                    ( !str.substring(i,i+1).equals("x") && i !=str.length()-1 )) {
                result = result + str.charAt(i);

            }
//
//            if (i != 0 && i != str.length()-1 ) {
//                result = result + str.charAt(i);
//            }
//            if (str.substring(str.length()-1 ).equals("x") && i == str.length()-1){
//                result = str.replace("x", "");
//            }

 //           result = result+str.charAt(i);
        }

//        if (result.substring(result.length()-1,result.length()).equals("x"))
//        {result = result.replace("x", "");
//        }
        return result;
    }
}
