package com.thestudygroup.celebrityquiz.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.common.PreferenceManager;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener
{
    private RadioButton radioButtonLevelOne;
    private RadioButton radioButtonLevelTwo;
    private RadioButton radioButtonLevelThree;
    private RadioButton radioButton30;
    private RadioButton radioButton60;
    private RadioButton radioButton90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Objects.requireNonNull(getSupportActionBar()).hide();

        radioButtonLevelOne = findViewById(R.id.radioButtonLevelOne);
        radioButtonLevelTwo = findViewById(R.id.radioButtonLevelTwo);
        radioButtonLevelThree = findViewById(R.id.radioButtonLevelThree);
        radioButton30 = findViewById(R.id.radioButton30);
        radioButton60 = findViewById(R.id.radioButton60);
        radioButton90 = findViewById(R.id.radioButton90);

        switch (PreferenceManager.getInt(this, "second", 90)) {
            case 30:
                radioButton30.setChecked(true);
                break;
            case 60:
                radioButton60.setChecked(true);
                break;
            case 90:
                radioButton90.setChecked(true);
                break;
        }

        switch (PreferenceManager.getInt(this, "level", 2)) {
            case 1:
                radioButtonLevelOne.setChecked(true);
                break;
            case 2:
                radioButtonLevelTwo.setChecked(true);
                break;
            case 3:
                radioButtonLevelThree.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(final View view) {
        final int vid = view.getId();
        if (vid == R.id.radioButton30) {
            PreferenceManager.setInt(this, "second", 30);
        } else if (vid == R.id.radioButton60) {
            PreferenceManager.setInt(this, "second", 60);
        } else if (vid == R.id.radioButton90) {
            PreferenceManager.setInt(this, "second", 90);
        } else if (vid == R.id.radioButtonLevelOne) {
            PreferenceManager.setInt(this, "level", 1);
        } else if (vid == R.id.radioButtonLevelTwo) {
            PreferenceManager.setInt(this, "level", 2);
        } else if (vid == R.id.radioButtonLevelThree) {
            PreferenceManager.setInt(this, "level", 3);
        }
    }
}
