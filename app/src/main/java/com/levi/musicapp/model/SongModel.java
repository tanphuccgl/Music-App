package com.levi.musicapp.model;

import java.util.ArrayList;

public class SongModel {
    String id ;
    String name;
    String author;
    String artist;
    String idAlbum;
    String linkSong;
    String imageLink;
    String lyric;

    public SongModel(
        String id, String name, String author, String artist, String idAlbum, String linkSong, String imageLink, String lyric
    ) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.artist = artist;
        this.idAlbum = idAlbum;
        this.linkSong = linkSong;
        this.imageLink = imageLink;
        this.lyric = lyric;
    }

}
