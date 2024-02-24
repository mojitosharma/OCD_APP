package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// TODO: set image based on gender. check first if the image exist or not.

public class AboutUsActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        intitalize();
        onClickbackArrow();
        onClickBottomNavigationView();


    }

    private void intitalize() {
        backArrow = findViewById(R.id.backArrow);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
    }

    private void onClickbackArrow(){
        backArrow.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void onClickBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
//                    selectedFragment = new HomeFragment();
                    return true;
                case R.id.menu_task:
                    Toast.makeText(AboutUsActivity.this, "My Plants Clicked", Toast.LENGTH_SHORT).show();
//                    selectedFragment = new TaskFragment();
                    return true;
                case R.id.menu_resource:
//                    selectedFragment = new ResourceFragment();
                    return true;
                case R.id.menu_profile:
//                    selectedFragment = new ProfileFragment();
                    return true;
            }
            return false;
        });
    }


}
