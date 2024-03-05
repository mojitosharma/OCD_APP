package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


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
            Intent intent;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    intent = new Intent(AboutUsActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_task:
                    intent = new Intent(AboutUsActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_resource:
                    intent = new Intent(AboutUsActivity.this, ResourcesActivity.class);
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
