package com.robotnec.reddit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.dto.FeedItemDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<FeedItemDto> mItems;

    public FeedAdapter(Context context) {
        mItems = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setItems(List<FeedItemDto> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(mItems.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
