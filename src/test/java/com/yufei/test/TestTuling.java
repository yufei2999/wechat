package com.yufei.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by pc on 2016-10-12.
 */
public class TestTuling {

    public String getTulingResult(String content){
        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
        String apiUrl = "http://www.tuling123.com/openapi/api?key=213f4a20683c4f2c83a41b5f91b19787&info=";
        String param = "";
        try {
            param = apiUrl+ URLEncoder.encode(content,"utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //将参数转为url编码

        /** 发送httpget请求 */
        HttpGet request = new HttpGet(param);
        String result = "";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 请求失败处理 */
        if(null==result){
            return "对不起，你说的话真是太高深了……";
        }

        try {
            JSONObject json = new JSONObject(result);
            //以code=100000为例，参考图灵机器人api文档
            if(100000==json.getInt("code")){
                result = json.getString("text");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new TestTuling().getTulingResult("你好"));
    }

}
