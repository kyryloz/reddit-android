package com.robotnec.reddit.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopFeedAdapter extends RecyclerView.Adapter<TopFeedAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<FeedItemDto> items;
    private final Picasso picasso;
    private final Context context;
    private final OnImageThumbnailClickListener thumbnailClickListener;

    public TopFeedAdapter(Context context, OnImageThumbnailClickListener listener) {
        this.context = context;
        thumbnailClickListener = listener;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
    }

    public void setItems(List<FeedItemDto> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void addItems(List<FeedItemDto> nextItems) {
        items.addAll(nextItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItemDto feedItem = items.get(position);

        holder.title.setText(feedItem.getTitle());
        holder.caption.setText(createCaptionString(feedItem));
        holder.numberOfComments.setText(createNumberOfCommentsString(feedItem));

        String thumbnail = feedItem.getImageThumbnail();
        holder.thumbnail.setVisibility(TextUtils.isEmpty(thumbnail) ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(thumbnail)) {
            picasso.load(thumbnail)
                    .centerCrop()
                    .fit()
                    .into(holder.thumbnail);
        }

        holder.thumbnail.setOnClickListener(view -> {
            if (thumbnailClickListener != null) {
                thumbnailClickListener.onClick(feedItem.getImageFull());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    private String createCaptionString(FeedItemDto feedItem) {
        String relativeDate = DateUtils.getRelativeTimeSpanString(
                feedItem.getCreatedTimestamp() * 1000,
                new Date().getTime(),
                DateUtils.MINUTE_IN_MILLIS).toString();
        return context.getString(R.string.caption_format,
                relativeDate, feedItem.getAuthor());
    }

    @NonNull
    private String createNumberOfCommentsString(FeedItemDto feedItem) {
        int numberOfComments = feedItem.getNumberOfComments();
        return context.getResources().getQuantityString(
                R.plurals.comments_format,
                numberOfComments,
                numberOfComments);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.numberOfComments)
        TextView numberOfComments;

        @BindView(R.id.caption)
        TextView caption;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnImageThumbnailClickListener {
        void onClick(String fullImageUrl);
    }
}
