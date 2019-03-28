package com.example.study;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupTest_02 {
    public static void main(String[] args) {
        try {
            // 1. URL 선언
            String connUrl = "https://comic.naver.com";

            // 2. HTML 가져오기
            Document doc = Jsoup.connect(connUrl).post();

            // 3. 가져온 HTML Document 를 확인하기
            System.out.println(doc.toString());

        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }
}
