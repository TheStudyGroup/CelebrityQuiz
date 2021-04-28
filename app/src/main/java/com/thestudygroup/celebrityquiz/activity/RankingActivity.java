package com.thestudygroup.celebrityquiz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.adapter.RankingAdapter;
import com.thestudygroup.celebrityquiz.adapter.SolutionAdapter;
import com.thestudygroup.celebrityquiz.vo.UserRankVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class RankingActivity extends AppCompatActivity implements OnCompleteListener<DocumentSnapshot>
{
    private static final String TAG = "RankingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("ranking").document("rank");
        docRef.get().addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                final Map<String, Object> rank  = document.getData();
                final ArrayList<UserRankVO> users = new ArrayList<>();
                for (final String userName : rank.keySet()) {
                    users.add(new UserRankVO(userName, (Long) rank.get(userName)));
                }
                Collections.sort(users);

                final ConstraintLayout layoutLoading = findViewById(R.id.ranking_loading);
                final RecyclerView     recyclerUser  = findViewById(R.id.ranking_recyclerview);
                final RankingAdapter   adapter        = new RankingAdapter(users);
                layoutLoading.setVisibility(View.GONE);
                recyclerUser.setLayoutManager(new LinearLayoutManager(this));
                recyclerUser.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Failed to load ranking list.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, task.getException().toString());
            finish();
        }
    }
}
