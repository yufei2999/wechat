package com.yufei.servlet;

import com.yufei.utils.SHA1;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by pc on 2016-10-11.
 */
public class WechatVerifyServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(WechatVerifyServlet.class);

    // 自定义 token
    private String TOKEN = "yufei";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        logger.info("--------signature=" + signature);

        String[] str = {TOKEN, timestamp, nonce};
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        logger.info("--------bigStr=" + bigStr);
        // SHA1加密
        String digest = SHA1.getDigestOfString(bigStr).toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            logger.info("--------success");
            response.getWriter().print(echostr);
        }
    }

}
