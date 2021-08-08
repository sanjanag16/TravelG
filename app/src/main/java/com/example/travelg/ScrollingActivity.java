package com.example.travelg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ScrollingActivity extends AppCompatActivity {

    TextView cityname, descriptionView;

    ImageView profileView;
    TextInputLayout til_hotels, til_hidden, til_tourist;
    AutoCompleteTextView act_hotels, act_hidden, act_tourist;

    ArrayList<String> arrayList_hotels,arrayList_tourist,arrayList_hidden;
    ArrayAdapter<String> arrayAdapter_hotels,arrayAdapter_tourist,arrayAdapter_hidden;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scrolling);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScrollingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        til_hotels = (TextInputLayout) findViewById(R.id.til_Hotels);
        act_hotels = (AutoCompleteTextView) findViewById(R.id.act_hotels);
        act_hotels.setThreshold(1);

        til_tourist = (TextInputLayout) findViewById(R.id.til_hidden);
        act_hidden = (AutoCompleteTextView) findViewById(R.id.act_hidden);
        act_hidden.setThreshold(1);


        til_hidden = (TextInputLayout) findViewById(R.id.til_TouristPlaces);
        act_tourist = (AutoCompleteTextView) findViewById(R.id.act_touristplaces);
        act_tourist.setThreshold(1);



        cityname = findViewById(R.id.cityNameView);
        descriptionView = findViewById(R.id.descriptionView);
        profileView = findViewById(R.id.profileView);

        Intent intent = getIntent();
        String city = intent.getStringExtra("City");

        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available() + 1;
            byte[] buffer = new byte[size];
            is.read(buffer);
            String json = new String(buffer, UTF_8); // One big JSON string with all the data
            JSONArray ja = new JSONArray(json); // JSON Array composed of JSON objects

            for (int i = 0; i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                if (jo.getString("city").equals(city)){

                    cityname.setText(jo.getString("city").toUpperCase(Locale.ROOT));
                    descriptionView.setText(jo.getString("description"));
                    String profile = city.toLowerCase(Locale.ROOT)+"_profile";
                    int resourceId = this.getResources().getIdentifier(profile,"drawable",this.getPackageName());
                    profileView.setImageResource(resourceId); // Sets profile image

//                  For all hotels
                    arrayList_hotels = new ArrayList<>();
                    for(int j=0; j<jo.getJSONArray("hotels").length();j++){
                        arrayList_hotels.add(jo.getJSONArray("hotels").getString(j));
                    }

                    arrayAdapter_hotels = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_hotels);
                    act_hotels.setAdapter(arrayAdapter_hotels);

//                  For all tourist places

                    arrayList_tourist = new ArrayList<>();
                    for(int j=0; j<jo.getJSONArray("tourist").length();j++){
                        arrayList_tourist.add(jo.getJSONArray("tourist").getString(j));
                    }


                    arrayAdapter_tourist = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_tourist);
                    act_tourist.setAdapter(arrayAdapter_tourist);


//                  For all hidden

                    arrayList_hidden = new ArrayList<>();
                    for(int j=0; j<jo.getJSONArray("hidden").length();j++){
                        arrayList_hidden.add(jo.getJSONArray("hidden").getString(j));
                    }

                    arrayAdapter_hidden = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_hidden);
                    act_hidden.setAdapter(arrayAdapter_hidden);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}