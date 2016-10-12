package com.yufei.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

/**
 * Created by pc on 2016-10-12.
 */
public class Test {

    public static void main(String[] args) {

        try {
            String keyword = "鲁冰花";
            keyword = URLEncoder.encode(keyword, "utf-8");
            String param = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=xml&callback=&query=" + keyword + "&_=1413017198449";
            HttpGet request = new HttpGet(param);
            String result = "";
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println(response.getEntity());
                result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
