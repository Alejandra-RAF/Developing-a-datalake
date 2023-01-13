package example;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }



}