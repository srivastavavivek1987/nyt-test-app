package com.test.handler;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.test.DataFetchListener;
import com.test.model.Response;
import com.test.model.Result;
import com.test.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataFetchTask extends AsyncTask<String, Void, Response> {
    private DataFetchListener listener;
    private Context ctx;
    private boolean isSearch;

    public DataFetchTask(Context ctx,boolean isSearch, DataFetchListener listener) {
        this.listener = listener;
        this.ctx = ctx;
        this.isSearch = isSearch;
    }

    protected Response doInBackground(String... urls) {
        Response response = null;

        try {
            if (Utils.isNetworkOnline(ctx)) {
                //basically I am using Volley for network request but using HttpURLConnection for demo app
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder("");
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String json = stringBuilder.toString();
                Parser parser = new Parser();
                if(!isSearch) {
                    response = parser.parseJson(json);
                }else{
                    response = parser.parseSearchJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(Response response) {
        if (listener != null) {
            listener.doAfterFetchData(response);
        }
    }
}