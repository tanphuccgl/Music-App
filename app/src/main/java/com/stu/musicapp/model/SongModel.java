package com.stu.musicapp.model;

public class SongModel {
    String id ;
    String name;
    String author;
    String artist;
    String idAlbum;
    String linkSong;

    public String getId() {
        return id;
    }

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
