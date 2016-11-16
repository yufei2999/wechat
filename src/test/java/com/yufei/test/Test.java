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
        keyword = "风中有朵雨做的云 孟庭苇";
        MusicService service;
        Music music;

        // 百度搜索
        System.out.println();
        service = new BaiduMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
        System.out.println(service.getMusicList(keyword));

        // 酷狗搜索
        System.out.println();
        service = new KugouMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
        System.out.println(service.getMusicList(keyword));

        // 酷我搜索
        System.out.println();
        service = new KuwoMusicServiceImpl();
        music = service.searchMusic(keyword);
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
        System.out.println(service.getMusicList(keyword));
    }
}
