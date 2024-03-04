package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.ocd.model.Name;
import com.example.ocd.model.User;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginPref";
    private static final String FIRST_TIME_KEY = "isFirstTime";
    private static final String USER_DATA = "user_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
//        startActivity(intent1);
//        finish();



        // Check if the app is opened for the first time
        checkFirstTime();


        // Rest of your MainActivity code...
    }

    private void checkFirstTime() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME_KEY, true);

        String userJson = prefs.getString(USER_DATA, null);
        Gson gson = new Gson();
        User savedUser = gson.fromJson(userJson, User.class);

        if (savedUser != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (isFirstTime) {
                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void createuser() {
        User user = new User(new Name("john"), "20/01/1990", "Male", "Bachelor's Degree", "Software Engineer", "john.doe@example.com", "securePassword123");
        System.out.println("here are we");
        Gson gson = new Gson();
        String updatedUserJson = gson.toJson(user);
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_DATA, updatedUserJson);
        editor.apply();
    }
}

//    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//        @Override
//        public void handleOnBackPressed() {
//            requireActivity().getFragmentManager().popBackStack();
//        }
//    };

//dob: {
//bsonType: 'date',
//description: 'Date of birth of user. Must be in date format'
//        },
//day_of_enrollment: {
//bsonType: 'date',
//description: 'Date of enrollment into the therapy program'
//        },