package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity  implements View.OnClickListener
{
    private String       name;
    private int          score;
    private QuestionVO[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final Intent intent = getIntent();
        name      = intent.getStringExtra("name");
        score     = intent.getIntExtra("score", 0);
        questions = (QuestionVO[]) intent.getSerializableExtra("question");
        ((TextView) findViewById(R.id.score_text_name)).setText(getString(R.string.score_quiz_name, name));
        ((TextView) findViewById(R.id.score_text_user)).setText(Integer.toString(score));
        ((TextView) findViewById(R.id.score_text_total)).setText(Integer.toString(questions.length));
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.score_btn_review) {
            final Intent intent = new Intent(ScoreActivity.this, SolutionActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("score", score);
            intent.putExtra("question", questions);
            startActivity(intent);
            finish();
        } else if (vid == R.id.score_btn_exit) {
            finish();
        }
    }
}