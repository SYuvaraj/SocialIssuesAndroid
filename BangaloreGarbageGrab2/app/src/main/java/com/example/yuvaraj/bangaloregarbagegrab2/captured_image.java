package com.example.yuvaraj.bangaloregarbagegrab2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.database.Cursor;
import android.widget.TextView;
import 	org.apache.http.entity.StringEntity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Criteria;

/**
 * Created by Yuvaraj on 12/07/15.
 */
public class captured_image extends ActionBarActivity implements View.OnClickListener,LocationListener{

    ImageView capturedImage, uploadIcon;
//    private static final String SERVER_ADDRESS = "http://192.168.0.173:1337/";//http://54.169.148.127:1337/";
    TextView latVal, lngVal, currTime;
    String latv,lngv;
    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    //GPS VARIABLES
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
//    String lat;
//    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private String provider;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captured_image);

        capturedImage = (ImageView)findViewById(R.id.captured_snap);
        latVal = (TextView)findViewById(R.id.latvalue);
        lngVal = (TextView)findViewById(R.id.lngvalue);
        currTime = (TextView)findViewById(R.id.currentTime);
        currTime.setText(currentDateTimeString);
//        latVal.setText(""+1234);
//        lngVal.setText("" + 5678);
        uploadIcon=(ImageView)findViewById(R.id.upload_icon);
        capturedImage.setOnClickListener(this);
        uploadIcon.setOnClickListener(this);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(provider, 0, 0, this);
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);

        selectImage();


    }//onCreate

    //GPS
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            lat = (double) (location.getLatitude());
            lng = (double) (location.getLongitude());
            latVal.setText(String.valueOf(lat));
            lngVal.setText(String.valueOf(lng));
        } else {
            latVal.setText("Provider not available");
            lngVal.setText("Provider not available");
        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    //Select Image Method
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(captured_image.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    capturedImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                capturedImage.setImageBitmap(thumbnail);
            }
        }
    }//activity result ends

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.upload_icon:
                Bitmap image = ((BitmapDrawable) capturedImage.getDrawable()).getBitmap();
                new UploadImage(image).execute();
                break;
            default:
                break;

        }
    }//onclick ends




    //Async Task
    private class UploadImage extends AsyncTask<Void, Void, Void> {


        Bitmap image;
        public UploadImage(Bitmap image){
            this.image = image;
        }
        @Override
        protected Void doInBackground(Void... params){

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            // 1. create HttpClient
            HttpClient client = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost post = new HttpPost("http://192.168.1.10:1337/user/uploadFile");

            //Encoding post data
            try{

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("image", encodedImage);
                jsonObject.put("Latitude",String.valueOf(lat) );//latVal
                jsonObject.put("Longitude", String.valueOf(lng));//lngVal
                jsonObject.put("currentTime", currentDateTimeString);//currTime

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();


                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                post.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json");

            }
            catch(Exception e){
                e.printStackTrace();
            }

            //send http post request
            try {
                HttpResponse response = client.execute(post);
                // write response to log
                Log.d("Http Post Response:", response.toString());
            }
            catch (ClientProtocolException e) {
                // Log exception
                e.printStackTrace();
            } catch (IOException e) {
                // Log exception
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

}
