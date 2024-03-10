package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ocd.model.Name;
import com.example.ocd.model.User;
import com.example.ocd.registraion.TermAndConditionActivity;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Callback;
import retrofit2.Response;

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


    private void registerUser() {
        RetrofitService retrofitService = new RetrofitService();
        User user = new User(new Name("john"), "20/01/1990", "Male", "Bachelor's Degree", "Software Engineer", "mohit20086@iiitd.ac.in", "securePassword123");

        UserAPI usrapi = retrofitService.getRetrofit().create(UserAPI.class);
        Call<ResponseBody> call = usrapi.registerUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String contentType = response.headers().get("Content-Type");
                    ResponseBody responseBody = response.body();

                    if (contentType != null && contentType.contains("application/json")) {
                        try {
                            User user = parseJsonResponse(responseBody.string(), User.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (contentType != null && contentType.contains("text/plain")) {
                        try {
                            String stringValue = responseBody.string();
                            System.out.println("Sting");
                            System.out.println(stringValue);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (contentType != null && contentType.contains("text/html")) {
                        try {
                            int intValue = Integer.parseInt(responseBody.string());
                            System.out.println("Int");
                            System.out.println(intValue);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        runOnUiThread(() -> {
                            assert responseBody != null;
                            Toast.makeText(MainActivity.this, "Registration failed: " + responseBody, Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    // Server returned an error response
                    Toast.makeText(MainActivity.this, "Error: Failed to Register!! Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(MainActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                Logger.getLogger(TermAndConditionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

    }


    private <T> T parseJsonResponse(String json, Class<T> type) {
        // Use your preferred JSON parsing library (e.g., Gson) to deserialize the JSON string
        // and return the corresponding object of the specified type.
        // For simplicity, I'm assuming Gson here.
        System.out.println("here");
        Gson gson = new Gson();
        User registeredUser = (User) gson.fromJson(json, type);

        // Save user data to SharedPreferences

        String userJson = gson.toJson(registeredUser);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_DATA, userJson);
        editor.apply();

        return (T) registeredUser;

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