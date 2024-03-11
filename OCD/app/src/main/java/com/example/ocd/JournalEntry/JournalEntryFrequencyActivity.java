package com.example.ocd.JournalEntry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocd.R;
import com.example.ocd.TaskWeeklyActivity;
import com.example.ocd.adapter.JournalFrequencyAdapter;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class JournalEntryFrequencyActivity extends AppCompatActivity {


    private RecyclerView compulsionFrequencyListRecycler, compulsionDurationListRecycler;
    private Button btnSubmit;
    private ArrayList<String> frequencyList, timeList;
    private JournalFrequencyAdapter customAdapterFrequency, customAdapterTime;
    private ImageView backArrow, crossButton;
    private Slider sliderStressMeter;
    private TextView txtStressValue;
    private String trigger, obsession, compulsionName, compulsion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry_frequency);

        initialize();
        readValues();
        getTimeAndFrequencyList();
        setRecyclerView();
        onClickBackArrow();
        onClickCrossButton();
        onClickSliderStressMeter();
        onClickBtnSubmit();

    }


    private void initialize() {
        backArrow = findViewById(R.id.backArrow);
        crossButton = findViewById(R.id.crossButton);
        compulsionFrequencyListRecycler = findViewById(R.id.compulsionFrequencyListRecycler);
        compulsionDurationListRecycler = findViewById(R.id.compulsionDurationListRecycler);
        sliderStressMeter = findViewById(R.id.sliderStressMeter);
        txtStressValue = findViewById(R.id.txtStressValue);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
    }

    private void readValues() {
        Intent intent = getIntent();
        if (intent.hasExtra("trigger") && intent.hasExtra("obsession")) {
            trigger = intent.getStringExtra("trigger");
            obsession = intent.getStringExtra("obsession");
            compulsionName = intent.getStringExtra("compulsionName");
            compulsion = intent.getStringExtra("compulsion");
        }
    }

    private void getTimeAndFrequencyList() {
        // todo: add logic to fetch the values from the remote database
        frequencyList = new ArrayList<>();
        frequencyList.add("0-5 times");
        frequencyList.add("6-10 times");
        frequencyList.add("11-15 times");
        frequencyList.add("16-20 times");
        frequencyList.add("21-25 times");
        frequencyList.add("More than 25 times");
        timeList = new ArrayList<>();
        timeList.add("0-5 minutes");
        timeList.add("6-10 minutes");
        timeList.add("11-15 minutes");
        timeList.add("16-20 minutes");
        timeList.add("21-25 minutes");
        timeList.add("More than 25 minutes");
    }

    private void setRecyclerView(){
        compulsionFrequencyListRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        customAdapterFrequency = new JournalFrequencyAdapter(frequencyList);
        compulsionFrequencyListRecycler.setAdapter(customAdapterFrequency);
        compulsionFrequencyListRecycler.setItemAnimator(new DefaultItemAnimator());

        compulsionDurationListRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        customAdapterTime = new JournalFrequencyAdapter(timeList);
        compulsionDurationListRecycler.setAdapter(customAdapterTime);
        compulsionDurationListRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void onClickBackArrow(){
        backArrow.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void onClickCrossButton() {
        crossButton.setOnClickListener(view -> {
            Intent intent = new Intent(JournalEntryFrequencyActivity.this, TaskWeeklyActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void onClickSliderStressMeter() {
        sliderStressMeter.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                txtStressValue.setText(String.valueOf(value));
            }
        });

    }

    private void onClickBtnSubmit() {
        btnSubmit.setOnClickListener(view -> {
            String time = customAdapterTime.getSelectedOption();
            String frequency = customAdapterFrequency.getSelectedOption();
            String stressValue = txtStressValue.getText().toString();
            if(!time.isEmpty() && !frequency.isEmpty() && !stressValue.equals("NIL")){
                // todo: add logic to send the values to the remote database
                Intent intent = new Intent(JournalEntryFrequencyActivity.this, ObservationSubmittedActivity.class);
                startActivity(intent);
                this.finish();
            }
            else{
                Toast.makeText(JournalEntryFrequencyActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
