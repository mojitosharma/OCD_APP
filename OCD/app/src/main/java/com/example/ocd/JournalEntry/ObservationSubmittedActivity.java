package com.example.ocd.JournalEntry;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ocd.R;

public class ObservationSubmittedActivity extends AppCompatActivity {

    private Button btnLetsMoveMyJourneyAhead, btnViewMyJournalEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_submitted);

        initialize();
        onClickBtnMoveMyJourneyAhead();
        onClickBtnViewMyJournalEntry();

    }

    private void initialize() {
        btnLetsMoveMyJourneyAhead = findViewById(R.id.btnLetsMoveMyJourneyAhead);
        btnViewMyJournalEntry = findViewById(R.id.btnViewMyJournalEntry);
    }

    private void onClickBtnMoveMyJourneyAhead() {
    }

    private void onClickBtnViewMyJournalEntry() {
    }
}