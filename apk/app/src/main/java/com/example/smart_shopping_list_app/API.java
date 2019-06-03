package com.example.smart_shopping_list_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import javax.net.ssl.HttpsURLConnection;

class API {
    private static String url = "https://smart-shopping-list-pwr-api.herokuapp.com/";

    private static class PullNewListsUpdate extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            URL httpEndpoint;
            HttpsURLConnection connection;
            try {
                httpEndpoint = new URL(url+ "lists");
                connection = (HttpsURLConnection) httpEndpoint.openConnection();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class PushNewListsUpdate extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String[] objects) {

            URL httpEndpoint;
            HttpsURLConnection connection;

            try {
                httpEndpoint = new URL(url+ "lists/");
                connection = (HttpsURLConnection) httpEndpoint.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setAllowUserInteraction(false);
                String json = objects[0];
                connection.setRequestProperty("Content-length", json.getBytes().length + "");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(json.getBytes(StandardCharsets.UTF_8));
                outputStream.close();

                connection.connect();
                Log.d("Api", String.valueOf(connection.getResponseCode()));
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class UpdateDistances extends AsyncTask<Void, Void, JSONOperations.Helper> {
        @Override
        protected JSONOperations.Helper doInBackground(Void... voids) {
            URL httpEndpoint;
            HttpsURLConnection connection;
            JSONOperations.Helper helper = null;
            try {
                httpEndpoint = new URL(url + "model");
                connection = (HttpsURLConnection) httpEndpoint.openConnection();
                if(connection.getResponseCode() == 200){
                    helper = JSONOperations.readDistances(connection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Update", "Data to update prepared.");

            return helper;
        }
    }

    static class CheckModelVerison extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            URL httpEndpoint;
            HttpsURLConnection connection;
            int version = 0;
            try {
                httpEndpoint = new URL(url + "model/version");
                connection = (HttpsURLConnection) httpEndpoint.openConnection();
                if(connection.getResponseCode() == 200){
                    version = JSONOperations.readModelVersion(connection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return version;
        }
    }
}
