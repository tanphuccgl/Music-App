package com.levi.musicapp.model;

import java.util.ArrayList;

public class AlbumModel {
    String id;
    String name;
    String artist;
    String imageLink;
    ArrayList<String> listIdSong;

    public AlbumModel(String id, String name, String artist, String imageLink, ArrayList<String> listIdSong) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.imageLink = imageLink;
        this.listIdSong = listIdSong;
    }
}
