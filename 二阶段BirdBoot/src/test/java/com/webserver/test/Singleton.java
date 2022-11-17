package com.webserver.test;

/**
 * 单例模式
 * 1:私有化构造器(防止外界通过new来实例化对象)
 * 2:提供一个静态方法用于将当前实例返回给外界
 * 3:提供一个静态的私有的当前类型实例的属性并初始化(确保只有一个实例)
 */
public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton(){}

    public static Singleton getInstance(){
         return instance;
    }
}
