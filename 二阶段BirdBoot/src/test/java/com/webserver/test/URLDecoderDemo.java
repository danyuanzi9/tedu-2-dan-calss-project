package com.webserver.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class URLDecoderDemo {
    public static void main(String[] args) {
        String str = "干饭咯";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(data));

        str = "/regUser?username=%E8%8C%83%E4%BC%A0%E5%A5%87&password=123456";
        try {
            str = URLDecoder.decode(str,"utf-8");
            System.out.println(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
