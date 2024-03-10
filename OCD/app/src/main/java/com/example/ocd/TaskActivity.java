package com.example.ocd;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ocd.JournalEntry.JournalEntryObsessionActivity;


public class TaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = new Intent(this, JournalEntryObsessionActivity.class);
        startActivity(intent);
        finish();
    }
}
