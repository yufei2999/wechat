package com.yufei.test;

import com.yufei.model.Music;
import com.yufei.service.MusicService;
import com.yufei.service.impl.BaiduMusicServiceImpl;
import com.yufei.service.impl.KugouMusicServiceImpl;
import com.yufei.service.impl.KuwoMusicServiceImpl;

/**
 * Created by LXY on 2016/10/17.
 */
public class Test {

    public static void main(String[] args) {
        String keyword = "爱如星火 杨小曼";
        keyword = "仙剑问情 萧人凤";
        MusicService service = null;
        Music music = null;
        // 百度搜索
        service = new BaiduMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
        // 酷狗搜索
        service = new KugouMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
        // 酷我搜索
        service = new KuwoMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
    }
}


