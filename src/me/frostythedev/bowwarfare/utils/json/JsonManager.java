package me.frostythedev.bowwarfare.utils.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public class JsonManager {

    private Map<Class<?>, JsonSerializer<?>> serializers = new HashMap<>();
    private Map<Class<?>, JsonDeserializer<?>> deserializers = new HashMap<>();

   private static JsonManager instance;

   public static JsonManager getInstance() {
       if (instance == null) {
           instance = new JsonManager();
       }
       return instance;
   }

    public void register(Class<?> base, JsonAdaptor<?> js) {
        registerSerializer(base, js);
        registerDeSerializer(base, js);
    }

    public void registerSerializer(Class<?> base, JsonSerializer<?> js) {
        if (serializers.containsKey(base)) {
            return;
        }
        serializers.put(base, js);
    }

    public void registerDeSerializer(Class<?> base, JsonDeserializer<?> js) {
        if (deserializers.containsKey(base)) {
            return;
        }
        deserializers.put(base, js);
    }

    public String serialize(Object object) {
        GsonBuilder builder = new GsonBuilder();
        for (Map.Entry<Class<?>, JsonSerializer<?>> entrys : serializers.entrySet()) {
            builder.registerTypeHierarchyAdapter(entrys.getKey(), entrys.getValue());
        }
        builder.setPrettyPrinting();
        builder.serializeNulls();
        return builder.create().toJson(object);
    }

    public Object deserialize(String data, Class<?> of) {
        GsonBuilder builder = new GsonBuilder();
        for (Map.Entry<Class<?>, JsonDeserializer<?>> entrys : deserializers.entrySet()) {
            builder.registerTypeHierarchyAdapter(entrys.getKey(), entrys.getValue());
        }
        return builder.create().fromJson(data, of);
    }
}
