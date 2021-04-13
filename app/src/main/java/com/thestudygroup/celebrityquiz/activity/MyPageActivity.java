package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thestudygroup.celebrityquiz.R;

import java.util.Objects;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.mypage_btn_ranking) {
            startActivity(new Intent(this, RankingActivity.class));
        } else if (vid == R.id.mypage_btn_record) {
            startActivity(new Intent(this, RecordActivity.class));
        } else if (vid == R.id.mypage_btn_logout) {
           //TODO
        }
    }
}