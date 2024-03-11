package com.example.ocd.profile;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ocd.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText txtEnterPassword;
    private EditText txtNewPassword;
    private EditText txtConfirmPassword;
    private ImageView imgEyePreviousPassword;
    private ImageView imgEyeNewPassword;
    private ImageView imgEyeConfirmPassowrd;
    private FrameLayout btnShowPasswordPreviousPassowrd;
    private FrameLayout btnShowPasswordNewPassowrd;
    private FrameLayout btnShowPasswordConfirmPassowrd;
    private Button btnSaveChanges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialize();
        onClickShowHidePassword();
        onClickBtnSaveChanges();
    }

    private void initialize() {
        txtEnterPassword = findViewById(R.id.txtEnterPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        imgEyePreviousPassword = findViewById(R.id.imgEyePreviousPassword);
        imgEyeNewPassword = findViewById(R.id.imgEyeNewPassword);
        imgEyeConfirmPassowrd = findViewById(R.id.imgEyeConfirmPassowrd);
        btnShowPasswordPreviousPassowrd = findViewById(R.id.btnShowPasswordPreviousPassowrd);
        btnShowPasswordNewPassowrd = findViewById(R.id.btnShowPasswordNewPassowrd);
        btnShowPasswordConfirmPassowrd = findViewById(R.id.btnShowPasswordConfirmPassowrd);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
    }

    private void onClickBtnSaveChanges(){
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo validate password with the saved data and update the passoword
                if (txtNewPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                    // Do something if passwords match
                } else {
                    // Do something if passwords do not match
                }
            }
        });
    }


    public void onClickShowHidePassword() {
        btnShowPasswordPreviousPassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassword();
            }
        });
        btnShowPasswordNewPassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassword();
            }
        });
        btnShowPasswordConfirmPassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassword();
            }
        });
    }

    private void showHidePassword(){
        if (txtNewPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            // If password is currently hidden, show it
            txtEnterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            txtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            txtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgEyePreviousPassword.setImageResource(R.drawable.img_eye);
            imgEyeNewPassword.setImageResource(R.drawable.img_eye);
            imgEyeConfirmPassowrd.setImageResource(R.drawable.img_eye);
        } else {
            // If password is currently shown, hide it
            txtEnterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            txtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            txtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgEyePreviousPassword.setImageResource(R.drawable.img_eye_close);
            imgEyeNewPassword.setImageResource(R.drawable.img_eye_close);
            imgEyeConfirmPassowrd.setImageResource(R.drawable.img_eye_close);
        }
    }
}