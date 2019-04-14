package com.example.study;

import org.jsoup.Jsoup;

import java.io.IOException;

public class UserAgentTest {
    public static void main(String[] args) {
        try {

            String strText =
                    Jsoup
                            .connect("http://www.useragentstring.com/")
                            .get()
                            .text();

            System.out.println(strText);

        } catch (IOException ioe) {
            System.out.println("Exception: " + ioe);
        }
    }
}