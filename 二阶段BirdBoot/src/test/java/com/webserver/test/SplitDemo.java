package com.webserver.test;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SplitDemo {
    public static void main(String[] args) {
        String line = "1=2=3=4=5=6======";
        String[] array = line.split("=");
        //array:{1,2,3,4,5,6}
        System.out.println(Arrays.toString(array));

        //limit=2  表达仅拆分为两项
        array = line.split("=",2);
        //array:{1,2=3=4=5=6======}
        System.out.println(Arrays.toString(array));

        //limit=3  表达仅拆分为三项
        array = line.split("=",3);
        //array:{1,2,3=4=5=6======}
        System.out.println(Arrays.toString(array));

        //当可拆分项不足limit时，仅保留所有可拆分项
        array = line.split("=",100);
        //array:{1,2,3,4,5,6,,,,,,,}
        System.out.println(Arrays.toString(array));

        //当limit为0时，作用与split(String regex)一致
        array = line.split("=",0);
        //array:{1,2,3,4,5,6}
        System.out.println(Arrays.toString(array));

        //limit为负数时为应拆尽拆并且全保留,所有负数都一样。
        array = line.split("=",-1);
        //array:{1,2,3,4,5,6,,,,,,,}
        System.out.println(Arrays.toString(array));


    }
}
