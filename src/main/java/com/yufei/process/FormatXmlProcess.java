package com.yufei.process;

import com.yufei.model.Music;

import java.util.Calendar;

/**
 * 封装最终的xml格式结果
 *
 * @author pamchen-1
 */
public class FormatXmlProcess {
    /**
     * 回复文本消息
     *
     * @param to
     * @param from
     * @param content
     * @return
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("<ToUserName><![CDATA[").append(to).append("]]></ToUserName>");
        xml.append("<FromUserName><![CDATA[").append(from).append("]]></FromUserName>");
        xml.append("<CreateTime>").append(Calendar.getInstance().getTime()).append("</CreateTime>");
        xml.append("<MsgType><![CDATA[text]]></MsgType>");
        xml.append("<Content><![CDATA[").append(content).append("]]></Content>");
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 回复音乐消息
     *
     * @param to
     * @param from
     * @param music
     * @return
     */
    public String formatXmlMusic(String to, String from, Music music) {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("<ToUserName><![CDATA[").append(to).append("]]></ToUserName>");
        xml.append("<FromUserName><![CDATA[").append(from).append("]]></FromUserName>");
        xml.append("<CreateTime>").append(Calendar.getInstance().getTime()).append("</CreateTime>");
        xml.append("<MsgType><![CDATA[music]]></MsgType>");
        xml.append("<Music>");
        xml.append("<Title><![CDATA[").append(music.getSongName()).append("]]></Title>");
        xml.append("<Description><![CDATA[").append(music.getArtistName()).append("]]></Description>");
        xml.append("<MusicUrl><![CDATA[").append(music.getUrl()).append("]]></MusicUrl>");
        xml.append("<HQMusicUrl><![CDATA[").append(music.getUrl()).append("]]></HQMusicUrl>");
        xml.append("</Music>");
        xml.append("</xml>");
        return xml.toString();
    }
}
