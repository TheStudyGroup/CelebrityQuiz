package com.thestudygroup.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static com.thestudygroup.celebrityquiz.ExpandableListAdapter.CHILD;
import static com.thestudygroup.celebrityquiz.ExpandableListAdapter.HEADER;

public class CategoryActivity extends AppCompatActivity
{
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerview = findViewById(R.id.select_rec_quiz);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        data.add(new ExpandableListAdapter.Item(HEADER, "Actor"));
        data.add(new ExpandableListAdapter.Item(CHILD, "Talent"));
        data.add(new ExpandableListAdapter.Item(CHILD, "Comedian"));
        data.add(new ExpandableListAdapter.Item(CHILD, "Musical"));
        data.add(new ExpandableListAdapter.Item(CHILD, "Hollywood"));

        data.add(new ExpandableListAdapter.Item(HEADER, "Model"));
        data.add(new ExpandableListAdapter.Item(CHILD, "Fashion"));

        ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(HEADER, "Musician");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new ExpandableListAdapter.Item(CHILD, "K-POP"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(CHILD, "Vocalist"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(CHILD, "Composer"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(CHILD, "Lyricist"));
        data.add(places);

        recyclerview.setAdapter(new ExpandableListAdapter(data));
    }
}