package com.robotnec.reddit.core.web.deserializer;

import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.robotnec.reddit.core.web.dto.ImageDto;
import com.robotnec.reddit.core.web.dto.ImageThumbnailDto;

import java.lang.reflect.Type;

public class TopFeedDeserializer implements JsonDeserializer<TopFeedListing> {

    private final Gson gson;

    public TopFeedDeserializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ImageDto.class, new ImagePreviewDeserializer())
                .registerTypeAdapter(ImageThumbnailDto.class, new ImageThumbnailDeserializer())
                .create();
    }

    @Override
    public TopFeedListing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject data = json
                .getAsJsonObject()
                .get("data")
                .getAsJsonObject();

        JsonArray jsonArray = data
                .get("children")
                .getAsJsonArray();

        JsonElement afterElement = data
                .get("after");
        String after = afterElement.isJsonNull() ? null : afterElement.getAsString();

        FeedItemData[] feedItems = gson.fromJson(jsonArray, FeedItemData[].class);

        return new TopFeedListing(Stream.of(feedItems)
                .map(item -> item.feedItem)
                .toList(), after);
    }

    private static class FeedItemData {

        @SerializedName("data")
        FeedItemDto feedItem;
    }
}
