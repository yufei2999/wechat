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

            // 艺术家
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

            // 音乐详情查询（songid）
            String musicInfo = null;
            // 返回具体的音乐信息
            String songInfo = null;
            // 音乐链接地址
            String songLink = null;
            // 返回筛选后的音乐
            Music music = null;
            for (BaiduSong item : list) {
                // 根据歌曲id（songid）进行二次查询
                musicInfo = CommonUtils.callHttpGetRequest(DataTypeUtils.BAIDU_MUSIC_API_DETAIL + item.getSongid());
                json = JSONObject.parseObject(musicInfo);
                logger.info("json:" + json.toString());
                // 结果是列表形式，取第一条（一般也只有一条）
                songInfo = json.getJSONObject("data").getJSONArray("songList").get(0).toString();
                logger.info("songInfo:" + songInfo);

                json = JSONObject.parseObject(songInfo);
                if (songLink == null) {
                    songLink = json.getString("songLink");
                }
                // 优先选取艺术家相同的音乐
                if (StringUtils.isNotBlank(artistName) && StringUtils.contains(json.getString("artistName").toUpperCase(), artistName.toUpperCase())) {
                    music = new Music();
                    music.setSongName(item.getSongname());
                    music.setArtistName(item.getArtistname());
                    music.setUrl(this.dealSongLink(json.getString("songLink")));
                    break;
                }
            }

            // 没有匹艺术家的音乐，则选列表中第一首
            if (music == null) {
                BaiduSong baiduSong = list.get(0);
                music = new Music();
                music.setSongName(baiduSong.getSongname());
                music.setArtistName(baiduSong.getArtistname());
                music.setUrl(this.dealSongLink(songLink));
            }

            logger.info("songLink:" + music.getUrl());
            return music;

        } catch (Exception e) {
            logger.error("search error", e);
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

}
