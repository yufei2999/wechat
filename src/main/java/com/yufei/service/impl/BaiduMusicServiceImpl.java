package com.yufei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.Music;
import com.yufei.model.BaiduSong;
import com.yufei.service.MusicService;
import com.yufei.utils.CommonUtils;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 音乐搜索实现类：百度接口
 *
 * Created by pc on 2016-10-12.
 */
public class BaiduMusicServiceImpl implements MusicService {

    private static final Logger logger = Logger.getLogger(BaiduMusicServiceImpl.class);

    /**
     * 根据歌名搜索音乐
     *
     * @param keyword 关键词，格式：
     *                1、歌名，如：光辉岁月
     *                2、歌名+空格+歌手，如：光辉岁月 Beyond
     * @return Music
     */
    public Music searchMusic(String keyword) {

        try {

            if (StringUtils.isBlank(keyword)) {
                logger.info("keyword is empty");
                return null;
            }

            // 歌手
            String artistName = null;
            if (keyword.contains(" ")) {
                artistName = keyword.split(" ")[1];
            }

            // 百度音乐搜索API 音乐列表
            String requestUrl = DataTypeUtils.BAIDU_MUSIC_API_LIST + Calendar.getInstance().getTimeInMillis();
            // 关键词转码
            requestUrl = requestUrl.replace("{keyword}", URLEncoder.encode(keyword.replaceAll(" ", ""), DataTypeUtils.ENCODING_UTF8));

            // 音乐列表查询
            String result = CommonUtils.callHttpGetRequest(requestUrl);
            if (StringUtils.contains(result, "\"errno\":22001")) {
                logger.info("search failed");
                return null;
            }
            result = result.substring(1, result.length() - 2);
            logger.info("result:" + result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getString("song");
            List<BaiduSong> list = JSON.parseArray(songs, BaiduSong.class);
            if (list == null || list.isEmpty()) {
                logger.info("result is empty");
                return null;
            }

            // 返回的音乐对象
            Music music = null;
            Music musicTemp = null;
            // 音乐链接地址
            String songLink = null;
            for (BaiduSong item : list) {
                json = this.getSongInfoJson(item.getSongid());
                if (json == null) {
                    continue;
                }
                songLink = this.dealSongLink(json.getString("songLink"));
                if (StringUtils.isBlank(songLink)) {
                    continue;
                }
                if (StringUtils.isBlank(artistName)) {
                    // 关键词中不含歌手，取列表中有音乐链接地址的数据
                    if (music == null) {
                        music = this.getMusic(item, songLink);
                        break;
                    }
                } else {
                    // 关键词中含歌手，取对应歌手的数据
                    if (StringUtils.contains(json.getString("artistName").toUpperCase(), artistName.toUpperCase())) {
                        music = this.getMusic(item, songLink);
                        break;
                    }
                }
                // 关键词中含歌手,但对应歌手的数据没取到，则从这里取数据
                if (musicTemp == null) {
                    musicTemp = this.getMusic(item, songLink);
                }
            }

            if (music == null) {
                music = musicTemp;
            }
            return music;

        } catch (Exception e) {
            logger.error("search error", e);
        }
        return null;
    }

    /**
     * 组装Music对象
     *
     * @param item
     * @param songLink
     * @return
     */
    private Music getMusic(BaiduSong item, String songLink) {
        Music music = new Music();
        music.setSongName(item.getSongname());
        music.setArtistName(item.getArtistname());
        music.setUrl(songLink);
        return music;
    }

    /**
     * 根据songId取音乐链接地址
     *
     * @param songId
     * @return
     */
    private JSONObject getSongInfoJson(String songId) {
        // 根据歌曲id（songid）进行二次查询
        String musicInfo = CommonUtils.callHttpGetRequest(DataTypeUtils.BAIDU_MUSIC_API_DETAIL + songId);
        JSONObject json = JSONObject.parseObject(musicInfo);
        logger.info("json:" + json.toString());

        json = json.getJSONObject("data");
        // songList下有数据再取，有时候可能网络超时等原因导致请求不到数据
        if (json.get("songList").toString().length() > 2) {
            // 结果是列表形式，取第一条（一般也只有一条）
            String songInfo = json.getJSONArray("songList").get(0).toString();
            logger.info("songInfo:" + songInfo);

            json = JSONObject.parseObject(songInfo);
            return json;
        }
        return null;
    }

    /**
     * 对返回的链接做处理
     *
     * @param songLink
     * @return
     */
    private String dealSongLink(String songLink) {
        if (StringUtils.contains(songLink, "&src=")) {
            songLink = songLink.substring(0, songLink.indexOf("&src="));
        }
        return songLink;
    }

    public List<Music> getMusicList(String keyword) {

        try {

            if (StringUtils.isBlank(keyword)) {
                logger.info("keyword is empty");
                return null;
            }
            // 百度音乐搜索API 音乐列表
            String requestUrl = DataTypeUtils.BAIDU_MUSIC_API_LIST + Calendar.getInstance().getTimeInMillis();
            // 关键词转码
            requestUrl = requestUrl.replace("{keyword}", URLEncoder.encode(keyword.replaceAll(" ", ""), DataTypeUtils.ENCODING_UTF8));

            // 音乐列表查询
            String result = CommonUtils.callHttpGetRequest(requestUrl);
            if (StringUtils.contains(result, "\"errno\":22001")) {
                logger.info("search failed");
                return null;
            }
            result = result.substring(1, result.length() - 2);
            logger.info("result:" + result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getString("song");
            List<BaiduSong> list = JSON.parseArray(songs, BaiduSong.class);
            if (list == null || list.isEmpty()) {
                logger.info("result is empty");
                return null;
            }

            // 音乐列表
            List<Music> musicList = new ArrayList<>();
            // 音乐链接地址
            String songLink;
            for (BaiduSong item : list) {
                json = this.getSongInfoJson(item.getSongid());
                if (json == null) {
                    continue;
                }
                songLink = this.dealSongLink(json.getString("songLink"));
                if (StringUtils.isBlank(songLink)) {
                    continue;
                }
                musicList.add(this.getMusic(item, songLink));
            }
            return musicList;
        } catch (Exception e) {
            logger.error("search error", e);
        }
        return null;
    }
}
