package com.robotnec.reddit.core.web.deserializer;

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
        if (url != null) {
            switch (url) {
                case "self":
                    url = "https://www.reddit.com/static/self_default2.png";
                    break;
                case "default":
                case "image":
                    url = "https://www.reddit.com/static/noimage.png";
                    break;
                case "nsfw":
                    url = "https://www.reddit.com/static/nsfw2.png";
                    break;
            }
        }
        return new ImageThumbnailDto(url);
    }
}
