package com.yufei.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufei.model.Music;
import com.yufei.model.Song;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-10-12.
 */
public class BaiduMusicService {

    /**
     * 根据歌名搜索音乐
     *
     * @param musicTitle 音乐名称
     * @return Music
     */
    public List<Music> searchMusic(String musicTitle) {

        try {
            // 百度音乐搜索地址
            String requestUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=xml&callback=&query={TITLE}&_=1413017198449";
            // 对歌名进URL编码
            requestUrl = requestUrl.replace("{TITLE}", URLEncoder.encode(musicTitle));
            // 处理歌名中间的空格
            requestUrl = requestUrl.replaceAll("\\+", "%20");

            // 查询并获取返回结果
            String result = getInfo(requestUrl);
            result = result.substring(1, result.length() - 2);
            System.out.println(result);

            // 获取歌曲信息列表
            JSONObject json = null;
            json = JSONObject.parseObject(result);
            String song = json.getString("song");
            List<Song> sList = JSON.parseArray(song, Song.class);

            String songInfo = null;
            String musicResult = null;
            String url = "http://music.baidu.com/data/music/links?songIds=";
            List<Music> mList = new ArrayList<Music>();
            for (Song item : sList) {
                musicResult = getInfo(url + item.getSongid());
                json = JSONObject.parseObject(musicResult);
                songInfo = json.getJSONObject("data").getJSONArray("songList").get(0).toString();
                System.out.println(songInfo);

                Music m = new Music();
                m.setSongName(item.getSongname());
                m.setArtistName(item.getArtistname());
                m.setUrl(JSONObject.parseObject(songInfo).getString("songLink"));
                mList.add(m);
            }
            return mList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getInfo(String requestUrl) {
        String result = "";
        try {
            HttpGet request = new HttpGet(requestUrl);
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
