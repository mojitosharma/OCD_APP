package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ocd.model.User;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {
    private static final String PREF_NAME = "LoginPref";
    private static final String USER_DATA = "user_data";

    private CheckBox checkBox;
    private Button btnContinue;
    private TextView tvTermsAndConditions;
    User user;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        readingData();
        initialize();
        loadTermsAndConditions();
        onClickCheckBox();
        onClickBtnContinue();
    }

    private void readingData() {
        user = (User) getIntent().getSerializableExtra("USER");
    }

    private void initialize() {
        checkBox = findViewById(R.id.checkBox);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
    }

    private void onClickCheckBox() {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnContinue.setEnabled(isChecked);
            if (isChecked) {
                btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
            } else{
                btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
            }
        });
    }

    private void onClickBtnContinue() {
        btnContinue.setOnClickListener(v -> {
            retrofitService = new RetrofitService();
            registerUser();
        });
    }


    private void registerUser() {
        UserAPI usrapi =retrofitService.getRetrofit().create(UserAPI.class);
        Call<User> call = usrapi.registerUser(user);
        System.out.println(user.getDob());
        System.out.println(user.getDay_of_enrollment());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response.body());
                System.out.println("here");
                if (response.isSuccessful()) {
                    // User registration successful
                    User registeredUser = response.body();
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                            .create();
                    String userJson = gson.toJson(registeredUser);
                    SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(USER_DATA, userJson);
                    editor.apply();
                    Intent intent = new Intent(TermAndConditionActivity.this, HomeActivity.class);
                    intent.putExtra("user", registeredUser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(TermAndConditionActivity.this, "Failed to Register!! Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure
                    Toast.makeText(TermAndConditionActivity.this, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(VerifyOTPActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                }
        });
    }



    private void loadTermsAndConditions() {
        try {
            // Read terms and conditions from a file
            InputStream inputStream = getResources().openRawResource(R.raw.term_and_condition);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            inputStream.close();

            // Convert HTML content to Spanned
            Spanned spannedHtml = Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT);

            // Set the loaded terms and conditions to the TextView
            tvTermsAndConditions.setText(spannedHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
