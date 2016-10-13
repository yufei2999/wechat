package com.yufei.servlet;

import com.yufei.process.WechatProcess;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 微信请求接口类
 *
 * Created by pc on 2016-10-12.
 */
public class WechatServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(WechatServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding(DataTypeUtils.ENCODING_UTF8);
        response.setCharacterEncoding(DataTypeUtils.ENCODING_UTF8);

        // 返回的消息
        String result = "";
        // 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回
        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            // 服务器设置，配置参数，激活验证
            result = echostr;
        } else {
            // 正常的微信处理流程
            StringBuffer xml = new StringBuffer();
            // 接收微信消息
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), DataTypeUtils.ENCODING_UTF8));
            String s = "";
            while ((s = br.readLine()) != null) {
                xml.append(s);
            }
            // 返回消息
            result = new WechatProcess().processWechatMsg(xml.toString());
        }

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes(DataTypeUtils.ENCODING_UTF8));
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("OutputStream write error", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
