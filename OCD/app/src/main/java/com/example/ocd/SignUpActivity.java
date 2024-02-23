package com.example.ocd;

import static com.example.ocd.helper.helperFunctions.isPasswordValid;
import static com.example.ocd.helper.helperFunctions.isValidDOBFormat;
import static com.example.ocd.helper.helperFunctions.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, dobEditText, emailEditText, passwordEditText, confirmPasswordEditText, educationEditText, occupationEditText;
    private Spinner genderSpinner;
    private ImageView imgShowPassword, imgShowConfirmPassword;
    private FrameLayout btnShowPassword, btnShowConfirmPassword;
    private Button btnRegister;
    private TextView btnAlreadyExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Initialize();
        readValues();
        onClickRegister();
        onClickShowHidePassword();
        onClickLogin();
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
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnShowConfirmPassword = findViewById(R.id.btnShowConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnAlreadyExist = findViewById(R.id.btnAlreadyExist);
    }

    private void readValues() {
        Intent intent = getIntent();
        if (intent.hasExtra("NAME")) {
            // Data is received from VerifyOTPActivity, update your UI or handle as needed
            String name = intent.getStringExtra("NAME");
            String dob = intent.getStringExtra("DOB");
            String gender = intent.getStringExtra("GENDER");
            String email = intent.getStringExtra("EMAIL");
            String education = intent.getStringExtra("EDUCATION");
            String occupation = intent.getStringExtra("OCCUPATION");

            // Update your UI or perform any other actions with the received data
            nameEditText.setText(name);
            emailEditText.setText(email);
            dobEditText.setText(dob);
            educationEditText.setText(education);
            occupationEditText.setText(occupation);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.gender_options,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(adapter);
            if (intent.hasExtra("GENDER")) {
                int position = adapter.getPosition(gender);
                genderSpinner.setSelection(position);
            }
        }
    }

    public void onClickRegister(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String name = nameEditText.getText().toString().trim();
                    String dob = dobEditText.getText().toString().trim();
                    String selectedGender = genderSpinner.getSelectedItem().toString();
                    String email = emailEditText.getText().toString().trim();
                    String education = educationEditText.getText().toString().trim();
                    String occupation = occupationEditText.getText().toString().trim();
                    // Create an Intent to start the next activity (VerifyOTPActivity)
                    Intent intent = new Intent(SignUpActivity.this, VerifyOTPActivity.class);
                    // Pass data to the next activity
                    intent.putExtra("NAME", name);
                    intent.putExtra("DOB", dob);
                    intent.putExtra("GENDER", selectedGender);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("EDUCATION", education);
                    intent.putExtra("OCCUPATION", occupation);

                    // Start the next activity
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void onClickShowHidePassword() {
        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassword();
            }
        });
        btnShowConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassword();
            }
        });
    }

    private void onClickLogin(){
        btnAlreadyExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showHidePassword(){
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
        if (email.isEmpty()) {
            emailEditText.setError("Email is Required");
            return false;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
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
        } else if (!isPasswordValid(password)) {
            passwordEditText.setError("Must contain at-least 8 characters and\nShould Contain at-least one: \n\tcapital letter\n\tsmallcase letter\n\tspecial character");
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
        } else if (!isPasswordValid(password)) {
            passwordEditText.setError("Must contain at-least 8 characters and\nShould Contain at-least one: \n\tcapital letter\n\tsmallcase letter\n\tspecial character");
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
