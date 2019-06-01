package com.example.smart_shopping_list_app;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JSONOperations {

    static void readDistances(InputStream responseBody, AppViewModel appViewModel) throws IOException {
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
        JsonReader jsonReader = new JsonReader(responseBodyReader);
        try {
            jsonReader.beginObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(jsonReader.hasNext()){
            String key = jsonReader.nextName();
            //first loop load all keys
        }
        jsonReader.close();
    }

    static int readModelVersion(InputStream responseBody) throws IOException {
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
        JsonReader jsonReader = new JsonReader(responseBodyReader);
        try {
            jsonReader.beginObject();
        } catch (IOException e){
            e.printStackTrace();
        }
        int version = jsonReader.nextInt();
        jsonReader.close();
        return version;
    }

    static JSONObject write(Object object, AppViewModel appViewModel){
        JSONObject postData = new JSONObject();
        try{
            postData.put("Key", "value");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return postData;
    }
}
