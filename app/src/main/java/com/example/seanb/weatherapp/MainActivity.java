package com.example.seanb.weatherapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView temp;
    TextView date;
    TextView wDescription;
    TextView cityName;
    ImageView wImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getActionBar().hide();

        temp = findViewById(R.id.temp);
        temp.setText("35");
        date = findViewById(R.id.date);
        wDescription = findViewById(R.id.wDescription);
        cityName = findViewById(R.id.cityName);
        date.setText(getCurrentDate());

        wImageView = (ImageView) findViewById(R.id.wImageView);



        String url = "https://api.openweathermap.org/data/2.5/weather?appid=23374a4b49ecf1e91734d3546bd125b7&zip=14260&,840&units=Imperial";

         JsonObjectRequest jsObjectRequest= new JsonObjectRequest
                 (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                     @Override
                     public void onResponse(JSONObject response) {
                         Log.v("WEATHER", "Response" + response.toString());
//                         temp.setText("Response: " + response.toString());
                         try {
                             JSONObject mainJO = response.getJSONObject("main");
                             JSONArray wArray= response.getJSONArray("weather");
                             JSONObject firstWeatherObject = wArray.getJSONObject(0);


                             String weatherDescription = firstWeatherObject.getString("description");
                             String tempJS = Integer.toString((int) Math.round(mainJO.getDouble("temp")));
                             String city = response.getString("name");

                             temp.setText(tempJS);
                             wDescription.setText(weatherDescription);
                             cityName.setText(city);
                             int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
                             wImageView.setImageResource(iconResourceId);



                         } catch (JSONException e) {

                             e.printStackTrace();

                         }

                     }
                 }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         // TODO: Handle error
                     }

                });

         RequestQueue queue = Volley.newRequestQueue(this);
         queue.add(jsObjectRequest);

// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(" EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }





}
