package com.example.study;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.*;

public class LoginTest_01 {
    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
        );
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public static void main(String[] args) {
        try {
            String forestUrl = "https://forest.skhu.ac.kr";
            String loginUrl = forestUrl + "/Gate/UniLogin.aspx";
            String mainUrl = forestUrl + "/Gate/UniMyMain.aspx";

            if (forestUrl.indexOf("https://") >= 0) {
                LoginTest_01.setSSL();
            }
            if (loginUrl.indexOf("https://") >= 0) {
                LoginTest_01.setSSL();
            }
            if (mainUrl.indexOf("https://") >= 0) {
                LoginTest_01.setSSL();
            }

            Response loginPageResponse =
                    Jsoup.connect("https://forest.skhu.ac.kr/Gate/UniLogin.aspx")
                            .referrer("https://forest.skhu.ac.kr/")
                            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                            .timeout(10 * 1000)
                            .followRedirects(true)
                            .execute();

            System.out.println("Fetched login page");

            //get the cookies from the response, which we will post to the action URL
            Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

            //lets make data map containing all the parameters and its values found in the form
            Map<String, String> mapParams = new HashMap<String, String>();
            //mapParams.put("FormName", "existing");
            //mapParams.put("seclogin", "on");
            mapParams.put("txtID", "");
            mapParams.put("txtPW", "");
            //mapParams.put("remember", "1");
            //mapParams.put("proceed", "Go");

            String strActionURL = "https://forest.skhu.ac.kr/Gate/UniLogin.aspx";

            Response responsePostLogin = Jsoup.connect(strActionURL)
                    .referrer("https://forest.skhu.ac.kr/Gate/UniLogin.aspx")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .timeout(10 * 1000)
                    //post parameters
                    .data(mapParams)
                    //cookies received from login page
                    .cookies(mapLoginPageCookies)
                    //many websites redirects the user after login, so follow them
                    .followRedirects(true)
                    .execute();

            System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

            //parse the document from response
            Document document = responsePostLogin.parse();
            //System.out.println(document);

            //get the cookies
            Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();

            /*
             * For all the subsequent requests, you need to send
             * the mapLoggedInCookies containing cookies
             */

            // 메인 페이지
            Document adminPageDocument = Jsoup.connect("https://forest.skhu.ac.kr/Gate/UniMyMain.aspx")
                    .referrer("https://forest.skhu.ac.kr/Gate/UniMyMain.aspx")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .timeout(10 * 1000)
                    .followRedirects(true)
                    .cookies(mapLoggedInCookies) // 위에서 얻은 '로그인 된' 쿠키
                    .get();

            //System.out.println("HTTP Status Code2: " + adminPageDocument.statusCode());

            //parse the document from response
            //Element document2 = adminPageDocument.getAllElements();
            System.out.println(adminPageDocument.title());



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
