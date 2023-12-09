package com.stu.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stu.musicapp.model.AccountModel;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button buttonSignUp;

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

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createAccount(name,email,password);
            }
        });
    }



    void createAccount(String name,String email,String password)
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> userModel = new HashMap<>();
        userModel.put("id", "");
        userModel.put("username", "" );
        userModel.put("password", password);
        userModel.put("displayname", name);
        userModel.put("numberPhone", "");
        userModel.put("email", email);
        userModel.put("linkImage", "");


        database.collection("accounts").document("111").set(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("stu", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("stu", "Error writing document", e);
                    }
                });
    }
}