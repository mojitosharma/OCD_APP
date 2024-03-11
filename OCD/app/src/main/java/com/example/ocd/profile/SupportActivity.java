package com.example.ocd.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ocd.HomeActivity;
import com.example.ocd.R;
import com.example.ocd.ResourcesActivity;
import com.example.ocd.TaskWeeklyActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SupportActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView backArrow;
    private LinearLayout llContactLiveChat, llSendUsEmail, llReportAProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        intitalize();
//        onClickLlContactLiveChat();
        onClickLlSendUsEmail();
        onClickLlReportAProblem();
        onClickbackArrow();
        onClickBottomNavigationView();
    }

    private void intitalize() {
        backArrow = findViewById(R.id.backArrow);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
//        llContactLiveChat = findViewById(R.id.llContactLiveChat);
        llSendUsEmail = findViewById(R.id.llSendUsEmail);
        llReportAProblem = findViewById(R.id.llReportAProblem);
    }

//    private void onClickLlContactLiveChat() {
//        llContactLiveChat.setOnClickListener(view -> {
//
//        });
//    }

    private void onClickLlSendUsEmail() {
        llSendUsEmail.setOnClickListener(view -> {
            String email = "ocdapplication@gmail.com";
            String subject = "OCD Support";

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                Toast.makeText(this, "Opening email app", Toast.LENGTH_SHORT).show();
                startActivity(emailIntent);
            } else {
                // Handle the case where no email app is available
                Toast.makeText(this, "No email app installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickLlReportAProblem() {
        llReportAProblem.setOnClickListener(view -> {

        });
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
                    intent = new Intent(SupportActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(SupportActivity.this, TaskWeeklyActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(SupportActivity.this, ResourcesActivity.class);
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