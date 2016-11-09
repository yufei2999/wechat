package com.yufei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.KuwoSong;
import com.yufei.model.Music;
import com.yufei.service.MusicService;
import com.yufei.utils.CommonUtils;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.List;

/**
 * 音乐搜索实现类：酷我接口
 *
 * Created by pc on 2016-11-08.
 */
public class KuwoMusicServiceImpl implements MusicService {

    private static final Logger logger = Logger.getLogger(KuwoMusicServiceImpl.class);

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

            // 酷我音乐搜索API 音乐列表
            String requestUrl = DataTypeUtils.KUWO_MUSIC_API_LIST + URLEncoder.encode(keyword, DataTypeUtils.ENCODING_UTF8);

            // 音乐列表查询
            String result = CommonUtils.callHttpGetRequest(requestUrl);
            logger.info("result:" + result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getString("abslist");
            List<KuwoSong> list = JSON.parseArray(songs, KuwoSong.class);
            if (list == null || list.isEmpty()) {
                logger.info("result is empty");
                return null;
            }

            // 返回筛选后的音乐
            Music music = null;
            Music musicTemp = null;
            // 音乐详情查询（songid）
            String musicInfo = null;
            // 音乐链接地址
            String songLink = null;
            for (KuwoSong item : list) {
                // 过滤处理
                if (item.getSONGNAME().contains(DataTypeUtils.KEYWORD_SKIP_ACCOMPANIMENT)
                        || item.getSONGNAME().contains(DataTypeUtils.KEYWORD_SKIP_BELL)
                        || item.getSONGNAME().contains(DataTypeUtils.KEYWORD_SKIP_DJ)
                        || item.getSONGNAME().contains(DataTypeUtils.KEYWORD_SKIP_MUSIC)
                        || item.getSONGNAME().contains(DataTypeUtils.KEYWORD_SKIP_FOUR)) {
                    continue;
                }
                // 根据歌曲rid（MUSICRID）进行二次查询
                songLink = CommonUtils.callHttpGetRequest(DataTypeUtils.KUWO_MUSIC_API_DETAIL + item.getMUSICRID());
                if (StringUtils.isBlank(songLink) || songLink.equals("res not found")) {
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
                    if (StringUtils.contains(item.getARTIST().toUpperCase(), artistName.toUpperCase())) {
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
            logger.error("search error in kuwo", e);
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
    private Music getMusic(KuwoSong item, String songLink) {
        Music music = new Music();
        music.setSongName(item.getSONGNAME());
        music.setArtistName(item.getARTIST());
        music.setUrl(songLink);
        return music;
    }

}
