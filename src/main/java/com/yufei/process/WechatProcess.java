package com.yufei.process;


import com.yufei.model.Music;
import com.yufei.model.ReceiveXml;
import com.yufei.service.MusicService;
import com.yufei.service.impl.KugouMusicServiceImpl;
import com.yufei.service.impl.KuwoMusicServiceImpl;
import com.yufei.utils.DataTypeUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 微信xml消息处理流程逻辑类
 *
 * @author pamchen-1
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
        // 解析xml数据
        ReceiveXml xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
        if (xmlEntity == null) {
            logger.info("parse wechat data failed");
            return null;
        }

        FormatXmlProcess format = new FormatXmlProcess();

        // xml结果
        String result = "";
        Music music = null;
        if (xmlEntity.getMsgType().equals(DataTypeUtils.WECHAT_MESSAGE_TYPE_EVENT)
                && xmlEntity.getEvent().equals(DataTypeUtils.WECHAT_EVENT_TYPE_SUBSCRIBE)) {
            // 关注事件回复消息
            return format.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), DataTypeUtils.TEXT_MESSAGE_SUBSCRIBE);
        } else if (xmlEntity.getMsgType().equals(DataTypeUtils.WECHAT_MESSAGE_TYPE_TEXT)) {
            // 被动回复用户消息
            logger.info("keyword:" + xmlEntity.getContent());
            MusicService service = null;
            // 百度搜索
            /*service = new BaiduMusicServiceImpl();
            music = service.searchMusic(xmlEntity.getContent());*/
            // 酷我搜索
            service = new KuwoMusicServiceImpl();
            music = service.searchMusic(xmlEntity.getContent());
            // 酷狗搜索
            if (music == null) {
                service = new KugouMusicServiceImpl();
                music = service.searchMusic(xmlEntity.getContent());
            }
        }

        // 返回消息
        if (music == null) {
            // 文本消息
            result = format.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), DataTypeUtils.TEXT_MESSAGE_CONTENT);
        } else {
            // 音乐消息
            logger.info("music:" + music);
            result = format.formatXmlMusic(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), music);
        }

        logger.info("result:" + result);
        return result;
    }

    /**
     * 图文消息
     * @param xml
     * @return
     */
    public String processWechatNewsMsg(String xml) {

        // 解析xml数据
        ReceiveXml xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
        if (xmlEntity == null) {
            logger.info("parse wechat data failed");
            return null;
        }

        FormatXmlProcess format = new FormatXmlProcess();

        // xml结果
        String result = "";
        List<Music> list = null;
        if (xmlEntity.getMsgType().equals(DataTypeUtils.WECHAT_MESSAGE_TYPE_EVENT)
                && xmlEntity.getEvent().equals(DataTypeUtils.WECHAT_EVENT_TYPE_SUBSCRIBE)) {
            // 关注事件回复消息
            return format.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), DataTypeUtils.TEXT_MESSAGE_SUBSCRIBE);
        } else if (xmlEntity.getMsgType().equals(DataTypeUtils.WECHAT_MESSAGE_TYPE_TEXT)) {
            // 被动回复用户消息
            logger.info("keyword:" + xmlEntity.getContent());
            MusicService service = null;
            // 百度搜索
            /*service = new BaiduMusicServiceImpl();
            music = service.getMusicList(xmlEntity.getContent());*/
            // 酷我搜索
            service = new KuwoMusicServiceImpl();
            list = service.getMusicList(xmlEntity.getContent());
            // 酷狗搜索
            if (list == null || list.isEmpty()) {
                service = new KugouMusicServiceImpl();
                list = service.getMusicList(xmlEntity.getContent());
            }
        }

        // 返回消息
        if (list == null || list.isEmpty()) {
            // 文本消息
            result = format.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), DataTypeUtils.TEXT_MESSAGE_CONTENT);
        } else {
            // 音乐消息
            logger.info("music list:" + list);
            result = format.formatXmlNews(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), list);
        }

        logger.info("result:" + result);
        return result;
    }
}