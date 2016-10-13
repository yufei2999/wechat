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
     * 封装文字类的返回消息
     *
     * @param to
     * @param from
     * @param content
     * @return
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(String.valueOf(Calendar.getInstance().getTime()));
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }

    public String formatXmlMusic(String to, String from, Music music) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[$toUserName]]></ToUserName>" +
                "<FromUserName><![CDATA[$fromUserName]]></FromUserName>" +
                "<CreateTime>$createTime</CreateTime>" +
                "<MsgType><![CDATA[music]]></MsgType>" +
                "<Music>" +
                "<Title><![CDATA[$title]]></Title>" +
                "<Description><![CDATA[DESCRIPTION]]></Description>" +
                "<MusicUrl><![CDATA[$musicUrl]]></MusicUrl>" +
                "<HQMusicUrl><![CDATA[$hQMusicUrl]]></HQMusicUrl>" +
                "</Music>" +
                "</xml>";
        xml = xml.replace("$toUserName", to)
                .replace("$fromUserName", from)
                .replace("$createTime", String.valueOf(Calendar.getInstance().getTime()))
                .replace("$title", music.getSongName())
                .replace("$musicUrl", music.getUrl())
                .replace("$hQMusicUrl", music.getUrl());
        return xml;
    }
}
