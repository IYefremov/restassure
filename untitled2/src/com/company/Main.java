package com.company;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
       List<String> mylist = new LinkedList<>();
        mylist.add("A");
        mylist.add("B");
        mylist.add("C");
        mylist.add("D");

        Iterator<String> iter = mylist.iterator();//создаем итератор
        while(iter.hasNext()) {//до тех пор, пока в списке есть элементы

            String nextStr = iter.next();//получаем следующий элемент
            if (nextStr.equals("B")) {
                iter.remove();//удаляем кота с нужным именем
                //System.out.println(nextStr);
            }
        }


        for (String elem : mylist){
            System.out.println(elem);
        }

//        for (Iterator<String> it = iter; it.hasNext(); ) {
//            String s = it.next();
//            System.out.println(s);
//        }
    }
}
