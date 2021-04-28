package com.thestudygroup.celebrityquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thestudygroup.celebrityquiz.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "RegisterActivity";

    private EditText     editUserEmail;
    private EditText     editUserPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        editUserEmail    = findViewById(R.id.register_edit_email);
        editUserPassword = findViewById(R.id.register_edit_password);
        firebaseAuth     = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.register_btn_login) {
            signUp();
        }
    }

    private void signUp() {
        try {
            final String userEmail = editUserEmail.getText().toString().trim();
            final String userPassword = editUserPassword.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Enter correct email and password.", Toast.LENGTH_SHORT).show();
        }
    }
}
