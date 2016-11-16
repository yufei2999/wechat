package com.yufei.process;

import com.sun.org.apache.xerces.internal.impl.dv.DatatypeException;
import com.yufei.model.Music;
import com.yufei.utils.DataTypeUtils;

import java.util.Calendar;
import java.util.List;

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

    /**
     * 回复图文消息
     *
     * @param to
     * @param from
     * @param list
     * @return
     */
    public String formatXmlNews(String to, String from, List<Music> list) {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("<ToUserName><![CDATA[").append(to).append("]]></ToUserName>");
        xml.append("<FromUserName><![CDATA[").append(from).append("]]></FromUserName>");
        xml.append("<CreateTime>").append(Calendar.getInstance().getTime()).append("</CreateTime>");
        xml.append("<MsgType><![CDATA[news]]></MsgType>");
        xml.append("<ArticleCount>").append(list.size()).append("</ArticleCount>");
        xml.append("<Articles>");
        for (Music item : list) {
            xml.append("<item>");
            xml.append("<Title><![CDATA[").append(item.getArtistName()).append(" - ").append(item.getSongName()).append("]]></Title>");
            xml.append("<Description><![CDATA[").append(item.getSongName()).append("]]></Description>");
            xml.append("<PicUrl><![CDATA[").append(DataTypeUtils.SERVICE_URL).append("images/default.png]]></PicUrl>");
            xml.append("<Url><![CDATA[")
                    .append(DataTypeUtils.SERVICE_URL)
                    .append("music?songName=").append(item.getSongName())
                    .append("&artistName=").append(item.getArtistName())
                    .append("&url=").append(item.getUrl()).append("]]></Url>");
            xml.append("</item>");
        }
        xml.append("</Articles>");
        xml.append("</xml>");
        return xml.toString();
    }
}
