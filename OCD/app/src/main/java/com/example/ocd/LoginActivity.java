package com.example.ocd;

import static com.example.ocd.helper.helperFunctions.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private ImageView eyeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialize();
    }

    // Initialize views
    private void Initialize(){
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.txtEnterPassword);
        eyeImageView = findViewById(R.id.imgEye);
    }

    // Set a click for show hide password
    public void showHidePassword(View view) {
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

    // Set a click listener for the login button
    public void onClickLogin(View view){
        if (validate()) {
            // Move to the next activity
//                    Intent intent = new Intent(LoginActivity.this, NextActivity.class);
//                    startActivity(intent);
            finish(); // optional, to finish the current activity
        }
    }

    // Set a click listener for New user
    public void newUser(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
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
