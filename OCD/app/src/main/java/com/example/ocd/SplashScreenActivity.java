package com.example.ocd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String PREF_NAME = "LoginPref";
    private static final String FIRST_TIME_KEY = "isFirstTime";
    private static final int SPLASH_SCREEN_DURATION = 3000;
    private int currentScreen = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentScreen = getIntent().getIntExtra("currentScreen", 1);

        loadLayout();

        if(currentScreen == 1 || currentScreen == 2){
            onClickSkip();
            onClickNext();
        } else if (currentScreen == 3) {
            onClickLetsMakeItHappen();
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                moveToNextScreen();
//            }
//        }, SPLASH_SCREEN_DURATION);
    }

    // Load the appropriate layout for each splash screen
    private void loadLayout() {
        if (currentScreen == 1) {
            setContentView(R.layout.activity_splash_screen);
        } else if (currentScreen == 2) {
            setContentView(R.layout.activity_splash_second);
        } else {
            setContentView(R.layout.activity_splash_third);
        }
    }

    private void moveToNextScreen() {
        currentScreen++;
        if (currentScreen <= 3) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.putExtra("currentScreen", currentScreen);
            startActivity(intent);
            // If it's the first time, set the flag to false and show splash activity
            SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();
            finish();
        }
    }

    private void onClickSkip(){
        Button skipButton = findViewById(R.id.btnSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skip all the splash screens
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void onClickNext(){
        Button nextButton = findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next splash screen
                moveToNextScreen();
            }
        });
    }

    private void onClickLetsMakeItHappen(){
        // If it's the last screen, set up the continue button
        Button continueButton = findViewById(R.id.btnLetsMakeItHappen);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the main activity
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        });
    }



}
