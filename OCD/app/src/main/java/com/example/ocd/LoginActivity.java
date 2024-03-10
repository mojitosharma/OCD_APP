package com.example.ocd;

import static com.example.ocd.helper.helperFunctions.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocd.model.User;
import com.example.ocd.registraion.SignUpActivity;
import com.example.ocd.registraion.VerifyOTPActivity;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "LoginPref";
    private static final String USER_DATA = "user_data";
    private static final String JWT = "jwt";
    private EditText emailEditText;
    private EditText passwordEditText;
    private FrameLayout btnShowPassword;
    private Button btnLogin;
    private TextView btnNewUser;

    private ImageView eyeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialize();
        onClickShowHidePassword();
        onClickLogin();
        onClickNewUser();
    }

    // Initialize views
    private void Initialize(){
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.txtEnterPassword);
        eyeImageView = findViewById(R.id.imgEye);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnLogin= findViewById(R.id.btnLogin);
        btnNewUser = findViewById(R.id.btnNewUser);
    }

    // Set a click for show hide password
    public void onClickShowHidePassword() {
        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    // If password is currently hidden, show it
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeImageView.setImageResource(R.drawable.img_eye);
                } else {
                    // If password is currently shown, hide it
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeImageView.setImageResource(R.drawable.img_eye_close);
                }
            }
        });

    }

    // Set a click listener for the login button
    public void onClickLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    loginUser(email, password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {

        RetrofitService retrofitService = new RetrofitService();
        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("email", email);
        jsonBody.addProperty("password", password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());

        Call<ResponseBody> call = userAPI.loginUser(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBodyString);
                        JSONObject userObject = jsonResponse.getJSONObject("user");
                        String jwt = jsonResponse.getString("jwt");
                        Gson gson = new Gson();
                        User registeredUser = gson.fromJson(userObject.toString(), User.class);
                        String userJson = gson.toJson(registeredUser);
                        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(USER_DATA, userJson);
                        editor.putString(JWT, jwt);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: Failed to Connect to Server!! Please try again later.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    // Set a click listener for New user
    public void onClickNewUser(){
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Validate function to check email and password
    private boolean validate() {
        boolean flagEmail = validateEmail();
        boolean flagPassword = validatePassword();
        if (flagEmail && flagPassword) {
            return true;
        }
        return false;
    }

    // Function to validate email
    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
            return false;
        } else {
            emailEditText.setError(null);
            return true;
        }
    }

    // Function to validate password
    private boolean validatePassword() {
        String password = passwordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        } else {
            passwordEditText.setError(null);
            return true;
        }
    }


}
