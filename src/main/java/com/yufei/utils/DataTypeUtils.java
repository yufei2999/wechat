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
     * 微信消息类型：文本 text
     */
    public static final String WECHAT_MESSAGE_TYPE_TEXT = "text";
    /**
     * 微信消息类型：事件 event
     */
    public static final String WECHAT_MESSAGE_TYPE_EVENT = "event";
    /**
     * 微信事件类型：关注
     */
    public static final String WECHAT_EVENT_TYPE_SUBSCRIBE = "subscribe";

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
     * 酷我音乐搜索API 音乐列表
     */
    public static final String KUWO_MUSIC_API_LIST = "http://search.kuwo.cn/r.s?ft=music&itemset=web_2013&client=kt&pn=0&rn=5&rformat=json&encoding=utf8&all=";
    /**
     * 酷我音乐搜索API 音乐详情（只有链接地址）
     */
    public static final String KUWO_MUSIC_API_DETAIL = "http://antiserver.kuwo.cn/anti.s?type=convert_url&format=mp3&response=url&rid=";

    /**
     * 无查询结果时返回的文本消息
     */
    public static final String TEXT_MESSAGE_CONTENT = "很抱歉，没有查询到您搜索的音乐，请更换关键词再试一下，关键词参考格式：\n1、歌名，如：光辉岁月\n2、歌名+空格+歌手，如：光辉岁月 Beyond";
    /**
     * 关注公众号时返回的文本消息
     */
    public static final String TEXT_MESSAGE_SUBSCRIBE = "欢迎光临！\n在这里可以充分施展你的任性，尽情搜索你喜欢的歌曲，搜索方式：\n1、歌名，如：光辉岁月\n2、歌名+空格+歌手，如：光辉岁月 Beyond\n预祝玩得愉快！";

    /**
     * 过滤掉的歌曲版本：伴奏
     */
    public static final String KEYWORD_SKIP_ACCOMPANIMENT = "伴奏";
    /**
     * 过滤掉的歌曲版本：铃声
     */
    public static final String KEYWORD_SKIP_BELL = "铃声";
    /**
     * 过滤掉的歌曲版本：DJ
     */
    public static final String KEYWORD_SKIP_DJ = "DJ";
    /**
     * 过滤掉的歌曲版本：纯音乐
     */
    public static final String KEYWORD_SKIP_MUSIC = "纯音乐";
    /**
     * 过滤掉的歌曲版本：并四
     */
    public static final String KEYWORD_SKIP_FOUR = "并四";

}
