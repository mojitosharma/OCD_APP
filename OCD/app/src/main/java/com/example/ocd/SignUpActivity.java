package com.example.ocd;

import static com.example.ocd.helper.helperFunctions.isValidDOBFormat;
import static com.example.ocd.helper.helperFunctions.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, dobEditText, emailEditText, passwordEditText, confirmPasswordEditText, educationEditText, occupationEditText;
    private Spinner genderSpinner;
    private ImageView imgShowPassword, imgShowConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Initialize();
    }

    // Initialize views
    public void Initialize(){

        nameEditText = findViewById(R.id.name);
        dobEditText = findViewById(R.id.dateOfBirth);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.txtEnterPassword);
        confirmPasswordEditText = findViewById(R.id.txtConfirmPassword);
        educationEditText = findViewById(R.id.education);
        occupationEditText = findViewById(R.id.lbl_occupation);
        imgShowPassword = findViewById(R.id.imgShowPassword);
        imgShowConfirmPassword = findViewById(R.id.imgShowConfirmPassword);
        genderSpinner = findViewById(R.id.gender);
    }

    public void onClickRegister(View view){
        if (validate()) {
            // Move to the next activity
//                    Intent intent = new Intent(LoginActivity.this, NextActivity.class);
//                    startActivity(intent);
            finish(); // optional, to finish the current activity
        }
    }

    public void showHidePassword(View view) {
        if (passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            // If password is currently hidden, show it
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgShowConfirmPassword.setImageResource(R.drawable.img_eye);
            imgShowPassword.setImageResource(R.drawable.img_eye);
        } else {
            // If password is currently shown, hide it
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgShowConfirmPassword.setImageResource(R.drawable.img_eye_close);
            imgShowPassword.setImageResource(R.drawable.img_eye_close);
        }
    }

    private boolean validate() {
        boolean flagName = validateName();
        boolean flagDOB = validateDOB();
        boolean flagGender = isValidGender();
        boolean flagEmail = validateEmail();
        boolean flagPassword = validatePassword();
        boolean flagConfirmPassword = validateConfirmPassword();
        boolean flagEducation = validateEducation();
        boolean flagOccupation = validateOccupation();

        return flagName && flagDOB && flagGender && flagEmail && flagPassword && flagConfirmPassword && flagEducation && flagOccupation;
    }

    // Validate name
    private boolean validateName() {
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameEditText.setError("Name is Required");
            return false;
        }
        else {
            nameEditText.setError(null);
            return true;
        }
    }

    // Validate date of birth
    private boolean validateDOB() {
        String dob = dobEditText.getText().toString().trim();
        if (dob.isEmpty()) {
            dobEditText.setError("Date of Birth is Required");
            return false;
        } else if(!isValidDOBFormat(dob)){
            dobEditText.setError("Correct Format is DD/MM/YYYY");
            return false;
        } else {
            dobEditText.setError(null);
            return true;
        }
    }

    private boolean isValidGender() {
        String selectedGender = genderSpinner.getSelectedItem().toString();

        if ("Gender".equals(selectedGender)) {
            Toast.makeText(getApplicationContext(), "Invalid gender selected!", Toast.LENGTH_SHORT).show();
            genderSpinner.setSelection(0);
            return false;
        }

        return true;
    }

    // Validate email
    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (!isValidEmail(email)) {
            emailEditText.setError("Email is Required");
            return false;
        }else {
            emailEditText.setError(null);
            return true;
        }
    }

    // Validate password
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

    // Validate confirm password
    private boolean validateConfirmPassword() {
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Password is required");
            return false;
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            confirmPasswordEditText.setError("Password does not match");
            return false;
        } else {
            confirmPasswordEditText.setError(null);
            return true;
        }
    }

    // Validate education
    private boolean validateEducation() {
        String education = educationEditText.getText().toString().trim();
        if (education.isEmpty()) {
            educationEditText.setError("Education is Required");
            return false;
        }else {
            educationEditText.setError(null);
            return true;
        }
    }

    // Validate occupation
    private boolean validateOccupation() {
        String occupation = occupationEditText.getText().toString().trim();
        if (occupation.isEmpty()) {
            occupationEditText.setError("Occupation is Required");
            return false;
        } else {
            occupationEditText.setError(null);
            return true;
        }
    }



}
