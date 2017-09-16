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

import com.annimon.stream.Stream;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS_BAR = 1;

    private final LayoutInflater inflater;
    private final List<AdapterItem> items;
    private final Picasso picasso;
    private final Context context;
    private final OnItemClickListener clickListener;
    private final AdapterItem progressBarItem;

    public TopFeedAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        clickListener = listener;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        progressBarItem = new AdapterItem(null, TYPE_PROGRESS_BAR);
    }

    public void addItems(List<FeedItemDto> nextItems) {
        items.addAll(Stream.of(nextItems)
                .map(feedItem -> new AdapterItem(feedItem, TYPE_ITEM))
                .toList());
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        if (loading) {
            items.add(progressBarItem);
            notifyItemInserted(items.size());
        } else {
            items.remove(progressBarItem);
            notifyItemRemoved(items.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.item_feed, parent, false));
            case TYPE_PROGRESS_BAR:
                return new ProgressBarViewHolder(inflater.inflate(R.layout.item_progress_bar, parent, false));
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterItem adapterItem = items.get(position);
        if (adapterItem.type == TYPE_PROGRESS_BAR) {
            return;
        }

        FeedItemDto feedItem = adapterItem.feedItem;
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.title.setText(feedItem.getTitle());
        itemViewHolder.caption.setText(createCaptionString(feedItem));
        itemViewHolder.numberOfComments.setText(createNumberOfCommentsString(feedItem));

        String thumbnail = feedItem.getImageThumbnail();
        itemViewHolder.thumbnail.setVisibility(TextUtils.isEmpty(thumbnail) ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(thumbnail)) {
            picasso.load(thumbnail)
                    .centerCrop()
                    .fit()
                    .into(itemViewHolder.thumbnail);
        }

        itemViewHolder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onClick(feedItem);
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

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.numberOfComments)
        TextView numberOfComments;

        @BindView(R.id.caption)
        TextView caption;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        ProgressBarViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(FeedItemDto feedItem);
    }

    private static class AdapterItem {
        final FeedItemDto feedItem;
        int type;

        AdapterItem(FeedItemDto item, int type) {
            this.feedItem = item;
            this.type = type;
        }
    }
}
