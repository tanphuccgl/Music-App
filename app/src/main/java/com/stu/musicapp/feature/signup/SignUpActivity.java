package com.stu.musicapp.feature.signup;

import static android.app.PendingIntent.getActivity;

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


}
