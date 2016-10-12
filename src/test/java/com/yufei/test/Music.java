package com.yufei.test;

/**
 * Created by pc on 2016-10-12.
 */
public class Music {

    private String songName;
    private String artistName;
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

    public String toString() {
        return "songName:" + songName + "artistName:" + artistName + "url:" + url;
    }
}
