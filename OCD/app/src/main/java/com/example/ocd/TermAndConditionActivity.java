package com.example.ocd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TermAndConditionActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private Button btnContinue;
    private TextView tvTermsAndConditions;
    private String name;
    private String dob;
    private String gender;
    private String email;
    private String education;
    private String occupation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        readingData();
        initialize();
        loadTermsAndConditions();
        onClickCheckBox();
        onClickBtnContinue();
    }

    private void readingData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        dob = intent.getStringExtra("DOB");
        gender = intent.getStringExtra("GENDER");
        email = intent.getStringExtra("EMAIL");
        education = intent.getStringExtra("EDUCATION");
        occupation = intent.getStringExtra("OCCUPATION");
    }

    private void initialize() {
        checkBox = findViewById(R.id.checkBox);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
    }

    private void onClickCheckBox() {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnContinue.setEnabled(isChecked);
            if (isChecked) {
                btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
            } else{
                btnContinue.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
            }
        });
    }

    private void onClickBtnContinue() {
        btnContinue.setOnClickListener(v -> {
            // Handle continue button click
            // Add your logic here
        });
    }





    private void loadTermsAndConditions() {
        try {
            // Read terms and conditions from a file
            InputStream inputStream = getResources().openRawResource(R.raw.term_and_condition);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            inputStream.close();

            // Convert HTML content to Spanned
            Spanned spannedHtml = Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT);

            // Set the loaded terms and conditions to the TextView
            tvTermsAndConditions.setText(spannedHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
