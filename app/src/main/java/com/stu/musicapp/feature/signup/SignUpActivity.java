package com.stu.musicapp.feature.signup;

import static android.app.PendingIntent.getActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stu.musicapp.MainActivity;
import com.stu.musicapp.R;
import com.stu.musicapp.feature.home.HomeActivity;
import com.stu.musicapp.feature.signin.SignInActivity;
import com.stu.musicapp.model.AccountModel;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText;
    Button sendCodeButton;
    Button backButton;
    Button loginButton;


        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acticity_sign_up);
            emailEditText = (EditText) findViewById(R.id.emailForgotPasswordEditText);
            sendCodeButton=  (Button) findViewById(R.id.sendCodeButton);
            backButton=  (Button) findViewById(R.id.backButton);
            loginButton=  (Button) findViewById(R.id.loginButton);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Intent

                }
            });

            // nhập email -> nhấn nút -> kiểm tra database -> xem có email được nhập trùng
            // với email trên db khoogn -> n
            // => nếu có thì hiển thị password
            // => nếu không thì show thông báo không có email trên hệ thong
            sendCodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("stu", emailEditText.getText().toString());
                    if(emailEditText.getText().toString().isEmpty()){
                        showToast("Vui lòng nhap email");
                        return;
                    }
                    // => call server

                   getPasswordFromDatabase(emailEditText.getText().toString());

                }
            });


        }

        // get , post, delete, put
        void getPasswordFromDatabase(String emailValue){
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("accounts")
                    .whereEqualTo("email", emailValue)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            try {
                                if (!task.isSuccessful()) {
                                    Log.d("stu", "is error ", task.getException());
                                    showToast("Lỗi app");
                                    return;
                                }

                                List<DocumentSnapshot> listAccount = task.getResult().getDocuments();
                                if (listAccount.isEmpty()) {
                                    Log.d("stu", "Không có phần tử nào được trả về ");
                                    showToast("Khong tim thay email");
                                    return;
                                }
                                for (DocumentSnapshot item : listAccount) {

                                    Map<String,Object> mapValue = item.getData();
                                    Gson gson = new Gson();
                                    String jsonString = new Gson().toJson(mapValue);
                                    AccountModel data =  gson.fromJson(jsonString, AccountModel.class);
                                    String passwordCurrent = data.getPassword();
                                    showToast("mat khau cua ban la: "+ passwordCurrent );
//
                                }

                            } catch (Exception e) {
                                Log.d("stu", "lỗi server ", e);
                                showToast("Lỗi app");
                            }

                        }
                    });
        }



    void showToast(String text){
        Toast.makeText(SignUpActivity.this, text,
                Toast.LENGTH_LONG).show();
    }



}
