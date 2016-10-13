package com.yufei.model;

/**
 * Created by pc on 2016-10-12.
 */
public class Music {

    /**
     * 音乐名称
     */
    private String songName;
    /**
     * 艺术家
     */
    private String artistName;
    /**
     * 音乐链接
     */
    private String url;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Music{" +
                "songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
