package com.example.yuvaraj.bangaloregarbagegrab2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpGet;

import android.util.Log;

import org.apache.http.params.HttpParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.HttpVersion;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.client.HttpClient;


public class JSONParser {

    static InputStream is = null;
//    static JSONObject jObj = null;
    static JSONArray jObj=null;
    static String json = "";


    // constructor
    public JSONParser() {

    }

    public JSONArray getJSONFromUrl(String url) {

        // Making HTTP request
        try {

            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
//            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);//iso-8859-1
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
//            Log.i("JSON Parser", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

        // try parse the string to a JSON object
        try {

//            jObj = new JSONObject(json);
            jObj = new JSONArray(json);

//            jObj = new JSONObject(json.substring(3));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }


}
