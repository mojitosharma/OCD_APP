package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ocd.JournalEntry.JournalEntryCompulsionActivity;
import com.example.ocd.JournalEntry.TaskMonthlyActivity;
import com.example.ocd.profile.ProfileActivity;
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
            Intent intent;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    return true;
                case R.id.menu_task:
                    intent = new Intent(HomeActivity.this, TaskMonthlyActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(HomeActivity.this, JournalEntryCompulsionActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_profile:
                    intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        });
    }

}