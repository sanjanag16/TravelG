package com.example.travelg;

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
    TextInputLayout til_hotels, til_hidden;
    AutoCompleteTextView act_hotels, act_hidden;

    ArrayList<String> arrayList_hotels;
    ArrayAdapter<String> arrayAdapter_hotels;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        til_hotels = (TextInputLayout) findViewById(R.id.til_Hotels);
        act_hotels = (AutoCompleteTextView) findViewById(R.id.act_hotels);

        arrayList_hotels = new ArrayList<>();
        arrayList_hotels.add("hotel1");
        arrayList_hotels.add("hotel2");
        arrayList_hotels.add("hotel3");
        arrayList_hotels.add("hotel4");

        arrayAdapter_hotels = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_hotels);
        act_hotels.setAdapter(arrayAdapter_hotels);

        act_hotels.setThreshold(1);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scrolling);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScrollingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        cityname = findViewById(R.id.cityNameView);
        descriptionView = findViewById(R.id.descriptionView);
        profileView = findViewById(R.id.profileView);
        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available() + 1;
            byte[] buffer = new byte[size];
            is.read(buffer);
            String json = new String(buffer, UTF_8);
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                String city = null;
                if (jo.getString("city").equals(city)){
                    /*Toast.makeText(ScrollingActivity.this,"Current city is " + city+" and some data is " + jo.getString("description"),Toast.LENGTH_SHORT).show();*/
                    cityname.setText(jo.getString("city").toUpperCase(Locale.ROOT));
                    descriptionView.setText(jo.getString("description"));
                    String profile = city.toLowerCase(Locale.ROOT)+"_profile";
                    int resourceId = this.getResources().getIdentifier(profile,"drawable",this.getPackageName());
                    profileView.setImageResource(resourceId);
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