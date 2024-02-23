package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView firstImage, secondImage;
    private CardView circularCardView;
    private TextView txtName, genderAndDOB, editProfile, setting, support, aboutUs, appGuide, logOut;
    BottomNavigationView bottomNavigationView;

        // get gender based on gender set image if img is not set
//    String formattedString = getString(R.string.gender_and_dob, gender, dob);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initialize();

        // You can perform further configurations or set listeners as needed
        // For example, setting text for TextViews
        txtName.setText(getString(R.string.lbl_name)); // Replace with actual data
        genderAndDOB.setText(getString(R.string.gender_and_dob, "Male", "01/01/1990")); // Replace with actual data
    }

    private void initialize() {
        firstImage = findViewById(R.id.firstImage);
        secondImage = findViewById(R.id.secondImage);
        circularCardView = findViewById(R.id.circularCardView);
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

    private void onClickBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
//                    selectedFragment = new HomeFragment();
                    return true;
                case R.id.menu_task:
                    Toast.makeText(ProfileActivity.this, "My Plants Clicked", Toast.LENGTH_SHORT).show();
//                    selectedFragment = new TaskFragment();
                    return true;
                case R.id.menu_resource:
//                    selectedFragment = new ResourceFragment();
                    return true;
                case R.id.menu_profile:
                    return true;
            }
            return false;
        });
    }
}
