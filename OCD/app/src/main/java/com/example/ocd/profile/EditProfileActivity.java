package com.example.ocd.profile;

import static com.example.ocd.helper.helperFunctions.isValidDOBFormat;
import static com.example.ocd.helper.helperFunctions.isValidEmail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ocd.HomeActivity;
import com.example.ocd.R;
import com.example.ocd.ResourcesActivity;
import com.example.ocd.TaskActivity;
import com.example.ocd.model.Name;
import com.example.ocd.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;


// todo set the read the value and image from the database and set the values in the edit text

public class EditProfileActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginPref";
    private static final String USER_DATA = "user_data";
    private static final String USER_IMAGE = "user_image";

    private EditText nameEditText, emailEditText, dobEditText, educationEditText, occupationEditText;
    private Spinner genderSpinner;
    private ImageView profileImage, cameraIcon, backArrow;
    private Button btnSaveChanges;

    private User storedUser;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialize();
        loadStoredUserData();
        onClickCameraIcon();
        onClickButtonSaveChanges();
        onClickBottomNavigationView();
        onClickbackArrow();
    }

    // Initialize other EditText fields, ImageView, and Button
    private void initialize() {
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        dobEditText = findViewById(R.id.dobInput);
        genderSpinner = findViewById(R.id.gender);
        educationEditText = findViewById(R.id.educationEditText);
        occupationEditText = findViewById(R.id.occupationEditText);
        profileImage = findViewById(R.id.profileImage);
        cameraIcon = findViewById(R.id.cameraIcon);
        backArrow = findViewById(R.id.backArrow);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);

    }

    // Load stored user data on startup
    private void loadStoredUserData() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String storedUserJson = preferences.getString(USER_DATA, null);
        String storedUserImage = preferences.getString(USER_IMAGE, null);

        if (storedUserJson != null) {
            Gson gson = new Gson();
            storedUser = gson.fromJson(storedUserJson, User.class);

            // Set the retrieved user data in respective EditText fields
            nameEditText.setText(storedUser.getNameString());
            emailEditText.setText(storedUser.getEmail());
            System.out.println("here");
            System.out.println(storedUser.getDob());
            dobEditText.setText(storedUser.getDobString());
            educationEditText.setText(storedUser.getEducation());
            occupationEditText.setText(storedUser.getOccupation());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.gender_options,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(adapter);
            if (!storedUser.getGender().isEmpty()) {
                int position = adapter.getPosition(storedUser.getGender());
                genderSpinner.setSelection(position);
            }

            if (storedUserImage != null) {
                // todo read the image from the database and set the image in the image view
                // Image exists, load it
                // Implement logic to load the image into 'profileImage' ImageView
            } else {
                // Image does not exist, set default image based on gender
                if ("Male".equalsIgnoreCase(storedUser.getGender())) {
                    profileImage.setImageResource(R.drawable.img_male_1);
                } else {
                    profileImage.setImageResource(R.drawable.img_female_1);
                }
            }

        }
    }

    // todo: take the image and update the image on the image icon saved prefferences and call the api to update the image
    private void onClickCameraIcon(){
        cameraIcon.setOnClickListener(view -> {
            // Implement image capture or gallery upload logic
            // Update the 'profileImage' accordingly
        });
    }

    private void onClickButtonSaveChanges() {
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });
    }

    private void updateUserData() {
        // Retrieve updated data from EditText fields
        String updatedName = nameEditText.getText().toString().trim();
        String updatedEmail = emailEditText.getText().toString().trim();
        String updatedDob = dobEditText.getText().toString().trim();
        String updatedGender = genderSpinner.getSelectedItem().toString();
        String updatedEducation = educationEditText.getText().toString().trim();
        String updatedOccupation = occupationEditText.getText().toString().trim();

        // Check if any data has been updated
        if(validate()){
            if (!updatedName.equals(storedUser.getNameString()) || !updatedEmail.equals(storedUser.getEmail()) || !updatedDob.equals(storedUser.getDobString()) || !updatedGender.equals(storedUser.getGender()) || !updatedEducation.equals(storedUser.getEducation()) || !updatedOccupation.equals(storedUser.getOccupation())) {
                storedUser.setName(new Name(updatedName));
                storedUser.setEmail(updatedEmail);
                storedUser.setDob(updatedDob);
                storedUser.setGender(updatedGender);
                storedUser.setEducation(updatedEducation);
                storedUser.setOccupation(updatedOccupation);

                Gson gson = new Gson();
                String updatedUserJson = gson.toJson(storedUser);
                SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(USER_DATA, updatedUserJson);
                editor.apply();
                // todo call the backend api to update the user data
                Toast.makeText(EditProfileActivity.this, "Changes saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditProfileActivity.this, "No changes detected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validate(){
        boolean flagName = validateName();
        boolean flagDOB = validateDOB();
        boolean flagGender = isValidGender();
        boolean flagEmail = validateEmail();
        boolean flagEducation = validateEducation();
        boolean flagOccupation = validateOccupation();

        return flagName && flagDOB && flagGender && flagEmail && flagEducation && flagOccupation;
    }

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

    private void onClickbackArrow(){
        backArrow.setOnClickListener(view -> {
            this.finish();
        });
    }


    private void onClickBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(EditProfileActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(EditProfileActivity.this, ResourcesActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_profile:
                    return true;
            }
            return false;
        });
    }
}
