package com.thestudygroup.celebrityquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thestudygroup.celebrityquiz.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    public interface OnItemClickListener {
        void onItemClick(final View view, final ListItem listItem, final int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(final View view, final ListItem listItem, final int position);
    }

    public static final int HEADER = 0;
    public static final int CHILD  = 1;

    private final List<ListItem>          data;
    private final OnItemClickListener     listener;
    private final OnItemLongClickListener listenerLong;

    public ExpandableListAdapter(
        @NonNull  final List<ListItem>              data,
        @Nullable final OnItemClickListener     listener,
        @Nullable final OnItemLongClickListener listenerLong)
    {
        this.data         = data;
        this.listener     = listener;
        this.listenerLong = listenerLong;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
        final LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case HEADER:
                final View viewHeader = inflater.inflate(R.layout.layout_list_header, viewGroup, false);
                return new ListHeaderViewHolder(viewHeader);
            case CHILD:
                final View viewChild = inflater.inflate(R.layout.layout_list_child, viewGroup, false);
                return new ListChildViewHolder(viewChild);
        }
        return null;
    }

    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final ListItem listItem = data.get(position);

        switch (listItem.type) {
            case HEADER:
                final ListHeaderViewHolder headerHolder = (ListHeaderViewHolder) holder;
                headerHolder.listItem = listItem;
                headerHolder.textView.setText(listItem.text);
                if (listItem.invisibleChildren == null) {
                    headerHolder.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
                } else {
                    headerHolder.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
                }

//                headerViewHolder.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                headerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (listItem.invisibleChildren == null) {
                            listItem.invisibleChildren = new ArrayList<ListItem>();
                            int count = 0;
                            int pos = data.indexOf(headerHolder.listItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                listItem.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            headerHolder.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
                        } else {
                            int pos = data.indexOf(headerHolder.listItem);
                            int index = pos + 1;
                            for (ListItem i : listItem.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            headerHolder.btnExpand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
                            listItem.invisibleChildren = null;
                        }
                    }
                });
                break;

            case CHILD:
                final ListChildViewHolder childHolder = (ListChildViewHolder) holder;
                childHolder.listItem = listItem;
                childHolder.textView.setText(listItem.text);
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





    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ListItem listItem;
        public View     itemView;
        public TextView textView;

        public ListViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (listener != null) {
                listener.onItemClick(view, listItem, getBindingAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(final View view) {
            if (listenerLong != null) {
                listenerLong.onItemLongClick(view, listItem, getBindingAdapterPosition());
            }
            return false;
        }
    }

    private class ListHeaderViewHolder extends ListViewHolder {
        public ImageView btnExpand;

        public ListHeaderViewHolder(final View itemView) {
            super(itemView);
            textView  = itemView.findViewById(R.id.list_header_title);
            btnExpand = itemView.findViewById(R.id.list_header_expand);
        }
    }

    private class ListChildViewHolder extends ListViewHolder {
        public ImageButton btnStart;

        public ListChildViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_child_title);
            btnStart = itemView.findViewById(R.id.list_child_btn);
        }
    }




    public static class ListItem {
        private final int     type;
        private String        text;
        private String        hiddenText;
        public List<ListItem> invisibleChildren;

        public ListItem(final int type, final String text) {
            this.type = type;
            this.text = text;
        }
        public ListItem(final int type, final String text, final String hiddenText) {
            this(type, text);
            this.hiddenText = hiddenText;
        }

        public int    getType()       { return type;       }
        public String getText()       { return text;       }
        public String getHiddenText() { return hiddenText; }
        public void   setText      (final String text)       { this.text = text;             }
        public void   setHiddenText(final String hiddenText) { this.hiddenText = hiddenText; }

        public boolean isHeader() { return type == HEADER; }
        public boolean isChild()  { return type == CHILD;  }

        @NonNull
        @Override
        public String toString() {
            return "ListItem[type=" + (type == HEADER ? "HEADER" : "CHILD") + ", text=" + text + ", hidden=" + hiddenText + "]";
        }
    }
}


