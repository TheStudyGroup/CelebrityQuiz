package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.adapter.SolutionAdapter;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;

import java.util.Arrays;
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

        final int          score    = getIntent().getIntExtra("score", 0);
        final QuestionVO[] question = (QuestionVO[]) (getIntent().getSerializableExtra("question"));

        final TextView        textUserScore   = findViewById(R.id.scoreTextView);
        final TextView        textTotalScore  = findViewById(R.id.scoreTotalTextView);
        final RecyclerView    recyclerView    = findViewById(R.id.ranking_recyclerview);
        final SolutionAdapter solutionAdapter = new SolutionAdapter(Arrays.asList(question));

        textUserScore.setText(String.valueOf(score));
        textTotalScore.setText(String.valueOf(question.length));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(solutionAdapter);
    }
}
