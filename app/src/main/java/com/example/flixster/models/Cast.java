package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cast {

    String profile_path;
    String name;
    String character;

    public Cast(JSONObject jsonObject) throws JSONException {
        profile_path = jsonObject.getString("profile_path");
        name = jsonObject.getString("name");
        character = jsonObject.getString("character");
    }

    public static List<Cast> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Cast> cast = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++) {
            cast.add(new Cast(movieJsonArray.getJSONObject(i)));
        }
        return cast;
    }

    public String getProfile_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", profile_path);
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
}
