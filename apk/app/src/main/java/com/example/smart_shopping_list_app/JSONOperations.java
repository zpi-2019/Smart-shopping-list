package com.example.smart_shopping_list_app;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class JSONOperations {
    static class Helper{
        List<String> keysList;
        List<List<Double>> distances;

        Helper(List<String> keysList, List<List<Double>> distances){
            this.distances = distances;
            this.keysList = keysList;
        }
    }

    static Helper readDistances(InputStream responseBody) throws IOException {
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(responseBodyReader);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(bufferedReader.readLine());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> keysList = new ArrayList<>();
        List<List<Double>> distances = new ArrayList<>();
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            keysList.add(key);
            ArrayList <Double> list = new ArrayList();
            try {
                JSONArray array = jsonObject.getJSONArray(key);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.getDouble(i));
                }
                distances.add(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return new Helper(keysList, distances);
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

    static JSONObject writeList(List<ListItem> list, String token){
        JSONObject postData = new JSONObject();
        try {
            postData.put("userToken", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items = new JSONArray();
        for(ListItem item: list) {
            try {
                JSONArray array = new JSONArray();
                array.put(item.ProductName);
                array.put(item.Amount);
                array.put(item.Unit);
                items.put(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            postData.put("items", items);
            postData.put("listId", list.get(0).IDList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }

    static void readLists(InputStream responseBody){
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(responseBodyReader);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(bufferedReader.readLine());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
