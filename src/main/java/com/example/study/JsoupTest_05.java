package com.example.study;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class JsoupTest_05 {
    public static void main(String[] args) {
        try {
            // 1. URL 선언
            String connUrl = "https://comic.naver.com/webtoon/weekday.nhn";

            // 2. SSL 체크
            if (connUrl.indexOf("https://") >= 0) {
                JsoupTest_04.setSSL();
            }

            // 3. HTML 가져오기
            Connection conn = Jsoup
                    .connect(connUrl)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true);

            Document doc = conn.get();

            // class 명이 list_area인 항목 (전체 웹툰 목록)
            Elements items1 = doc.select(".list_area");

//            // id 명이 test인 항목만 가져와라
//            Elements items2 = doc.select("#test");
//
//            // class 명이 test인 항목 안의 a 태그만 가져와라
//            Elements tags = doc.select(".test a");

            // 4. 가져온 HTML Document 를 확인하기
            System.out.println(items1.toString());

        } catch (
                IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        } catch (
                KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (
                NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
