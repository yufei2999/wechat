package com.yufei.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.Music;
import com.yufei.model.BaiduSong;
import com.yufei.utils.CommonUtils;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pc on 2016-10-12.
 */
public class BaiduMusicService {

    /**
     * 根据歌名搜索音乐
     *
     * @param keyword 关键词，支持格式：
     *                1、歌名，如：光辉岁月
     *                2、歌名+空格+歌手，如：光辉岁月 Beyond
     * @return Music
     */
    public Music searchMusic(String keyword) {

        try {

            if (StringUtils.isBlank(keyword)) {
                System.out.println("keyword is empty");
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
                System.out.println("search failed");
                return null;
            }
            result = result.substring(1, result.length() - 2);
            System.out.println(result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getString("song");
            List<BaiduSong> list = JSON.parseArray(songs, BaiduSong.class);
            if (list == null || list.isEmpty()) {
                System.out.println("result is empty");
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
                // 结果是列表形式，取第一条（一般也只有一条）
                songInfo = json.getJSONObject("data").getJSONArray("songList").get(0).toString();
                System.out.println("songInfo:" + songInfo);

                json = JSONObject.parseObject(songInfo);
                if (songLink == null) {
                    songLink = json.getString("songLink");
                }
                // 优先选取艺术家相同的音乐
                if (StringUtils.isNotBlank(artistName) && StringUtils.equals(artistName, json.getString("artistName"))) {
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

            System.out.println("songLink:" + music.getUrl());
            return music;

        } catch (Exception e) {
            e.printStackTrace();
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

    // test
    public static void main(String[] args) {
        String keyword = "光辉岁月 Beyond";
        Music music = new BaiduMusicService().searchMusic(keyword);
        System.out.println("音乐名称：" + music.getSongName());
        System.out.println("艺 术 家：" + music.getArtistName());
        System.out.println("音乐链接：" + music.getUrl());
    }

}

// 音乐列表查询
/*
{
    "song": [
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月",
            "artistname": "Beyond",
            "control": "0000000000",
            "songid": "7338336",
            "has_mv": "1",
            "encrypted_songid": "60066ff9600956e65d05L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月(国语版)",
            "artistname": "黄家驹",
            "control": "0000000000",
            "songid": "55402399",
            "has_mv": "1",
            "encrypted_songid": "190734d5f9f09561d41c3L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月",
            "artistname": "陈奕迅",
            "control": "0000000000",
            "songid": "44265869",
            "has_mv": "1",
            "encrypted_songid": "98072a3718d095771eff4L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月",
            "artistname": "迪克牛仔,杨培安",
            "control": "0000000000",
            "songid": "1440673",
            "has_mv": "0",
            "encrypted_songid": "250615fba109571f100eL"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月",
            "artistname": "林忆莲",
            "control": "0000000000",
            "songid": "301265",
            "has_mv": "1",
            "encrypted_songid": "0805498d109561cf822L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月(国语)",
            "artistname": "Beyond",
            "control": "0000000000",
            "songid": "257129962",
            "has_mv": "0",
            "encrypted_songid": "3407f537dea095685dd85L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "架子鼓",
            "artistname": "光辉岁月",
            "control": "0000000000",
            "songid": "85135734",
            "has_mv": "0",
            "encrypted_songid": "9007513117609561ce2c4L"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "架子鼓伴奏",
            "artistname": "光辉岁月,网络歌手",
            "control": "0000000000",
            "songid": "84963754",
            "has_mv": "0",
            "encrypted_songid": "730751071aa09561d4e6aL"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "吉他曲 - 木吉他完整版",
            "artistname": "光辉岁月",
            "control": "0000000000",
            "songid": "85052931",
            "has_mv": "0",
            "encrypted_songid": "5107511ce0309561cdbadL"
        },
        {
            "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
            "yyr_artist": "0",
            "songname": "光辉岁月",
            "artistname": "岳薇",
            "control": "0000000000",
            "songid": "16613733",
            "has_mv": "0",
            "encrypted_songid": "4806fd816509561d0f9bL"
        }
    ],
    "album": [
        {
            "albumname": "光辉岁月-二十周年精选",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/dbd27f6ed570ea779ec13420e92613c2/261985190/261985190.jpg",
            "albumid": "194931",
            "artistname": "Beyond"
        },
        {
            "albumname": "光辉岁月十五年",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/91583bcfd626f153ecad8b045b13e22d/262000292/262000292.jpg",
            "albumid": "23667034",
            "artistname": "Beyond"
        },
        {
            "albumname": "光辉岁月",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/4d0986657b054871c5740dc928d8638b/125584157/125584157.jpg",
            "albumid": "44265870",
            "artistname": "陈奕迅"
        },
        {
            "albumname": "光辉岁月",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/3e829010e3061e94097da93e73ceb3e3/261986859/261986859.jpg",
            "albumid": "198114",
            "artistname": "Beyond"
        },
        {
            "albumname": "新光辉岁月",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/ba18ab1ae34971bd266e35af17ad9f69/259073418/259073418.jpg",
            "albumid": "259074037",
            "artistname": "李秋实"
        },
        {
            "albumname": "狼的光辉岁月",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/100423169/100423169.jpg",
            "albumid": "100422213",
            "artistname": "FlameSky FM 36.2"
        },
        {
            "albumname": "Remember the Titans 光辉岁月",
            "artistpic": "http://qukufile2.qianqian.com/data2/pic/100446475/100446475.jpg",
            "albumid": "100361594",
            "artistname": "Trevor Rabin"
        }
    ],
    "order": "artist,song,album",
    "error_code": 22000,
    "artist": [
        {
            "yyr_artist": "0",
            "artistid": "211117107",
            "artistpic": "",
            "artistname": "光辉岁月"
        }
    ]
}
*/

// 音乐详情查询
/*
{
    "errorCode": 22000,
    "data": {
        "xcode": "cdbecbc6918dfc564123b7c1d80498b2",
        "songList": [
            {
                "queryId": "7338336",
                "songId": 7338336,
                "songName": "光辉岁月",
                "artistId": "1100",
                "artistName": "Beyond",
                "albumId": 7325878,
                "albumName": "Band 5 - 世纪组合",
                "songPicSmall": "http://musicdata.baidu.com/data2/pic/117381282/117381282.jpg",
                "songPicBig": "http://musicdata.baidu.com/data2/pic/117381269/117381269.jpg",
                "songPicRadio": "http://musicdata.baidu.com/data2/pic/117381256/117381256.jpg",
                "lrcLink": "http://musicdata.baidu.com/data2/lrc/240400812/240400812.lrc",
                "version": "",
                "copyType": 0,
                "time": 298,
                "linkCode": 22000,
                "songLink": "http://yinyueshiting.baidu.com/data2/music/31f7617e8b14fe9dcaa84605e7cac516/64022199/733833686400128.mp3?xcode=cdbecbc6918dfc56718197b4a0f16666",
                "showLink": "http://yinyueshiting.baidu.com/data2/music/31f7617e8b14fe9dcaa84605e7cac516/64022199/733833686400128.mp3?xcode=cdbecbc6918dfc56718197b4a0f16666",
                "format": "mp3",
                "rate": 128,
                "size": 4784878,
                "relateStatus": "0",
                "resourceType": "0",
                "source": "web"
            }
        ]
    }
}
 */