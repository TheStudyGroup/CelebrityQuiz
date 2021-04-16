package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.thestudygroup.celebrityquiz.R;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    private Button register;
    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = (Button) findViewById(R.id.login_btn_register);
        login = (Button) findViewById(R.id.login_btn_login);
        email_login = (EditText) findViewById(R.id.login_ID);
        pwd_login = (EditText) findViewById(R.id.login_PW);
        String txt_email = email_login.getText().toString();
        String txt_pwd = pwd_login.getText().toString();
        firebaseAuth = firebaseAuth.getInstance();//firebaseAuth의 인스턴스를 가져옴

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String pwd = pwd_login.getText().toString().trim();
                //String형 변수 email.pwd(edittext에서 받오는 값)으로 로그인하는것
                firebaseAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if( TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pwd))//공백일때
                                    Toast.makeText(LoginActivity.this, "fill txt" ,Toast.LENGTH_SHORT).show();
                                if (task.isSuccessful()) {//성공했을때
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {//실패했을때
                                    Toast.makeText(LoginActivity.this, "없는아이디입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}
