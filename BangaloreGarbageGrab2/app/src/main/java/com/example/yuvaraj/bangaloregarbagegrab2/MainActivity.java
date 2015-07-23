package com.example.yuvaraj.bangaloregarbagegrab2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    ListView list;
    Button reportBtn;
    TextView currentTime,Latitude,Longitude,ct,lat,lng;
    ImageView imgname,mimgview;
    Button Btngetdata;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://192.168.1.10:1337/user/";

    //JSON Node Names
    private static final String TAG_OS = "android";
    private static final String TAG_time="currentTime";
    private static final String TAG_latitude= "Latitude";
    private static final String TAG_longitude = "Longitude";
    private static final String TAG_image = "imgname";
    String latv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reportBtn = (Button)findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(this);

        oslist = new ArrayList<HashMap<String, String>>();
        // Calling async task to get json
        // new JSONParse().execute();
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();

            }
        });

    }//onCreate

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.reportBtn:
                Intent i = new Intent(MainActivity.this, report_issue.class);
                startActivity(i);
                break;
            default:
                break;

        }
    }//onclick ends

    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Latitude = (TextView)findViewById(R.id.latitudeId);
            Longitude = (TextView)findViewById(R.id.longitudeID);
            currentTime = (TextView)findViewById(R.id.timeID);
            imgname= (ImageView)findViewById(R.id.imageViewID);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONArray doInBackground(String... args) {

            //Getting JSON from URL
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);
            System.out.println("LENGTH" + json.length());
            return json;

        }
        @Override
        protected void onPostExecute(JSONArray json) {
            pDialog.dismiss();
            try {
                //Getting JSON Array from URL
                for(int i = 0; i < json.length(); i++){
                    JSONObject c = json.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String  Latitude= c.getString(TAG_latitude);
                    String Longitude = c.getString(TAG_longitude);
                    String currentTime = c.getString(TAG_time);
                    String imgname = c.getString(TAG_image).toString();

                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_latitude, Latitude);
                    map.put(TAG_longitude, Longitude);
                    map.put(TAG_time, currentTime);
//                    if(imgname == ImageEqualMethod()){
//                        map.put(TAG_image, imgname);
//                    }

                    oslist.add(map);
                    list=(ListView)findViewById(R.id.list);

                    ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                            R.layout.list_row,
                            new String[] { TAG_latitude,TAG_longitude,TAG_time}, new int[] {
                             R.id.latitudeId, R.id.longitudeID,R.id.timeID});
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(MainActivity.this, "You Clicked at "+oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }














}//MainActivity
