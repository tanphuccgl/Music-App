package com.stu.musicapp.model;

import java.util.ArrayList;

public class PlaylistModel {
    String id;
    String name;
    String imageLink;
    ArrayList<String> listIdSong;

    public PlaylistModel(String id, String name, String imageLink, ArrayList<String> listIdSong) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.listIdSong = listIdSong;
    }
}
