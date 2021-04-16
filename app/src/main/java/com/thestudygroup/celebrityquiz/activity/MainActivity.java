package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.common.PreferenceManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        final String name = user.getEmail().split("@")[0];
        PreferenceManager.setString(this, "nick_name", name);
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.main_btn_start) {
            startActivity(new Intent(this, CategoryActivity.class));
        } else if (vid == R.id.main_btn_ranking) {
            startActivity(new Intent(this, RankingActivity.class));
        } else if (vid == R.id.main_btn_mypage) {
            startActivity(new Intent(this, MyPageActivity.class));
        } else if (vid == R.id.main_btn_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        }
    }
}