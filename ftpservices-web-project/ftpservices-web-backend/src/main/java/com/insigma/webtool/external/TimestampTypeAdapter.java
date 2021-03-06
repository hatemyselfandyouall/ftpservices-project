package com.insigma.webtool.external;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Title:
 *
 * @author wangjh
 * @created 2015年6月26日  下午5:02:39
 */
public class TimestampTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2)
    {
        String dateFormatAsString = format.format(new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        if (!(json instanceof JsonPrimitive))
        {
            throw new JsonParseException("The date should be a string value");
        }
        if (json.getAsJsonPrimitive().isNumber())
        {
            return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
        }
        try
        {
            Date date = format.parse(json.getAsString());
            return new Date(date.getTime());
        }
        catch (ParseException e)
        {
            throw new JsonParseException(e);
        }
    }
}
