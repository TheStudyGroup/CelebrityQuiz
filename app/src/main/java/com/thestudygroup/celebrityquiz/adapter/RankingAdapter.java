package com.thestudygroup.celebrityquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.vo.UserRankVO;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final ArrayList<UserRankVO> users;

    public RankingAdapter(@NonNull final ArrayList<UserRankVO> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int viewType) {
        final LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.item_ranking, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (users.isEmpty()) {
            return;
        }

        final TextView textRankIndex = viewHolder.itemView.findViewById(R.id.item_rank_text_index);
        final TextView textRankName  = viewHolder.itemView.findViewById(R.id.item_rank_text_name);
        final TextView textRankScore = viewHolder.itemView.findViewById(R.id.item_rank_text_score);

        textRankIndex.setText(Integer.toString(position + 1));
        textRankName.setText(users.get(position).name);
        textRankScore.setText(Long.toString(users.get(position).score));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
