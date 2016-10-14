package com.yufei.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.KugouSong;
import com.yufei.model.Music;
import com.yufei.utils.CommonUtils;
import com.yufei.utils.DataTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by pc on 2016-10-14.
 */
public class KugouMusicService {

    private static final Logger logger = Logger.getLogger(KugouMusicService.class);

    /**
     * 根据歌名搜索音乐
     *
     * @param keyword 关键词，支持格式：
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
            System.out.println("result:" + result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String songs = json.getJSONObject("data").getString("info");
            List<KugouSong> list = JSON.parseArray(songs, KugouSong.class);
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
            for (KugouSong item : list) {
                // 根据歌曲id（songid）进行二次查询
                musicInfo = CommonUtils.callHttpGetRequest(DataTypeUtils.KUGOU_MUSIC_API_DETAIL + item.getHash());
                json = JSONObject.parseObject(musicInfo);
                System.out.println("songInfo:" + json);
                if (songLink == null) {
                    songLink = json.getString("url");
                }
                // 优先选取艺术家相同的音乐
                if (StringUtils.isNotBlank(artistName) && StringUtils.equals(artistName, item.getSingername())) {
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
                music.setUrl(json.getString("url"));
            }

            return music;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // test
    public static void main(String[] args) {
        String keyword = "仙剑问情 萧人凤";
        Music music = new KugouMusicService().searchMusic(keyword);
        System.out.println("音乐名称：" + music.getSongName());
        System.out.println("艺 术 家：" + music.getArtistName());
        System.out.println("音乐链接：" + music.getUrl());
    }

}

// 音乐列表查询
/*
{
    "status": 1,
    "error": "",
    "data": {
        "timestamp": 1476438277,
        "correctiontype": 0,
        "info": [
            {
                "filename": "萧人凤 - 仙剑问情 - DJ小可版",
                "songname": "仙剑问情",
                "m4afilesize": 1213450,
                "320hash": "8707778515b38b9fd34ebc3662b00389",
                "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                "privilege": 0,
                "filesize": 4676083,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 343,
                "othername": "DJ小可版",
                "sqhash": "",
                "topic": "",
                "320filesize": 11704334,
                "isnew": 0,
                "duration": 292,
                "album_id": "939171",
                "hash": "91901bbad29e3c614522d1b8bc5cc32b",
                "singername": "萧人凤",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 1,
                "sqprivilege": 0,
                "album_name": "最新汽车升级版2 电音汽车达人",
                "feetype": 0
            },
            {
                "filename": "萧人凤 - 仙剑问情 - 原版伴奏【《仙剑奇侠传5前传》 游戏原声带】",
                "songname": "仙剑问情",
                "m4afilesize": 1046456,
                "320hash": "f334efe673525201919952383d40b017",
                "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                "privilege": 0,
                "filesize": 4113086,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 866,
                "othername": "原版伴奏",
                "sqhash": "6073bcaa00da48f5b202a6072f0de307",
                "topic": "《仙剑奇侠传5前传》 游戏原声带",
                "320filesize": 10279029,
                "isnew": 0,
                "duration": 256,
                "album_id": "521014",
                "hash": "b6233bfd95626f2e10cde886a49b63ff",
                "singername": "萧人凤",
                "sqfilesize": 24814746,
                "320privilege": 0,
                "sourceid": 1,
                "group": [
                    {
                        "filename": "萧人凤 - 仙剑问情 - 原版伴奏",
                        "songname": "仙剑问情",
                        "m4afilesize": 1046456,
                        "320hash": "f334efe673525201919952383d40b017",
                        "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                        "privilege": 0,
                        "filesize": 4113086,
                        "source": "sc",
                        "album_id": "568816",
                        "ownercount": 866,
                        "othername": "原版伴奏",
                        "topic": "",
                        "320filesize": 10279029,
                        "isnew": 0,
                        "duration": 256,
                        "sqhash": "6073bcaa00da48f5b202a6072f0de307",
                        "Accompany": 1,
                        "singername": "萧人凤",
                        "hash": "b6233bfd95626f2e10cde886a49b63ff",
                        "320privilege": 0,
                        "sourceid": 1,
                        "sqfilesize": 24814746,
                        "bitrate": 128,
                        "srctype": 1,
                        "extname": "mp3",
                        "sqprivilege": 0,
                        "album_name": "仙剑奇侠传五·仙剑奇侠传五前传 游戏原声带",
                        "feetype": 0
                    }
                ],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 1,
                "sqprivilege": 0,
                "album_name": "仙剑奇侠传5前传 游戏原声带",
                "feetype": 0
            },
            {
                "filename": "心然 - 仙剑问情",
                "songname": "仙剑问情",
                "m4afilesize": 1066983,
                "320hash": "953533242f89579f16785d2412f7b509",
                "mvhash": "",
                "privilege": 0,
                "filesize": 4068748,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 782,
                "othername": "",
                "sqhash": "",
                "topic": "",
                "320filesize": 10349956,
                "isnew": 0,
                "duration": 254,
                "album_id": "1596268",
                "hash": "fc8c71b9208d784d73045047f605c7f6",
                "singername": "心然",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 0,
                "sqprivilege": 0,
                "album_name": "缘聚心然",
                "feetype": 0
            },
            {
                "filename": "洛天依 - 仙剑问情",
                "songname": "仙剑问情",
                "m4afilesize": 1030558,
                "320hash": "23f8091389f26d7d331ffcabc6cd52b0",
                "mvhash": "",
                "privilege": 0,
                "filesize": 4070644,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 190,
                "othername": "",
                "sqhash": "",
                "topic": "",
                "320filesize": 10203975,
                "isnew": 0,
                "duration": 254,
                "album_id": "",
                "hash": "c218e6aa729047d9cb753c510bfff20f",
                "singername": "洛天依",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 0,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "萧人凤 - 仙剑问情 - 笛子版纯音乐",
                "songname": "仙剑问情",
                "m4afilesize": 1040919,
                "320hash": "d13fa7139335d09527a6611ccbf7fb0f",
                "mvhash": "",
                "privilege": 0,
                "filesize": 3960154,
                "source": "sc",
                "bitrate": 129,
                "ownercount": 100,
                "othername": "笛子版纯音乐",
                "sqhash": "",
                "topic": "",
                "320filesize": 9897400,
                "isnew": 0,
                "duration": 247,
                "album_id": "",
                "hash": "3e39ecbece8ba1fa81ed3b92e7674d3c",
                "singername": "萧人凤",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 0,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "萧人凤 - 仙剑问情 - 纯音乐版",
                "songname": "仙剑问情",
                "m4afilesize": 1042082,
                "320hash": "",
                "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                "privilege": 0,
                "filesize": 4128600,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 83,
                "othername": "纯音乐版",
                "sqhash": "",
                "topic": "",
                "320filesize": 0,
                "isnew": 0,
                "duration": 258,
                "album_id": "",
                "hash": "4563639a3df34b5a16ae44735c009b44",
                "singername": "萧人凤",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 1,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "董贞 - 现场版(仙剑问情)",
                "songname": "现场版(仙剑问情)",
                "m4afilesize": 954020,
                "320hash": "",
                "mvhash": "",
                "privilege": 0,
                "filesize": 3734592,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 34,
                "othername": "",
                "sqhash": "",
                "topic": "",
                "320filesize": 0,
                "isnew": 0,
                "duration": 233,
                "album_id": "",
                "hash": "d49480c0895cbcb439266f7e2a2e4ec0",
                "singername": "董贞",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 0,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "叶洛洛 - 仙剑问情",
                "songname": "仙剑问情",
                "m4afilesize": 1044623,
                "320hash": "",
                "mvhash": "",
                "privilege": 0,
                "filesize": 4067579,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 27,
                "othername": "",
                "sqhash": "",
                "topic": "",
                "320filesize": 0,
                "isnew": 0,
                "duration": 254,
                "album_id": "",
                "hash": "d7f05e34c578087d416e4725771c1d16",
                "singername": "叶洛洛",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 0,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "萧人凤 - 仙剑问情 - DJ版",
                "songname": "仙剑问情",
                "m4afilesize": 880191,
                "320hash": "1a5e7748c99d21ec94ebbbe45c4392e3",
                "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                "privilege": 0,
                "filesize": 3346137,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 22,
                "othername": "DJ版",
                "sqhash": "",
                "topic": "",
                "320filesize": 8360960,
                "isnew": 0,
                "duration": 209,
                "album_id": "",
                "hash": "7b074ad94fc901838528064312a4f822",
                "singername": "萧人凤",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 1,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            },
            {
                "filename": "萧人凤 - 仙剑问情 - DJ阿刚版",
                "songname": "仙剑问情",
                "m4afilesize": 1113018,
                "320hash": "",
                "mvhash": "75257764b74640d1810eb0bd458dd1a5",
                "privilege": 0,
                "filesize": 4248192,
                "source": "sc",
                "bitrate": 128,
                "ownercount": 22,
                "othername": "DJ阿刚版",
                "sqhash": "",
                "topic": "",
                "320filesize": 0,
                "isnew": 0,
                "duration": 265,
                "album_id": "",
                "hash": "4bf8ef8ef352513ea1a4dcf4426b3669",
                "singername": "萧人凤",
                "sqfilesize": 0,
                "320privilege": 0,
                "sourceid": 1,
                "group": [],
                "srctype": 1,
                "extname": "mp3",
                "Accompany": 1,
                "sqprivilege": 0,
                "album_name": "",
                "feetype": 0
            }
        ],
        "total": 45,
        "istag": 0,
        "correctiontip": "",
        "forcecorrection": 0,
        "istagresult": 0
    },
    "errcode": 0
}
*/

// 音乐详情查询
/*
{
    "fileHead": 100,
    "q": 16835,
    "extra": {
        "320filesize": 11704334,
        "sqfilesize": 0,
        "sqhash": "",
        "128hash": "91901BBAD29E3C614522D1B8BC5CC32B",
        "320hash": "8707778515B38B9FD34EBC3662B00389",
        "128filesize": 4676083
    },
    "fileSize": 4676083,
    "hash": "91901bbad29e3c614522d1b8bc5cc32b",
    "error": "",
    "topic_remark": "",
    "url": "http://fs.open.kugou.com/17e7bcd11d9882a9ac1e1601090adc30/5800ad42/G004/M01/13/09/RA0DAFS15MmAJgzvAEdZ8-5C6AY505.mp3",
    "ctype": 1009,
    "status": 1,
    "stype": 11323,
    "singerHead": "",
    "privilege": 0,
    "fileName": "萧人凤 - 仙剑问情 - DJ小可版",
    "topic_url": "",
    "req_hash": "91901BBAD29E3C614522D1B8BC5CC32B",
    "bitRate": 128,
    "extName": "mp3",
    "errcode": 0,
    "timeLength": 292
}
*/