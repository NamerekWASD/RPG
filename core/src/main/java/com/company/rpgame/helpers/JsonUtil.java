package com.company.rpgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonUtil {
    public static JsonValue readFromFile(String resourcePath){
        JsonReader reader = new JsonReader();
        return reader.parse(Gdx.files.internal(resourcePath + ".json"));
    }
    public static JsonValue getProperty(String property, JsonValue parent){
        JsonValue jsonValue = parent.get(property);
        if(jsonValue != null){
            return jsonValue;
        }
        JsonValue.JsonIterator jsonIterator = parent.iterator();
        jsonIterator.forEach(iterableValue -> {
            if (iterableValue.child != null) {
                getProperty(property, iterableValue.child);
            }
        });
        throw new NullPointerException("Property does not exists.");
    }
    public static int getAsInt(String property, JsonValue parent){
        return getProperty(property, parent).asInt();
    }

    public static String[] readRules(Class<?> forClass, String property) {
        JsonValue value = readFromFile(forClass.getSimpleName());
        value = getProperty(property, value);
        return value.asStringArray();
    }
}
