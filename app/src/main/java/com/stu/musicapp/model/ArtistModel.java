package com.stu.musicapp.model;

import java.util.ArrayList;

public class ArtistModel {
    String id;
    String name;
    String imageLink;

    public ArtistModel(String id, String name, String imageLink, ArrayList<String> listIdSong) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.listIdSong = listIdSong;
    }

    ArrayList<String> listIdSong;

}
