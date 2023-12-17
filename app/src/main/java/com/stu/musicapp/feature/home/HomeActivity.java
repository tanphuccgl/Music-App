package com.stu.musicapp.feature.home;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stu.musicapp.R;
import com.stu.musicapp.feature.signin.SignInActivity;
import com.stu.musicapp.model.AccountModel;
import com.stu.musicapp.model.SongModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HomeActivity extends AppCompatActivity {

    ListView songListView;
    ArrayList<SongModel> list = new ArrayList<SongModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        songListView = (ListView) findViewById(R.id.songListView);

        final ArrayAdapter  adapter = new ArrayAdapter<SongModel>(HomeActivity.this,
                R.layout.song_item, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // kiem tra neu convertView khong co => khong lay out thi gan lai lay out cho convertView
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.song_item, parent, false);
                }

                // lay data
                SongModel song = getItem(position);

                // khai bao bien va dinh danh voi id
                TextView nameSongItemTextView = (TextView) convertView.findViewById(R.id.name);
                ImageView songItemImageView = (ImageView) convertView.findViewById(R.id.songItemImageView);

                //gan gia tri
                nameSongItemTextView.setText(song.getName().toString());
                Picasso.with(getContext()).load(song.getImageLink()).fit().into(songItemImageView);
                return convertView;
            }
        };


                getSongFromDatabase(new SongListener() {
            @Override
            public void onSongReceived(ArrayList<SongModel> songModelArrayList) {
                if(!songModelArrayList.isEmpty())
                {
                    list.addAll(songModelArrayList);
                       Log.d("stu", "danh sach bai hat onSongReceived" +list.size());
//                    Log.d("stu", "danh sach bai hat setAdapter" +songListView.getCount());
                    // goi ra UI view -> hien thi len giao dien
                    songListView.setAdapter(adapter);

                }
            }
        });


        adapter.setNotifyOnChange(true);





    }


    interface SongListener {
        void onSongReceived(ArrayList<SongModel> songModelArrayList);
    }
  void getSongFromDatabase( final SongListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         ArrayList<SongModel> listResult = new ArrayList<SongModel>();
         // call hàm A -> có gia trị -> trả về từ ham đó A

      // call hàm A (ham A chay bat dong bo) -> luu vao ham B (listen) -> khi co gia tri
      // tu ham A -> ham B se tu dong cap nhat

    db.collection("songs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (!task.isSuccessful()) {
                                Log.d("stu", "Lấy danh sách nhạc thất bại", task.getException());
                                showToast("Lấy danh sách nhạc thất bại");
                                listener.onSongReceived(listResult);
                                return ;
                            }

                            List<DocumentSnapshot> listSong = task.getResult().getDocuments();
                            if (listSong.isEmpty()) {
                                Log.d("stu", "Không có phần tử nào được trả về ");
                                showToast("Không tìm thấy nhạc");
                                listener.onSongReceived(listResult);
                                return ;
                            }

                            for (DocumentSnapshot item : listSong) {

                                Map<String,Object> mapValue = item.getData();
                                Gson gson = new Gson();
                                String jsonString = new Gson().toJson(mapValue);
                                SongModel data =  gson.fromJson(jsonString, SongModel.class);
                                listResult.add(data);
                            }

                            listener.onSongReceived(listResult);
                        } catch (Exception e) {
                            Log.d("stu", "lỗi server ", e);

                            showToast("Lỗi app");
                            listener.onSongReceived(listResult);
                        }

                    }
                });


        Log.d("stu", "danh sach listSong " + listResult.size());



    }
    void showToast(String text){
        Toast.makeText(HomeActivity.this, text,
                Toast.LENGTH_LONG).show();
    }
}


