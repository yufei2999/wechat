package com.yufei.process;

import com.yufei.model.Music;

import java.util.Date;

/**
 * 封装最终的xml格式结果
 *
 * @author pamchen-1
 */
public class FormatXmlProcess {
    /**
     * 封装文字类的返回消息
     *
     * @param to
     * @param from
     * @param content
     * @return
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }

    public String formatXmlMusic(String to, String from, Music music) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]</FromUserName>");
        sb.append("<CreateTime>");
        sb.append(new Date().getTime());
        sb.append("</CreateTime>");
        sb.append("<MsgType><![CDATA[music]]></MsgType>");
        sb.append("<Music>");
        sb.append("<Title><![CDATA[");
        sb.append(music.getSongName());
        sb.append("]]></Title>");
        sb.append("<Description><![CDATA[");
        sb.append(music.getArtistName());
        sb.append("]]></Description>");
        sb.append("<MusicUrl>");
        sb.append("<![CDATA[");
        sb.append(music.getUrl());
        sb.append("]]>");
        sb.append("</MusicUrl>");
        sb.append("<HQMusicUrl>");
        sb.append("<![CDATA[");
        sb.append(music.getUrl());
        sb.append("]]>");
        sb.append("</HQMusicUrl>");
        sb.append("<ThumbMediaId><![CDATA[]]></ThumbMediaId>");
        sb.append("</Music>");
        sb.append("</xml>");
        return sb.toString();
    }
}
