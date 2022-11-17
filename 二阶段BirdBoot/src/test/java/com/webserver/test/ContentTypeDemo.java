package com.webserver.test;

import javax.activation.MimetypesFileTypeMap;

public class ContentTypeDemo {
    public static void main(String[] args) {
        String contentType
                = new MimetypesFileTypeMap().getContentType("123.png");
        System.out.println(contentType);
    }
}
