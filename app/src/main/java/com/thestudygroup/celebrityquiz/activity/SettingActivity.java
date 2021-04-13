package com.thestudygroup.celebrityquiz.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.thestudygroup.celebrityquiz.R;

public class SettingActivity extends AppCompatActivity {

    // Declare Variables
    private RadioButton radioButtonLevelOne;
    private RadioButton radioButtonLevelTwo;
    private RadioButton radioButtonLevelThree;
    private RadioButton radioButton30;
    private RadioButton radioButton60;
    private RadioButton radioButton90;

    public int level;
    public int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Define Level views
        radioButtonLevelOne = findViewById(R.id.radioButtonLevelOne);
        radioButtonLevelTwo = findViewById(R.id.radioButtonLevelTwo);
        radioButtonLevelThree = findViewById(R.id.radioButtonLevelThree);
        radioButtonLevelOne.setChecked(true);
        radioButtonLevelTwo.setChecked(false);
        radioButtonLevelThree.setChecked(false);

        // Define Time views
        radioButton30 = findViewById(R.id.radioButton30);
        radioButton60 = findViewById(R.id.radioButton60);
        radioButton90 = findViewById(R.id.radioButton90);
        radioButton30.setChecked(true);
        radioButton60.setChecked(false);
        radioButton90.setChecked(false);

    }


    public void onButtonStartQuiz(View view) {
        if(radioButtonLevelOne.isChecked()) level = 1;
        if(radioButtonLevelTwo.isChecked()) level = 2;
        if(radioButtonLevelThree.isChecked()) level = 3;

        if(radioButton30.isChecked()) seconds = 30;
        if(radioButton60.isChecked()) seconds = 60;
        if(radioButton90.isChecked()) seconds = 90;
    }
}