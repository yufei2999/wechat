package com.yufei.process;


import com.yufei.model.Music;
import com.yufei.model.ReceiveXml;
import com.yufei.service.BaiduMusicService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 微信xml消息处理流程逻辑类
 * 
 * @author pamchen-1
 * 
 */
public class WechatProcess {

    private static final Logger logger = Logger.getLogger(WechatProcess.class);

    /**
     * 解析处理xml
     * 
     * @param xml 接收到的微信数据
     * @return 最终的解析结果（xml格式数据）
     */
    public String processWechatMsg(String xml) {
        /** 解析xml数据 */
        ReceiveXml xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /** 以文本消息为例，获取回复内容 */
        String result = "";
        List<Music> list = null;
        if ("text".endsWith(xmlEntity.getMsgType())) {
            list = new BaiduMusicService().searchMusic(xmlEntity.getContent());
        }
        logger.info(list);

        if (list != null) {
            result = new FormatXmlProcess().formatXmlMusic(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), list.get(0));
        }

        return result;
    }
}