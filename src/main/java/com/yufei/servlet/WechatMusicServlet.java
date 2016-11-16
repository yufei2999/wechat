package com.yufei.servlet;

import com.yufei.process.WechatProcess;
import com.yufei.service.impl.KuwoMusicServiceImpl;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by pc on 2016-11-16.
 */
public class WechatMusicServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(WechatServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding(DataTypeUtils.ENCODING_UTF8);
        response.setCharacterEncoding(DataTypeUtils.ENCODING_UTF8);

        // 返回的消息
        String result = "";
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
            result = new WechatProcess().processWechatNewsMsg(xml.toString());
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
