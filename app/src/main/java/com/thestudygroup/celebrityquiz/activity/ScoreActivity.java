package com.thestudygroup.celebrityquiz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.adapter.RankingAdapter;
import com.thestudygroup.celebrityquiz.common.PreferenceManager;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;
import com.thestudygroup.celebrityquiz.vo.UserRankVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<DocumentSnapshot>
{
    private static final String TAG = "ScoreActivity";

    private String       name;
    private int          score;
    private QuestionVO[] questions;

    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();
        db.collection("ranking").document("rank").get().addOnCompleteListener(this);
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

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                final Map<String, Object> rank  = document.getData();
                final long   score = PreferenceManager.getInt(this, "total_score", 0);
                final String name  = PreferenceManager.getString(this, "nick_name");

                rank.put(name, score);

                db.collection("ranking").document("rank")
                    .set(rank)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error writing document", e);
                        }
                    });
            } else {
                Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to load ranking list.", Toast.LENGTH_SHORT).show();
        }
    }
}
