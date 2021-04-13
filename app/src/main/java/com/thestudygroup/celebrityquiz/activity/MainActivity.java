package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thestudygroup.celebrityquiz.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
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