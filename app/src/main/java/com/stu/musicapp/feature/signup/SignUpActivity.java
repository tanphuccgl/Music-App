package com.stu.musicapp.feature.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stu.musicapp.MainActivity;
import com.stu.musicapp.R;
import com.stu.musicapp.feature.signin.SignInActivity;

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
