package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intitalize();
        onClickBottomNavigationView();
    }
    private void intitalize() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }



    private void onClickBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
//                    selectedFragment = new HomeFragment();
                    return true;
                case R.id.menu_task:
                    Toast.makeText(HomeActivity.this, "TASK clicked", Toast.LENGTH_SHORT).show();
//                    selectedFragment = new TaskFragment();
                    return true;
                case R.id.menu_resource:
//                    selectedFragment = new ResourceFragment();
                    return true;
                case R.id.menu_profile:
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        });
    }

}