package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUsActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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
