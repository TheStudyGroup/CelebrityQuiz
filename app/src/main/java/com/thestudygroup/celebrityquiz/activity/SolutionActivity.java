package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.adapter.SolutionAdapter;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;

import java.util.List;
import java.util.Objects;

public class SolutionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Results");
        }

        int scoreValue = getIntent().getIntExtra("score", 0);
        List<QuestionVO> quizList = (List<QuestionVO>) getIntent().getSerializableExtra("quizList");

        TextView scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(scoreValue));

        TextView scoreTotalView = findViewById(R.id.scoreTotalTextView);
        scoreTotalView.setText(String.valueOf(quizList.size()));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SolutionAdapter solutionAdapter = new SolutionAdapter(quizList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(solutionAdapter);
    }
}
