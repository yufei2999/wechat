package com.yufei.utils;

/**
 * 常量类
 *
 * Created by pc on 2016-10-12.
 */
public abstract class DataTypeUtils {
    
    /**
     * 编码格式  UTF-8
     */
    public static final String ENCODING_UTF8 = "UTF-8";

    /**
     * 百度音乐搜索API 音乐列表
     */
    public static final String BAIDU_MUSIC_API_LIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=xml&callback=&query={keyword}&_=";

    /**
     * 百度音乐搜索API 音乐详情
     */
    public static final String BAIDU_MUSIC_API_DETAIL = "http://music.baidu.com/data/music/links?songIds=";

    /**
     * 酷狗音乐搜索API 音乐列表
     */
    public static final String KUGOU_MUSIC_API_LIST = "http://mobilecdn.kugou.com/api/v3/search/song?format=json&page=1&pagesize=10&showtype=1&keyword=";

    /**
     * 酷狗音乐搜索API 音乐详情
     */
    public static final String KUGOU_MUSIC_API_DETAIL = "http://m.kugou.com/app/i/getSongInfo.php?cmd=playInfo&hash=";

    /**
     * 无查询结果时返回的文本消息
     */
    public static final String TEXT_MESSAGE_CONTENT = "很抱歉，没有查询到您搜索的音乐，请更换关键词再试一下，关键词参考格式：\n1、歌名，如：光辉岁月\n2、歌名+空格+歌手，如：光辉岁月 Beyond";

}
