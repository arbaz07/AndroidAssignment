package com.example.androidassignment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ImageView companyLogo;
    private TextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide(); //Hide the action bar from the Splash Screen!

        companyLogo = findViewById(R.id.companyLogo);
        companyName = findViewById(R.id.companyName);

        animation();

        splashScreenTimer();
    }

    /**
     * This function is used for animating company logo and the text
     */
    private void animation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(SPLASH_DISPLAY_LENGTH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                companyLogo.setAlpha(alpha);
                companyName.setAlpha(alpha);
            }
        });
        valueAnimator.start();
    }

    /**
     * This function will stop the Splash Screen for 3 seconds
     */
    private void splashScreenTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Login.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 3 * 1000); // wait for 3 seconds
    }
}