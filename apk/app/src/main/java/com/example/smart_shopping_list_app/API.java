package com.example.smart_shopping_list_app;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class API {
    private static String url = "https://smart-shopping-list-pwr-api.herokuapp.com/?fbclid=IwAR2_OJ6GMw9E2xOsIz1DRl2lpCEgS4QXP__IOvuOzgMl9-kJxZrZnIs06l4";

    private static class PullNewListsUpdate extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            URL httpEndpoint = null;
            HttpsURLConnection connection = null;
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

    private static class PushNewListsUpdate extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }

    private static class UpdateDistances extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }

    private static class CheckModelVerison extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            URL httpEndpoint = null;
            HttpsURLConnection connection = null;
            try {
                httpEndpoint = new URL(url);
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
}
