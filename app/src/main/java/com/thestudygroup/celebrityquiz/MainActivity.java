package com.thestudygroup.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn_start;
    private Button btn_record;
    private Button btn_mypage;
    private Button btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start   = (Button)findViewById(R.id.main_btn_start);
        btn_record  = (Button)findViewById(R.id.main_btn_record);
        btn_mypage  = (Button)findViewById(R.id.main_btn_mypage);
        btn_setting = (Button)findViewById(R.id.main_btn_setting);
    }

    @Override
    public void onClick(final View view)
    {
        final int vid = view.getId();

        if (vid == R.id.main_btn_start) {
            startActivity(new Intent(this, SelectActivity.class));
        } else if (vid == R.id.main_btn_record) {
            startActivity(new Intent(this, RecordActivity.class));
        } else if (vid == R.id.main_btn_mypage) {
            startActivity(new Intent(this, MyPageActivity.class));
        } else if (vid == R.id.main_btn_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        }
    }
}