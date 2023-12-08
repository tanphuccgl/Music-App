package com.stu.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.stu.musicapp.model.SongModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongModel value = new SongModel("1","mua dong gia lanh","tac gia","nghe si","1","link song","image link","lyrix");
    }
}