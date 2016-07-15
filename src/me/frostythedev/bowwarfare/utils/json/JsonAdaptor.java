package me.frostythedev.bowwarfare.utils.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public interface JsonAdaptor<T> extends JsonSerializer<T>, JsonDeserializer<T> {
}
