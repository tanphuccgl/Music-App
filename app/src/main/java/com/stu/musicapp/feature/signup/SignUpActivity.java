package com.stu.musicapp.feature.signup;

import static android.app.PendingIntent.getActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stu.musicapp.MainActivity;
import com.stu.musicapp.R;
import com.stu.musicapp.feature.signin.SignInActivity;
import com.stu.musicapp.model.AccountModel;
import com.stu.musicapp.model.SongModel;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
        EditText nameEditText;
        EditText emailEditText;
        EditText passwordEditText;
        Button buttonSignUp;
         TextView signInTextView;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acticity_sign_up);

            // Khởi tạo định danh cho biến
            nameEditText = (EditText) findViewById(R.id.nameSignUpEditText);
            emailEditText = (EditText) findViewById(R.id.emailSignUpEditText);
            passwordEditText = (EditText) findViewById(R.id.passwordSignUpEditText);
            buttonSignUp = (Button) findViewById(R.id.signUpButton);
            signInTextView = (TextView) findViewById(R.id.signInTextView) ;
            buttonSignUp.setBackgroundColor(0xFFB1B0FC);

            signInTextView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("stu", "Onclick textview sign in");
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    }
            );



            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: tao danh sach bai hat tren database
                   // createListSongToDatabase();
                    String name = nameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    if(name.isEmpty() || email.isEmpty() || password.isEmpty())
                    {
                        showToast("Vui lòng điền đầy đủ thông tin");
                        return ;
                    }

                    createAccount(name,email,password);
                }
            });
        }


    Map<String, Object> convertModelToMap(AccountModel data){
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            Log.d("stu", jsonString);
            Map<String, Object> accountMap = new Gson().fromJson(
                    jsonString, new TypeToken<HashMap<String, Object>>() {}.getType()
            );
            Log.d("stu2", accountMap.toString());
            return accountMap;
        }

        void resetData(){
            nameEditText.setText("");
            emailEditText.setText("");
            passwordEditText.setText("");
        }

       void showToast(String text){
           Toast.makeText(SignUpActivity.this, text,
                   Toast.LENGTH_LONG).show();
       }



        void createAccount(String name,String email,String password)
        {
            try{
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                //Tạo id tự động
                String uniqueID = UUID.randomUUID().toString();

                AccountModel accountModel = new AccountModel(uniqueID,name,password,"","",email,"");
                //  Log.d("stu3", obj.toString());

                Map<String,Object> accountMap =  convertModelToMap(accountModel);

                database.collection("accounts").document(uniqueID).set(accountMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showToast("Đăng ký thành công");
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast("Đăng ký thất bại");
                                resetData();
                                Log.w("stu", "Error writing document", e);
                            }
                        });

            }catch (Exception e){
                showToast("Đăng ký thất bại");
                resetData();
                Log.w("stu", "Error Exception document", e);

            }

        }


        void createListSongToDatabase(){
            String linkImage1 = "https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2030.png?alt=media&token=8f380ab1-a4b0-4f71-b752-ff4560df907d";
            String linkImage2 ="https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2031.png?alt=media&token=dcbbcc31-3544-4bd4-bfd1-e0877777e901";
                    String linkImage3 ="https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2032.png?alt=media&token=23162fd5-5b92-4b98-8af1-79480a226ca5";
                    String linkImage4 = "https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2034.png?alt=media&token=7006ad82-40e9-48d0-a9c7-0975feec052a";
                    String linkImage5 = "https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2035.png?alt=media&token=b71b8ca8-6e78-47f9-8b62-d081f746bddc";
                    String linkImage6 = "https://firebasestorage.googleapis.com/v0/b/music-app-5ab94.appspot.com/o/Rectangle%2043.png?alt=media&token=9378a14d-1ab6-4170-9f46-2a56c8988f63";

                    List<SongModel> listSong = new ArrayList<>();
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat1","","","","",linkImage1,""));
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat2","","","","",linkImage2,""));
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat3","","","","",linkImage3,""));
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat4","","","","",linkImage4,""));
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat5","","","","",linkImage5,""));
            listSong.add(new SongModel(UUID.randomUUID().toString(),"ten bai hat6","","","","",linkImage6,""));

            for (int i = 0; i < listSong.size(); i++) {
                createSongToDatabase(listSong.get(i));
            }



        }

    void createSongToDatabase(SongModel songModel)
    {
        try{
            FirebaseFirestore database = FirebaseFirestore.getInstance();

            Map<String,Object> songMap =  convertSongModelToMap(songModel);

            database.collection("songs").document(songModel.getId()).set(songMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showToast("tao bai hat thanh cong");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToast("tao bai hat thất bại");
                            resetData();
                            Log.w("stu", "Error writing document", e);
                        }
                    });

        }catch (Exception e){
            showToast("tao bai hat thất bại");
            resetData();
            Log.w("stu", "Error Exception document", e);

        }

    }

    Map<String, Object> convertSongModelToMap(SongModel data){
        Gson gson = new Gson();
        String jsonString = gson.toJson(data);
        Log.d("stu", jsonString);
        Map<String, Object> accountMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        Log.d("stu2", accountMap.toString());
        return accountMap;
    }


}
