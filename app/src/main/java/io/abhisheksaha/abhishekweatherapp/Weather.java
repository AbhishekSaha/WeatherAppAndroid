package io.abhisheksaha.abhishekweatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class Weather extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.v("Weather.class", "Entered Weather.java");

        Intent intent = getIntent();

        final String apiOutput = intent.getStringExtra(Main.EXTRA_MESSAGE);
        final String zip = intent.getStringExtra(Main.ZIP);
        final String [] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        try {
            JSONObject obj = new JSONObject(apiOutput);
            JSONObject data = obj.getJSONObject("data");
            JSONArray arr = data.getJSONArray("current_condition");
            JSONObject today = arr.getJSONObject(0);
            String temp_f = today.getString("temp_F");

            JSONArray arr2 = today.getJSONArray("weatherDesc");
            JSONObject obj2 = arr2.getJSONObject(0);
            String sun = obj2.getString("value");
            Log.e("w/e", sun);

            JSONArray arr3 = data.getJSONArray("weather");
            JSONObject obj5 = arr3.getJSONObject(0);
            String maxT = obj5.getString("maxtempF");
            Log.e("w/e", maxT);
            String minT = obj5.getString("mintempF");

            String message = temp_f;
            Log.d("Weather.class", message);
            TextView textView = (TextView) findViewById(R.id.title);
            textView.setTextSize(24);
            String [] arrOfStrings  = new String[4];

            String m1 = "Current Temperature: ";
            m1 = m1.concat(message);
            arrOfStrings[0] = m1;

            String m2 = "High: "; m2 = m2.concat(maxT); arrOfStrings[1] = m2;
            String m3 = "Low: "; m3 = m3.concat(minT); arrOfStrings[2] = m3;
            String m4 = "Sky is currently: "; m4 = m4.concat(sun); arrOfStrings[3] = m4;

            String ster = String.format("\n%s\n" +
                    "%s\n" +
                    "%s\n" +
                    "%s", arrOfStrings);
            String arch = "Weather for: ";
            arch = arch.concat(zip).concat(ster);
            textView.setText(arch);





        } catch (JSONException e) {
            TextView t1 = (TextView) findViewById(R.id.title);
            t1.setText("ZIP Code not found");

        }

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Date d1 = new Date();
                    int d2 = d1.getDate();
                    String [] fiveDays = new String[5];

                    try {
                        JSONObject obj = new JSONObject(apiOutput);
                        JSONObject data = obj.getJSONObject("data");
                        JSONArray arr3 = data.getJSONArray("weather");
                        int i = 0;
                        for (i = 0; i<5 ; i++) {
                            JSONObject obj5 = arr3.getJSONObject(i);
                            String maxT = obj5.getString("maxtempF");

                            String minT = obj5.getString("mintempF");
                            String iday = daysOfWeek[d2 + i].concat(": ").concat("High- ").concat(maxT).concat(" Low- ").concat(minT);
                            fiveDays[i] = iday;

                            if(i+d2==6)
                                break;

                        }
                        i++;
                        int j = i; int w = i;
                        if (i<5){
                            d2=0;
                            for (j =0 ;j<(5-i); j++){
                                JSONObject obj5 = arr3.getJSONObject(j);
                                String maxT = obj5.getString("maxtempF");
                                String minT = obj5.getString("mintempF");
                                String iday = daysOfWeek[d2 + j].concat(": ").concat("High- ").concat(maxT).concat(" Low- ").concat(minT);
                                fiveDays[w] = iday;
                                w++;
                                Log.e("five day" , maxT);
                                Log.e("five day", iday);
                            }
                        }

                        String ster = String.format("%s\n%s\n%s\n" +
                                "%s\n%s\n", (Object [])fiveDays);
                        String arch = "Five Day Forecast for ";
                        arch = arch.concat(zip).concat("\n").concat(ster);

                        TextView textView = (TextView) findViewById(R.id.title);
                        textView.setTextSize(24);
                        textView.setText(arch);


                    } catch (JSONException e) {
                        TextView t1 = (TextView) findViewById(R.id.title);
                        t1.setText("ZIP Code not found");

                    }

                } else {
                    try {
                        JSONObject obj = new JSONObject(apiOutput);
                        JSONObject data = obj.getJSONObject("data");
                        JSONArray arr = data.getJSONArray("current_condition");
                        JSONObject today = arr.getJSONObject(0);
                        String temp_f = today.getString("temp_F");

                        JSONArray arr2 = today.getJSONArray("weatherDesc");
                        JSONObject obj2 = arr2.getJSONObject(0);
                        String sun = obj2.getString("value");
                        Log.e("w/e", sun);

                        JSONArray arr3 = data.getJSONArray("weather");
                        JSONObject obj5 = arr3.getJSONObject(0);
                        String maxT = obj5.getString("maxtempF");
                        Log.e("w/e", maxT);
                        String minT = obj5.getString("mintempF");

                        String message = temp_f;
                        Log.d("Weather.class", message);
                        TextView textView = (TextView) findViewById(R.id.title);
                        textView.setTextSize(24);
                        String [] arrOfStrings  = new String[4];

                        String m1 = "Current Temperature: ";
                        m1 = m1.concat(message);
                        arrOfStrings[0] = m1;

                        String m2 = "High: "; m2 = m2.concat(maxT); arrOfStrings[1] = m2;
                        String m3 = "Low: "; m3 = m3.concat(minT); arrOfStrings[2] = m3;
                        String m4 = "Sky is currently: "; m4 = m4.concat(sun); arrOfStrings[3] = m4;

                        String ster = String.format("\n%s\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s", arrOfStrings);
                        String arch = "Weather for: ";
                        arch = arch.concat(zip).concat(ster);
                        textView.setText(arch);





                    } catch (JSONException e) {
                        TextView t1 = (TextView) findViewById(R.id.title);
                        t1.setText("ZIP Code not found");

                    }


                }
            }
        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
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
