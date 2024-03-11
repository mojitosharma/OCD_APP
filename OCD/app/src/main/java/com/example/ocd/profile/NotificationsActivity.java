package com.example.ocd.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ocd.HomeActivity;
import com.example.ocd.R;
import com.example.ocd.ResourcesActivity;
import com.example.ocd.TaskWeeklyActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView backArrow;
    private CheckBox checkBoxtxtAllowPushNotifications;
    private CheckBox checkBoxAllowpictureinPicture;
    private CheckBox checkBoxAllowTaskReminder;
    private CheckBox checkBoxAllowAppUpdateNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        intitalize();
        onClickbackArrow();
        onClickCheckBoxAllowAppUpdateNotification();
        onClickCheckBoxAllowpictureinPicture();
        onClickCheckBoxAllowTaskReminder();
        onClickCheckBoxAllowPushNotifications();
        onClickBottomNavigationView();

    }

    private void intitalize() {
        backArrow = findViewById(R.id.backArrow);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        checkBoxAllowAppUpdateNotification = findViewById(R.id.checkBoxAllowAppUpdateNotification);
        checkBoxAllowpictureinPicture = findViewById(R.id.checkBoxAllowpictureinPicture);
        checkBoxtxtAllowPushNotifications = findViewById(R.id.checkBoxtxtAllowPushNotifications);
        checkBoxAllowTaskReminder = findViewById(R.id.checkBoxAllowTaskReminder);

    }

    private void onClickCheckBoxAllowAppUpdateNotification() {
    }

    private void onClickCheckBoxAllowpictureinPicture() {
    }

    private void onClickCheckBoxAllowTaskReminder() {
    }

    private void onClickCheckBoxAllowPushNotifications() {
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
                    intent = new Intent(NotificationsActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(NotificationsActivity.this, TaskWeeklyActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(NotificationsActivity.this, ResourcesActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_profile:
//                    selectedFragment = new ProfileFragment();
                    return true;
            }
            return false;
        });
    }
}