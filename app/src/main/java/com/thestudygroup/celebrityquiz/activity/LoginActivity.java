package com.thestudygroup.celebrityquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.thestudygroup.celebrityquiz.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText     editUserEmail;
    private EditText     editUserPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        editUserEmail    = (EditText) findViewById(R.id.login_edit_email);
        editUserPassword = (EditText) findViewById(R.id.login_edit_password);
        firebaseAuth     = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.login_btn_login) {
            final String userEmail    = editUserEmail.getText().toString().trim();
            final String userPassword = editUserPassword.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if( TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword))
                        Toast.makeText(LoginActivity.this, "Enter email and password" ,Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                });
        } else if (vid == R.id.login_btn_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
