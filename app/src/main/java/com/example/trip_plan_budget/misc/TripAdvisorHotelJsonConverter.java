package com.example.trip_plan_budget.misc;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TripAdvisorHotelJsonConverter<T> implements JsonDeserializationContext {
    @Override
    public T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get("data");
        return new Gson().fromJson(content, typeOfT);
    }
}
