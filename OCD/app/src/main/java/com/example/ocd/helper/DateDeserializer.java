package com.example.ocd.helper;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            // Parse the date string using SimpleDateFormat
            String dateString = json.getAsString();
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}
