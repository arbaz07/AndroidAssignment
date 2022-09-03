package com.example.androidassignment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        /**
         * Check User Email and show it on profile textview
         */
        emailText = findViewById(R.id.emailText);
        if (SetAndGetData.getInstance().getFirebaseUser().getEmail() != null) {
            emailText.setText(SetAndGetData.getInstance().getFirebaseUser().getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetAndGetData.getInstance().getBottomNavigationView().setSelectedItemId(R.id.home);
    }
}