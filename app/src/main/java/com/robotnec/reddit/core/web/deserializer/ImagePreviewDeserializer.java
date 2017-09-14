package com.robotnec.reddit.core.web.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.robotnec.reddit.core.web.dto.ImageDto;

import java.lang.reflect.Type;

class ImagePreviewDeserializer implements JsonDeserializer<ImageDto> {

    @Override
    public ImageDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ImageDto(json.getAsJsonObject()
                .get("images")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("source")
                .getAsJsonObject()
                .get("url")
                .getAsString());
    }
}
