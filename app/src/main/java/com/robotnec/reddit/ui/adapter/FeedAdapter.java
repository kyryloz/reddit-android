package com.robotnec.reddit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.dto.FeedItemDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<FeedItemDto> items;
    private final Picasso picasso;

    public FeedAdapter(Context context) {
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
    }

    public void setItems(List<FeedItemDto> items) {
        items.clear();
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItemDto feedItem = items.get(position);
        holder.text.setText(feedItem.getTitle());

        String thumbnail = feedItem.getThumbnail();

        holder.thumbnail.setVisibility(TextUtils.isEmpty(thumbnail) ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(thumbnail)) {
            picasso.load(thumbnail)
                    .centerCrop()
                    .fit()
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.text)
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
