package com.yufei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.KugouSong;
import com.yufei.model.Music;
import com.yufei.service.MusicService;
import com.yufei.utils.CommonUtils;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.List;

/**
 * 音乐搜索实现类：酷狗接口
 *
 * Created by LXY on 2016/10/14.
 */
public class KugouMusicServiceImpl implements MusicService {

    private static final Logger logger = Logger.getLogger(KugouMusicServiceImpl.class);


    /**
     * 根据歌名搜索音乐
     *
     * @param keyword 关键词，格式：
     *                1、歌名，如：仙剑问情
     *                2、歌名+空格+歌手，如：仙剑问情 萧人凤
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

            // 酷狗音乐搜索API 音乐列表
            String requestUrl = DataTypeUtils.KUGOU_MUSIC_API_LIST + URLEncoder.encode(keyword, DataTypeUtils.ENCODING_UTF8);

            // 音乐列表查询
            String result = CommonUtils.callHttpGetRequest(requestUrl);
            logger.info("result:" + result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getJSONObject("data").getString("info");
            List<KugouSong> list = JSON.parseArray(songs, KugouSong.class);
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
            for (KugouSong item : list) {
                // 根据歌曲id（songid）进行二次查询
                musicInfo = CommonUtils.callHttpGetRequest(DataTypeUtils.KUGOU_MUSIC_API_DETAIL + item.getHash());
                json = JSONObject.parseObject(musicInfo);
                logger.info("songInfo:" + json);
                // 过滤处理
                if (item.getFilename().contains(DataTypeUtils.KEYWORD_SKIP_ACCOMPANIMENT)
                        || item.getFilename().contains(DataTypeUtils.KEYWORD_SKIP_BELL)
                        || item.getFilename().contains(DataTypeUtils.KEYWORD_SKIP_DJ)
                        || item.getFilename().contains(DataTypeUtils.KEYWORD_SKIP_MUSIC)
                        || item.getFilename().contains(DataTypeUtils.KEYWORD_SKIP_FOUR)) {
                    continue;
                }
                if (songLink == null) {
                    songLink = json.getString("url");
                }
                // 优先选取艺术家相同的音乐
                if (StringUtils.isNotBlank(artistName) && StringUtils.contains(item.getSingername().toUpperCase(), artistName.toUpperCase())) {
                    music = new Music();
                    music.setSongName(item.getSongname());
                    music.setArtistName(item.getSingername());
                    music.setUrl(json.getString("url"));
                    break;
                }
            }

            // 没有匹艺术家的音乐，则选列表中第一首
            if (music == null) {
                KugouSong song = list.get(0);
                music = new Music();
                music.setSongName(song.getSongname());
                music.setArtistName(song.getSingername());
                music.setUrl(songLink);
            }

            return music;

        } catch (Exception e) {
            logger.error("search error in kugou", e);
        }
        return null;
    }

}
