package com.example.ocd.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocd.HomeActivity;
import com.example.ocd.MainActivity;
import com.example.ocd.R;
import com.example.ocd.ResourcesActivity;
import com.example.ocd.TaskActivity;
import com.example.ocd.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ProfileActivity extends AppCompatActivity {
    private static final String PREF_NAME = "LoginPref";
    private static final String USER_DATA = "user_data";
    private static final String USER_IMAGE = "user_image";
    private ImageView profileImage;
    private TextView txtName, genderAndDOB;
    private LinearLayout editProfile, setting, support, aboutUs, appGuide, logOut;
    BottomNavigationView bottomNavigationView;

        // get gender based on gender set image if img is not set
//    String formattedString = getString(R.string.gender_and_dob, gender, dob);
    // TODO: set image based on gender. check first if the image exist or not.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialize();
        loadStoredUserData();
        onClickBottomNavigationView();
        onClickEditProfile();
        onCLickSetting();
        onClickSupport();
        onClickAboutUs();
        onClickAppGuide();
        onClickLogOut();
        onClickAboutUs();

    }


    private void initialize() {
        profileImage = findViewById(R.id.profileImage);
        txtName = findViewById(R.id.txtName);
        genderAndDOB = findViewById(R.id.genderAndDOB);
        editProfile = findViewById(R.id.edit_profile);
        setting = findViewById(R.id.setting);
        support = findViewById(R.id.support);
        aboutUs = findViewById(R.id.about_us);
        appGuide = findViewById(R.id.app_guide);
        logOut = findViewById(R.id.log_out);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
    }

    private void loadStoredUserData(){
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String storedUserJson = preferences.getString(USER_DATA, null);
        String storedUserImage = preferences.getString(USER_IMAGE, null);
        if (storedUserJson != null) {
            try {
                Gson gson = new Gson();
                User storedUser = gson.fromJson(storedUserJson, User.class);

                // Set the retrieved user data in respective EditText fields
                txtName.setText(storedUser.getNameString());
                genderAndDOB.setText(getString(R.string.gender_and_dob, storedUser.getGender(), storedUser.getDobString()));
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
            } catch (JsonSyntaxException e) {
                // Handle JSON parsing error
                e.printStackTrace();
                Toast.makeText(this, "Error loading user data", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Login error !!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void onClickEditProfile(){
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }

    private void onCLickSetting() {
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void onClickSupport() {
        support.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SupportActivity.class);
            startActivity(intent);
        });
    }

    private void onClickAboutUs(){
        aboutUs.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });
    }

    private void onClickAppGuide() {
        appGuide.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AppGuideActivity.class);
            startActivity(intent);
        });
    }

    private void onClickLogOut() {
        logOut.setOnClickListener(v -> {
            showConfirmDialog();
        });
    }

    private void showConfirmDialog() {
        // Create and show a dialog with timeout error message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LogOut")
                .setMessage("Do you wish to continue?")
                .setPositiveButton("confirm", (dialog, which) -> {
                    SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(USER_DATA);
                    editor.remove(USER_IMAGE);
                    editor.apply();

                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Go Back", (dialog, which) -> {
                    return;
                })
                .setCancelable(false);

        // Show the created dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onClickBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(ProfileActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(ProfileActivity.this, ResourcesActivity.class);
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
