package com.robotnec.reddit.core.web.deserializer;

import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.robotnec.reddit.core.mvp.model.TopFeed;
import com.robotnec.reddit.core.web.dto.FeedItemDto;

import java.lang.reflect.Type;

public class TopFeedDeserializer implements JsonDeserializer<TopFeed> {

    private final Gson gson;

    public TopFeedDeserializer() {
        gson = new GsonBuilder().create();
    }

    @Override
    public TopFeed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonObject()
                .get("data")
                .getAsJsonObject()
                .get("children")
                .getAsJsonArray();

        FeedItemData[] feedItems = gson.fromJson(jsonArray, FeedItemData[].class);

        return new TopFeed(Stream.of(feedItems)
                .map(item -> item.feedItem)
                .toList());
    }

    private static class FeedItemData {

        @SerializedName("data")
        FeedItemDto feedItem;
    }
}
