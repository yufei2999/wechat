package com.yufei.test;

import com.yufei.model.Music;
import com.yufei.service.MusicService;
import com.yufei.service.impl.BaiduMusicServiceImpl;
import com.yufei.service.impl.KugouMusicServiceImpl;

/**
 * Created by LXY on 2016/10/17.
 */
public class Test {

    public static void main(String[] args) {
        String keyword = "花都开好了 s.h.e";
        // 先从百度搜索
        MusicService service = new BaiduMusicServiceImpl();
        Music music = service.searchMusic(keyword);
        // 如果百度没搜到，再从酷狗搜
        if (music == null) {
            service = new KugouMusicServiceImpl();
            music = service.searchMusic(keyword);
        }
        if (music != null) {
            System.out.println("音乐名称：" + music.getSongName());
            System.out.println("艺 术 家：" + music.getArtistName());
            System.out.println("音乐链接：" + music.getUrl());
        }
    }
}


