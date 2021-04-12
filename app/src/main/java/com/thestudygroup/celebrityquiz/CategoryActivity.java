package com.thestudygroup.celebrityquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thestudygroup.celebrityquiz.vo.MenuChildVO;
import com.thestudygroup.celebrityquiz.vo.MenuHeaderVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.thestudygroup.celebrityquiz.ExpandableListAdapter.CHILD;
import static com.thestudygroup.celebrityquiz.ExpandableListAdapter.HEADER;

public class CategoryActivity extends AppCompatActivity implements ExpandableListAdapter.OnItemClickListener
{
    private ConstraintLayout                     layoutLoading;
    private RecyclerView                         recyclerView;
    private ExpandableListAdapter                adapter;
    private List<ExpandableListAdapter.ListItem> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        layoutLoading = findViewById(R.id.select_loading);
        recyclerView  = findViewById(R.id.select_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuList = new ArrayList<>();
        adapter  = new ExpandableListAdapter(menuList, this, null);
        recyclerView.setAdapter(adapter);

        final String jsonUrl = "https://pastebin.com/raw/a1VzBh7V";
        downloadFromUrl(jsonUrl);
    }

    @Override
    public void onItemClick(final View view, final ExpandableListAdapter.ListItem listItem, final int position) {
        final String quizUrl = listItem.getHiddenText();
        if (quizUrl == null) {
            Toast.makeText(this, "준비중인 퀴즈입니다", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("url", quizUrl);
            intent.putExtra("seconds", 90);
            startActivity(intent);
            finish();
        }
    }


    private void onDownloadResponse(final String data) {
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
}
