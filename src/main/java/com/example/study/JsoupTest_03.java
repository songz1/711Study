/*
송지은_https://derveljunit.tistory.com/280 따라해보기
 */

package com.example.study;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupTest_03 {
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

    public static void main(String[] args) {
        try {
            // 1. URL 선언
            String connUrl = "https://comic.naver.com";

            // 2. HTML 가져오기
            Connection conn = Jsoup
                    .connect(connUrl)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true);

            Document doc = conn.get();

            // 3. 가져온 HTML Document 를 확인하기
            System.out.println(doc.toString());

        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }
}
