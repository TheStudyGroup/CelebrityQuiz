package com.thestudygroup.celebrityquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private final List<Item> data;

    public ExpandableListAdapter(final List<Item> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case HEADER:
                final View viewHeader = inflater.inflate(R.layout.layout_list_header, parent, false);
                return new ListHeaderViewHolder(viewHeader);
            case CHILD:
                final View viewChild = inflater.inflate(R.layout.layout_list_child, parent, false);
                return new ListChildViewHolder(viewChild);
        }
        return null;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.textTitle.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
                } else {
                    itemController.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
                }
//                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                itemController.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder itemController1 = (ListChildViewHolder) holder;
                itemController1.refferalItem = item;
                itemController1.textTitle.setText(item.text);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView         textTitle;
        public ImageView        btnExpand;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            layout    = itemView.findViewById(R.id.list_header_root);
            textTitle = itemView.findViewById(R.id.list_header_title);
            btnExpand = itemView.findViewById(R.id.list_header_expand);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView    textTitle;
        public ImageButton btnStart;
        public Item refferalItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.list_child_title);
            btnStart  = itemView.findViewById(R.id.list_child_btn);
        }
    }

    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}