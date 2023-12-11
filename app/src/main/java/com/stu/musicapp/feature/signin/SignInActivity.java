package com.stu.musicapp.feature.signin;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.stu.musicapp.R;
import com.stu.musicapp.feature.signup.SignUpActivity;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button buttonSignIn;
    TextView createAccountTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Khởi tạo định danh cho biến
        emailEditText = (EditText) findViewById(R.id.emailSignInEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordSignInEditText);
        buttonSignIn = (Button) findViewById(R.id.signInButton);

        createAccountTextView = (TextView) findViewById(R.id.createAccountTextView);


        createAccountTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("stu", "Onclick textview sign in");
                        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                login(email, password);
            }
        });
    }

// set - ghi đè => nếu mà chưa có phần tử  đó thì no sẽ tạo mới , nếu có thì sẽ ghi đè
    // get -> lấy phần  tử theo yêu cầu
    // delete -> xóa phần tử
    // add -> thêm mới
    // api -> REST full api ->

    // app của mình với database trên server sẽ làm việc với nhau thông qua kieu Map<key:value>

    // -> lấy danh sách data của collection account về -> so sánh với email và passowrd -> nếu mà có trên
    // database thì cho đăng nhập -> còn không thì trả lỗi

    void login(String emailValue, String passwordValue) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("accounts")
                .whereEqualTo("email", emailValue)
                .whereEqualTo("password", passwordValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (!task.isSuccessful()) {
                                Log.d("stu", "đăng nhập thất bại ", task.getException());
                                return;
                            }

                            List<DocumentSnapshot> listAccount = task.getResult().getDocuments();
                            if (listAccount.isEmpty()) {
                                Log.d("stu", "Không có phần tử nào được trả về ");
                                return;
                            }
                            for (DocumentSnapshot item : listAccount) {
                                Log.d("stu", "đăng nhập thành công: " + item.getData());
                            }

                        } catch (Exception e) {
                            Log.d("stu", "lỗi server ", e);
                        }

                    }
                });


    }
}
