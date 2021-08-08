package com.example.travelg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ScrollingActivity extends AppCompatActivity {

    TextView cityname, descriptionView;
    TextView txt_help_gest;

    ImageView profileView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scrolling);
/*
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar);
            toolBarLayout.setTitle(getTitle());
*/
/*
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScrollingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        Intent intent = getIntent();
        String city = intent.getStringExtra("City");
        txt_help_gest = (TextView) findViewById(R.id.hotelsbtn);
        // hide until its title is clicked
        txt_help_gest.setVisibility(View.GONE);

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


    public void toggle_contents(View v) {
        txt_help_gest.setVisibility(txt_help_gest.isShown()
                ? View.GONE
                : View.VISIBLE);
    }


}