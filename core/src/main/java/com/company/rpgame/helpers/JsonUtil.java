package com.company.rpgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.company.rpgame.helpers.Box2D.components.Size;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class JsonUtil {
    public static final String JSON = ".json";
    public enum DataType{
        Data,
        Rule
    }
    private static final String TEXTURE_SIZE = "textureSize";
    public static Size readTextureSize(String resourceName){
        JsonValue data = fetchData(resourceName, DataType.Data, TEXTURE_SIZE);

        int width = getAsInt("width", data);
        int height = getAsInt("height", data);
        return new Size(width, height);
    }

    public static Array<String> readRules(String rulesFileName, String propertyName) {
        JsonValue value = fetchData(rulesFileName, DataType.Rule, propertyName);
        return new Array<>(value.asStringArray());
    }

    public static JsonValue fetchData(String resourceName, DataType dataType, String propertyName){
        FileHandle fileHandle = AssetsUtil.findFile("data",
                resourceName + dataType.name() + JSON);
        if(fileHandle == null){
            throw new NoSuchElementException();
        }
        JsonValue value = readFromFile(fileHandle.path());
        return getProperty(propertyName, value);
    }

    public static JsonValue readFromFile(String resourcePath){
        JsonReader reader = new JsonReader();
        return reader.parse(Gdx.files.internal(resourcePath));
    }

    public static JsonValue getProperty(String property, JsonValue parent){
        AtomicReference<JsonValue> jsonValue = new AtomicReference<>(parent.get(property));
        if(jsonValue.get() != null){
            return jsonValue.get();
        }
        JsonValue.JsonIterator jsonIterator = parent.iterator();
        jsonIterator.forEach(iterableValue -> {
            if (iterableValue.child != null) {
                 jsonValue.set(getProperty(property, iterableValue.child));
            }
        });
        if(jsonValue.get() == null)
            throw new NullPointerException("Property does not exists.");
        return null;
    }

    public static int getAsInt(String property, JsonValue parent){
        return Objects.requireNonNull(getProperty(property, parent)).asInt();
    }

}
