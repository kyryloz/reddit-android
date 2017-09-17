package com.robotnec.reddit.core.web.deserializer;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.robotnec.reddit.core.web.dto.ImageThumbnailDto;

import java.lang.reflect.Type;

class ImageThumbnailDeserializer implements JsonDeserializer<ImageThumbnailDto> {

    @Override
    public ImageThumbnailDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement thumbnailElement = json.getAsJsonPrimitive();
        String url = thumbnailElement.isJsonNull() ? null : thumbnailElement.getAsString().trim();

        if (TextUtils.isEmpty(url) || url.equals("self")) {
            url = null;
        }

        return new ImageThumbnailDto(url);
    }
}
