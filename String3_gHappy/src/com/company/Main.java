//We'll say that a lowercase 'g' in a string is "happy" if there is another 'g' immediately to its left or right.
// Return true if all the g's in the given string are happy.
//
//
//        gHappy("xxggxx") → true
//        gHappy("xxgxx") → false
//        gHappy("xxggyygxx") → false

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(gHappy("gxxgggxyg"));
    }
    public static  boolean gHappy(String str) {
        boolean isHappy = false;
        if (str.length() == 2) {
            if (str.charAt(0) == 'g' && str.charAt(1) == 'g') return true;
        }
        if (str.length() == 0) return true;

        for (int i = 1; i < str.length()-1; i++){
            if (i == str.length()-2 && str.charAt(i+1) == 'g'){
                isHappy = false;
            }
            if (str.charAt(i) == 'g' && (str.charAt(i+1) != 'g' || str.charAt(i-1) != 'g')){
                isHappy = false;
            }
            if (str.charAt(i) == 'g' && (str.charAt(i+1) == 'g' || str.charAt(i-1) == 'g')){
                isHappy = true;
                i++;
            }



        }
        return isHappy;

    }

}
