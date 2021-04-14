package com.thestudygroup.celebrityquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.common.PreferenceManager;
import com.thestudygroup.celebrityquiz.download.DownloadListener;
import com.thestudygroup.celebrityquiz.download.DownloadTask;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener, DownloadListener
{
    private String       quizName;
    private QuestionVO[] questions;
    private int seconds;
    private int currentQuizIndex;

    private ConstraintLayout layoutQuiz;
    private ConstraintLayout layoutLoading;

    private TextView questionView;
    private ImageView imageView;

    private RadioGroup  radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private EditText    textInput;

    private Button buttonPrevious;
    private Button buttonNext;
    private Button buttonSubmit;
    private TextView textTime;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Objects.requireNonNull(getSupportActionBar()).hide();

        layoutQuiz     = findViewById(R.id.quiz_layout);
        layoutLoading  = findViewById(R.id.quiz_loading);
        questionView   = findViewById(R.id.quiz_content);
        imageView      = findViewById(R.id.quiz_image);
        radioGroup     = findViewById(R.id.quiz_radio_group);
        radioButton1   = findViewById(R.id.quiz_radio1);
        radioButton2   = findViewById(R.id.quiz_radio2);
        radioButton3   = findViewById(R.id.quiz_radio3);
        radioButton4   = findViewById(R.id.quiz_radio4);
        textInput      = findViewById(R.id.quiz_input);
        textTime       = findViewById(R.id.quiz_score);
        buttonNext     = findViewById(R.id.quiz_btn_next);
        buttonPrevious = findViewById(R.id.quiz_btn_previous);
        buttonSubmit   = findViewById(R.id.quiz_btn_submit);
        radioGroup.setOnCheckedChangeListener(null);

        seconds = PreferenceManager.getInt(this, "second", 90);
        layoutQuiz.setVisibility(View.INVISIBLE);
        layoutLoading.setVisibility(View.VISIBLE);

        final Intent intent = getIntent();
        quizName = intent.getStringExtra("name");
        DownloadTask.start(this, intent.getStringExtra("url"));
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.quiz_btn_previous) {
            setUserAnswer();
            currentQuizIndex--;
            displayQuiz();
        } else if (vid == R.id.quiz_btn_next) {
            setUserAnswer();
            currentQuizIndex++;
            displayQuiz();
        } else if (vid == R.id.quiz_btn_submit) {
            submitResult();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onDownloadResponse(final String data) {
        final Gson gson = new Gson();
        questions = gson.fromJson(data, QuestionVO[].class);

        currentQuizIndex = 0;
        displayQuiz();

        layoutQuiz.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        startTimer();
    }

    @Override
    public void onDownloadFailure() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show();
        finish();
    }



    private void displayQuiz() {
        final QuestionVO quiz = questions[currentQuizIndex];
        questionView.setText(String.format("%s. %s", currentQuizIndex + 1, quiz.question));
        Glide.with(imageView.getContext()).load(quiz.imageUrl).into(imageView);

        if (quiz.correctAnswer != 0) {
            radioGroup.setVisibility(View.VISIBLE);
            textInput.setVisibility(View.INVISIBLE);
            radioButton1.setText(quiz.one);
            radioButton2.setText(quiz.two);
            radioButton3.setText(quiz.three);
            radioButton4.setText(quiz.four);
            if (quiz.userAnswer == 0) {
                radioGroup.clearCheck();
            } else if (quiz.userAnswer == 1) {
                radioGroup.check(R.id.quiz_radio1);
            } else if (quiz.userAnswer == 2) {
                radioGroup.check(R.id.quiz_radio2);
            } else if (quiz.userAnswer == 3) {
                radioGroup.check(R.id.quiz_radio3);
            } else if (quiz.userAnswer == 4) {
                radioGroup.check(R.id.quiz_radio4);
            }
        } else {
            radioGroup.setVisibility(View.INVISIBLE);
            textInput.setVisibility(View.VISIBLE);
            textInput.setText(quiz.two);
        }

        buttonPrevious.setEnabled(currentQuizIndex != 0);
        buttonNext.setEnabled(currentQuizIndex != (questions.length - 1));
    }

    private void setUserAnswer() {
        final QuestionVO quiz = questions[currentQuizIndex];
        if (quiz.correctAnswer != 0) {
            if (radioButton1.isChecked()) quiz.userAnswer = 1;
            if (radioButton2.isChecked()) quiz.userAnswer = 2;
            if (radioButton3.isChecked()) quiz.userAnswer = 3;
            if (radioButton4.isChecked()) quiz.userAnswer = 4;
        } else {
            quiz.two = textInput.getText().toString();
        }
    }

    private int getScore() {
        int score = 0;
        for (final QuestionVO quiz : questions) {
            if (quiz.correctAnswer != 0) {
                if (quiz.userAnswer == quiz.correctAnswer) {
                    score++;
                }
            } else {
                if (Objects.equals(quiz.one.toLowerCase(), quiz.two.toLowerCase())) {
                    score++;
                }
            }
        }
        return score;
    }

    private void submitResult() {
        setUserAnswer();
        stopTimer();

        final int bestScore    = PreferenceManager.getInt(this, "best_score",    0);
        final int totalScore   = PreferenceManager.getInt(this, "total_score",   0) + getScore();
        final int totalSolved  = PreferenceManager.getInt(this, "total_solved",  0) + 1;
        PreferenceManager.setInt(this, "best_score",   Math.max(bestScore, getScore()));
        PreferenceManager.setInt(this, "total_score",  totalScore);
        PreferenceManager.setInt(this, "total_solved", totalSolved);

        final ArrayList<QuestionVO> list = new ArrayList<>(Arrays.asList(questions));
        final Intent intent = new Intent(QuizActivity.this, SolutionActivity.class);
        intent.putExtra("name", quizName);
        intent.putExtra("score", getScore());
        intent.putExtra("quizList", list);
        intent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(intent);
        finish();
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
