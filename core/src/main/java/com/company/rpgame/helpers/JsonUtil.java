package com.company.rpgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.company.rpgame.helpers.Box2D.components.Size;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class JsonUtil {
    public static final String JSON = ".json";
    public enum DataType {
        Data,
        Rule
    }
    private static final Json json = new Json();

    public static Array<String> readRules(String rulesFileName, String propertyName) {
        JsonValue value = fetchData(rulesFileName, DataType.Rule, propertyName);
        return new Array<>(value.asStringArray());
    }
    public static Size readSize(String resourceName, String propertyName){
        JsonValue data = fetchData(resourceName, DataType.Data, propertyName);
        return json.readValue(Size.class, data);
    }
    public static JsonValue fetchData(String resourceName, DataType dataType, String propertyName){
        FileHandle fileHandle = AssetsUtil.findFile(DataType.Data.name(),
                resourceName + dataType.name() + JSON);
        if(fileHandle == null){
            throw new NoSuchElementException("No such json file!" +
                    "\nJson file name: " + resourceName);
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
            throw new NullPointerException("Property does not exists: " + property);
        return null;
    }


}
