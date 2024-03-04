package com.example.ocd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView backArrow;
    private LinearLayout preferences;
    private LinearLayout appsShortcut;
    private LinearLayout changePassword;
    private LinearLayout notifications;
    private LinearLayout changeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        intitalize();
        onClickPreferences();
        onClickAppsShortcut();
        onClickChangePassword();
        onClickNotifications();
        onClickChangeLanguage();
        onClickbackArrow();
        onClickBottomNavigationView();


    }

    private void onClickPreferences() {
        preferences.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, PreferencesActivity.class);
            startActivity(intent);
        });
    }

    private void onClickAppsShortcut() {
        appsShortcut.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, AppShortcutActivity.class);
            startActivity(intent);
        });
    }

    private void onClickChangePassword() {
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }

    private void onClickNotifications() {
        notifications.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });
    }

    private void onClickChangeLanguage() {
    }

    private void intitalize() {
        backArrow = findViewById(R.id.backArrow);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        preferences = findViewById(R.id.preferences);
        appsShortcut = findViewById(R.id.apps_shortcut);
        changePassword = findViewById(R.id.change_password);
        notifications = findViewById(R.id.notifications);
        changeLanguage = findViewById(R.id.change_language);
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
                    intent = new Intent(SettingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(SettingActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(SettingActivity.this, ResourcesActivity.class);
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