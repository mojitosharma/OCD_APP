package com.example.ocd.registraion;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ocd.LoginActivity;
import com.example.ocd.R;
import com.example.ocd.model.User;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private Button btnContinue;
    private TextView tvTermsAndConditions;
    private ProgressBar progressBar;
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
        onBackPressedCustom();
    }

    private void readingData() {
        user = (User) getIntent().getSerializableExtra("USER");
    }

    private void initialize() {
        checkBox = findViewById(R.id.checkBox);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        progressBar = findViewById(R.id.progressBar);
        btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        retrofitService = new RetrofitService();
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
            registerUser();
        });
    }


    private void registerUser() {
        progressBar.setVisibility(View.VISIBLE);
       UserAPI usrapi = retrofitService.getRetrofit().create(UserAPI.class);
        Call<ResponseBody> call = usrapi.registerUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    String contentType = response.headers().get("Content-Type");
                    ResponseBody responseBody = response.body();

                    try {
                        if (contentType != null && contentType.contains("application/json")) {

                            Gson gson = new Gson();
                            assert responseBody != null;
                            User registeredUser = gson.fromJson(responseBody.string(), User.class);
                            Intent intent = new Intent(TermAndConditionActivity.this, VerifyOTPActivity.class);
                            intent.putExtra("USER", registeredUser);
                            startActivity(intent);
                        } else if (contentType != null && contentType.contains("text/plain")) {
                            assert responseBody != null;
                            String stringValue = responseBody.string();
                            Toast.makeText(TermAndConditionActivity.this, stringValue, Toast.LENGTH_SHORT).show();
                        } else {
                            runOnUiThread(() -> {
                                assert responseBody != null;
                                Toast.makeText(TermAndConditionActivity.this, "Registration failed: " + responseBody, Toast.LENGTH_SHORT).show();
                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        assert response.errorBody() != null;
                        Toast.makeText(TermAndConditionActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TermAndConditionActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                Logger.getLogger(TermAndConditionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
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

    public void onBackPressedCustom() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(TermAndConditionActivity.this, SignUpActivity.class);
                intent.putExtra("USER", user);

                // Start the SignUpActivity
                startActivity(intent);
                finish();

            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }


}
