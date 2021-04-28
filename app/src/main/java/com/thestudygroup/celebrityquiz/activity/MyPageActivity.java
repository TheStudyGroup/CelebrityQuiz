package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.common.PreferenceManager;

import java.util.Objects;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final String nickName     = PreferenceManager.getString(this, "nick_name",     "none");
        final int    bestScore    = PreferenceManager.getInt   (this, "best_score",    0);
        final int    totalScore   = PreferenceManager.getInt   (this, "total_score",   0);
        final int    totalSolved  = PreferenceManager.getInt   (this, "total_solved",  0);
        final float  averageScore = (totalSolved == 0 ? 0 : (float)totalScore / totalSolved);
        ((TextView) findViewById(R.id.mypage_nick))   .setText(getString(R.string.mypage_nick_name,     nickName));
        ((TextView) findViewById(R.id.mypage_best))   .setText(getString(R.string.mypage_best_score,    bestScore));
        ((TextView) findViewById(R.id.mypage_average)).setText(getString(R.string.mypage_average_score, averageScore));
        ((TextView) findViewById(R.id.mypage_total))  .setText(getString(R.string.mypage_total_solved,  totalSolved));
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.mypage_btn_ranking) {
            startActivity(new Intent(this, RankingActivity.class));
        } else if (vid == R.id.mypage_btn_record) {
            startActivity(new Intent(this, RecordActivity.class));
        } else if (vid == R.id.mypage_btn_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
