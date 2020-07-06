package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Pattern p =  Pattern.compile("[a-c2-3]");
        Matcher m =  p.matcher("fsadkf skfc1+1=2ataj ada efaksj s");

        while (m.find()){
            System.out.println(m.start() + " " + m.group() + " ");
        }
    }
}
