package com.thestudygroup.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.thestudygroup.celebrityquiz.vo.QuizVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener
{
    private QuizVO[] quizList;
    private int seconds;
    private int indexCurrentQuestion;

    private ConstraintLayout layoutQuiz;
    private ConstraintLayout layoutLoading;

    private TextView questionView;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private RadioButton radioButtonThree;
    private RadioButton radioButtonFour;
    private Button buttonPrevious;
    private Button buttonNext;
    private Button buttonSubmit;
    private TextView textTime;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Hide toolbar
        Objects.requireNonNull(getSupportActionBar()).hide();

        layoutQuiz       = findViewById(R.id.quiz_layout);
        layoutLoading    = findViewById(R.id.quiz_loading);
        questionView     = findViewById(R.id.celebrityQuestion);
        imageView        = findViewById(R.id.celebrityImage);
        radioGroup       = findViewById(R.id.celebrityOption);
        radioButtonOne   = findViewById(R.id.radioButtonOne);
        radioButtonTwo   = findViewById(R.id.radioButtonTwo);
        radioButtonThree = findViewById(R.id.radioButtonThree);
        radioButtonFour  = findViewById(R.id.radioButtonFour);
        textTime         = findViewById(R.id.textTime);
        buttonNext       = findViewById(R.id.buttonNext);
        buttonPrevious   = findViewById(R.id.buttonPrevious);
        buttonSubmit     = findViewById(R.id.buttonSubmit);

        radioButtonOne.setOnClickListener(this);
        radioButtonTwo.setOnClickListener(this);
        radioButtonThree.setOnClickListener(this);
        radioButtonFour.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);


        Intent intent = getIntent();
        final String jsonUrl = intent.getStringExtra("url");

        seconds = intent.getIntExtra("seconds", 30);

        layoutQuiz.setVisibility(View.INVISIBLE);
        layoutLoading.setVisibility(View.VISIBLE);

        downloadFromUrl(jsonUrl);
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.radioButtonOne) {
            ((RadioButton) view).setChecked(true);
            quizList[indexCurrentQuestion].userAnswer = 1;
        } else if (vid == R.id.radioButtonTwo) {
            ((RadioButton) view).setChecked(true);
            quizList[indexCurrentQuestion].userAnswer = 2;
        } else if (vid == R.id.radioButtonThree) {
            ((RadioButton) view).setChecked(true);
            quizList[indexCurrentQuestion].userAnswer = 3;
        } else if (vid == R.id.radioButtonFour) {
            ((RadioButton) view).setChecked(true);
            quizList[indexCurrentQuestion].userAnswer = 4;
        } else if (vid == R.id.buttonSubmit) {
            submitResult();
        }
    }

    private void onDownloadResponse(final String data) {
        final Gson   gson = new Gson();
        quizList = gson.fromJson(data, QuizVO[].class);
        indexCurrentQuestion = 0;
        QuizVO currentQuestion = quizList[indexCurrentQuestion];
        currentQuestionView(currentQuestion);
        buttonPrevious.setEnabled(false);
        layoutQuiz.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        startTimer();
    }

    private void onDownloadFailure() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void downloadFromUrl(final String url) {
        final OkHttpClient client  = new OkHttpClient();
        final Request      request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { onDownloadFailure(); }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    final String data = Objects.requireNonNull(response.body()).string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { onDownloadResponse(data); }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { onDownloadFailure(); }
                    });
                }
            }
        });
    }




    public void onButtonPrevious(View view) {
        if(indexCurrentQuestion != 0) {
            indexCurrentQuestion--;
            if(indexCurrentQuestion == 0) buttonPrevious.setEnabled(false);
            if(indexCurrentQuestion != (quizList.length - 1)) buttonNext.setEnabled(true);
            QuizVO currentQuestion = quizList[indexCurrentQuestion];
            currentQuestionView(currentQuestion);

            radioGroup = findViewById(R.id.celebrityOption);
            if(currentQuestion.userAnswer == 0) radioGroup.clearCheck();
            else {
                switch (currentQuestion.userAnswer) {
                    case 1: {
                        radioGroup.check(R.id.radioButtonOne);
                        break;
                    }
                    case 2: {
                        radioGroup.check(R.id.radioButtonTwo);
                        break;
                    }
                    case 3: {
                        radioGroup.check(R.id.radioButtonThree);
                        break;
                    }
                    case 4: {
                        radioGroup.check(R.id.radioButtonFour);
                        break;
                    }
                }
            }
        }
    }

    public void onButtonNext(View view) {
        if(indexCurrentQuestion != (quizList.length - 1)) {
            indexCurrentQuestion++;
            if(indexCurrentQuestion == (quizList.length - 1)) buttonNext.setEnabled(false);
            if(indexCurrentQuestion != 0) buttonPrevious.setEnabled(true);
            QuizVO currentQuestion = quizList[indexCurrentQuestion];
            currentQuestionView(currentQuestion);

            radioGroup = findViewById(R.id.celebrityOption);
            if(currentQuestion.userAnswer == 0) radioGroup.clearCheck();
            else {
                switch (currentQuestion.userAnswer) {
                    case 1: {
                        radioGroup.check(R.id.radioButtonOne);
                        break;
                    }
                    case 2: {
                        radioGroup.check(R.id.radioButtonTwo);
                        break;
                    }
                    case 3: {
                        radioGroup.check(R.id.radioButtonThree);
                        break;
                    }
                    case 4: {
                        radioGroup.check(R.id.radioButtonFour);
                        break;
                    }
                }
            }
        }
    }

    public void currentQuestionView(QuizVO currentQuestion) {
        questionView.setText(String.format("%s. %s", indexCurrentQuestion + 1, currentQuestion.question));
        radioButtonOne.setText(currentQuestion.one);
        radioButtonTwo.setText(currentQuestion.two);
        radioButtonThree.setText(currentQuestion.three);
        radioButtonFour.setText(currentQuestion.four);
        Glide.with(imageView.getContext()).load(currentQuestion.imageUrl).into(imageView);
    }



    private void submitResult() {
        stopTimer();
        Intent i = new Intent(QuizActivity.this, SolutionActivity.class);
        i.putExtra("score", getScore());
        // Change List to ArrayList to accommodate subList
        ArrayList<QuizVO> list = new ArrayList<>(Arrays.asList(quizList));
        i.putExtra("quizList", list);
        i.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(i);
    }

    private int getScore() {
        int score = 0;
        for (final QuizVO quizVO : quizList) {
            if (quizVO.userAnswer == quizVO.correctAnswer)
                score++;
        }
        return score;
    }


    private void startTimer() {
        textTime.setText(String.valueOf(seconds));
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTime.setText(String.valueOf((int) (millisUntilFinished / 1000)));
            }
            @Override
            public void onFinish() {
                submitResult();
            }
        }.start();
    }

    private void stopTimer() {
        countDownTimer.cancel();
    }
}
