package io.abhisheksaha.abhishekweatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import junit.framework.Test;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

//Abhishek Saha Production
public class Main extends Activity {

    String ul = "http://api.worldweatheronline.com/free/v2/weather.ashx?key=51590f2da9a58a9b65af312c41fa9&q="; String rst = "&format=json";

    public static String EXTRA_MESSAGE = "com.example.webapitutorial.MESSAGE";
    public static String ZIP = "com.exap";
    final Context context = this;
    private class CallAPI extends AsyncTask<String, String, String> {
    public String zipper;
        @Override
        protected String doInBackground(String... params) {

            String urlString=params[0]; // URL to call
            zipper = urlString;
            String urll = ul.concat(urlString).concat(rst);
            String apiOutput = "";  String resultToDisplay = "";

            InputStream in = null;

            // HTTP Get
            try {

                URL url = new URL(urll);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                in = new BufferedInputStream(urlConnection.getInputStream());
                if (urlConnection.getResponseCode() != 200)
                {
                    throw new RuntimeException("Failed : HTTP error code : " + urlConnection.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
                apiOutput = br.readLine();
                System.out.println(apiOutput);
                Log.v("APICall", apiOutput);
                urlConnection.disconnect();

               /* JSONObject obj = new JSONObject(apiOutput);

                JSONObject data = obj.getJSONObject("data");
                JSONArray arr = data.getJSONArray("current_condition");
                JSONObject today = arr.getJSONObject(0);
                String temp_f = today.getString("temp_F");
                System.out.println(temp_f);*/
                return apiOutput;

            } catch (NullPointerException e ) {

                System.out.println("NPE exception");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




            return "Hoppy";

        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), Weather.class);
            Log.v("CallAPI","Entered OnPostExecute");
            intent.putExtra(EXTRA_MESSAGE, result);
            intent.putExtra(ZIP, zipper);

            startActivity(intent);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Log.v("Main Method", "About to callAPI");
        new CallAPI().execute(urll);
        Log.v("Main Method", "About to call bill");

        Log.v("Main Method", "called Bill");*/

        final Button b1 = (Button) findViewById(R.id.button);


        final Button b2 = (Button) findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = (EditText) findViewById(R.id.editText);
                String zip = ed.getText().toString();
                new CallAPI().execute(zip);
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final AlertDialog headsup = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("GPS Turned Off");
        headsup.setTitle("Triangulating Position");
        alertDialog.setMessage("Please turn on GPS/Location");
        headsup.setMessage("The device is currently triangulating your zip code, please wait");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions
                dialog.dismiss();
            }
        });
        headsup.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headsup.show();
                LocationManager localLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                localLocationManager.getLastKnownLocation("gps");
                localLocationManager.requestLocationUpdates("gps", 2000L, 10.0F, new LocationListener() {
                    public void onLocationChanged(Location paramAnonymousLocation) {

                        double d1 = paramAnonymousLocation.getLatitude();
                        double d2 = paramAnonymousLocation.getLongitude();
                        Geocoder localGeocoder = new Geocoder(Main.this.getApplicationContext(), Locale.getDefault());
                        try {
                            List localList = localGeocoder.getFromLocation(d1, d2, 1);
                            if (localList.size() == 1) {
                                Address localAddress = (Address) localList.get(0);
                                Object[] arrayOfObject = new Object[3];
                                if (localAddress.getMaxAddressLineIndex() > 0) ;
                                String zip = localAddress.getPostalCode();
                                new CallAPI().execute(zip);
                            }
                        } catch (IOException localIOException) {
                            localIOException.printStackTrace();
                            return;
                        }
                    }

                    public void onProviderDisabled(String paramAnonymousString) {
                       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                        alertDialog.show();
                    }

                    public void onProviderEnabled(String paramAnonymousString) {
                        //localTextView.setText("Triangulating position");

                    }

                    public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
