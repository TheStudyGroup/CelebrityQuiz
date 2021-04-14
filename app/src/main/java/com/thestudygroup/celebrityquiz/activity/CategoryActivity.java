package com.thestudygroup.celebrityquiz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thestudygroup.celebrityquiz.adapter.ExpandableListAdapter;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.download.DownloadListener;
import com.thestudygroup.celebrityquiz.download.DownloadTask;
import com.thestudygroup.celebrityquiz.vo.MenuChildVO;
import com.thestudygroup.celebrityquiz.vo.MenuHeaderVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.thestudygroup.celebrityquiz.adapter.ExpandableListAdapter.CHILD;
import static com.thestudygroup.celebrityquiz.adapter.ExpandableListAdapter.HEADER;

public class CategoryActivity extends AppCompatActivity implements ExpandableListAdapter.OnItemClickListener, DownloadListener
{
    private ConstraintLayout                     layoutLoading;
    private RecyclerView                         recyclerView;
    private ExpandableListAdapter                adapter;
    private List<ExpandableListAdapter.ListItem> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Objects.requireNonNull(getSupportActionBar()).hide();

        layoutLoading = findViewById(R.id.cat_loading);
        recyclerView  = findViewById(R.id.cat_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuList = new ArrayList<>();
        adapter  = new ExpandableListAdapter(menuList, this, null);
        recyclerView.setAdapter(adapter);

        DownloadTask.start(this, "https://pastebin.com/raw/a1VzBh7V");
    }

    @Override
    public void onItemClick(final View view, final ExpandableListAdapter.ListItem listItem, final int position) {
        final String quizName = listItem.getText();
        final String quizUrl  = listItem.getHiddenText();
        if (quizUrl == null) {
            Toast.makeText(this, "준비중인 퀴즈입니다", Toast.LENGTH_SHORT).show();
        } else {
            final Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("name", quizName);
            intent.putExtra("url", quizUrl);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDownloadResponse(final String data) {
        final Gson           gson  = new Gson();
        final MenuHeaderVO[] menus = gson.fromJson(data, MenuHeaderVO[].class);
        menuList.clear();
        for (final MenuHeaderVO header : menus) {
            final ExpandableListAdapter.ListItem list = new ExpandableListAdapter.ListItem(HEADER, header.getName());
            list.invisibleChildren = new ArrayList<>();
            for (final MenuChildVO child : header.getList()) {
                list.invisibleChildren.add(new ExpandableListAdapter.ListItem(CHILD, child.getName(), child.getUrl()));
            }
            menuList.add(list);
        }
        layoutLoading.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDownloadFailure() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
