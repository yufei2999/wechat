package com.yufei.service;

import com.yufei.model.Music;

import java.util.List;

/**
 * 音乐搜索接口
 *
 * Created by LXY on 2016/10/14.
 */
public interface MusicService {

    /**
     * 音乐搜索
     *
     * @param keyword 关键词，格式：
     *                1、歌名，如：仙剑问情
     *                2、歌名+空格+歌手，如：仙剑问情 萧人凤
     * @return
     */
    Music searchMusic(String keyword);

    /**
     * 音乐列表
     *
     * @param keyword
     * @return
     */
    List<Music> getMusicList(String keyword);

}
