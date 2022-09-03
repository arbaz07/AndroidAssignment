package com.example.androidassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide(); //Hide the action bar from the home screen!

        SetAndGetData.getInstance().setBottomNavigationView(findViewById(R.id.bottomBar));


        /**
         * Click Listeners of Bottom Navigation Bar
         */
        SetAndGetData.getInstance().getBottomNavigationView().setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class)); //Home Icon Click Listener
                        return true;
                    case R.id.cam:
                        SetAndGetData.getInstance().setCamWebView(true);
                        startActivity(new Intent(getApplicationContext(), WebView.class)); //Cam WebView Icon Click Listener
                        return true;
                    case R.id.audio:
                        startActivity(new Intent(getApplicationContext(), WebView.class)); //Audio WebView Click Listener
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class)); //Profile Click Listener
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}