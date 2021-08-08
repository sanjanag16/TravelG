package com.example.travelg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {

    ImageButton bangaloreBTN,puneBTN,mumbaiBTN,delhiBTN,hydBTN,chennaiBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    public void redirect(View v){
        String city = v.getTag().toString(); // Contains tag value
        Intent onlyIntent =new Intent(LandingPage.this, ScrollingActivity.class);
        onlyIntent.putExtra("City",city);
        startActivity(onlyIntent);
    }
}