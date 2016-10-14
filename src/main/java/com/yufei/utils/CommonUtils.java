package com.yufei.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 公用方法类
 *
 * Created by pc on 2016-10-14.
 */
public abstract class CommonUtils {

    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     * 请求网络接口
     *
     * @param requestUrl
     * @return
     */
    public static String callHttpGetRequest(String requestUrl) {
        String result = "";
        try {
            HttpGet request = new HttpGet(requestUrl);
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            logger.error("call httpget error", e);
        }
        return result;
    }

}
