package com.example.smart_shopping_list_app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

class API {
    private static String url = "https://smart-shopping-list-pwr-api.herokuapp.com/";

    private static class PullNewListsUpdate extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            URL httpEndpoint;
            HttpsURLConnection connection;
            try {
                httpEndpoint = new URL("");
                connection = (HttpsURLConnection) httpEndpoint.openConnection();
                connection.setRequestMethod("");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class PushNewListsUpdate extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
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
