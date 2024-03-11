package com.example.ocd.JournalEntry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.ocd.R;
import com.example.ocd.TaskWeeklyActivity;
import com.example.ocd.adapter.JournalListAdapter;


public class JournalEntryCompulsionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText txtInputCompulsionName, txtInputCompulsion;
    private Button btnProceed;
    private ArrayList<String> compulsionList;
    private JournalListAdapter customAdapter;
    private ImageView backArrow, crossButton;
    private String trigger, obsession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry_compulsion);

        initialize();
        readValues();
        getCompulsionList();
        setRecyclerView();
        onClickBackArrow();
        onClickCrossButton();
        onClickBtnProceed();
    }


    private void initialize() {
        backArrow = findViewById(R.id.backArrow);
        crossButton = findViewById(R.id.crossButton);
        txtInputCompulsionName = findViewById(R.id.txtInputCompulsionName);
        txtInputCompulsion = findViewById(R.id.txtInputCompulsion);
        txtInputCompulsion.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.compulsionListRecycler);
        btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
    }

    private void readValues() {
            Intent intent = getIntent();
            if (intent.hasExtra("trigger") && intent.hasExtra("obsession")) {
                trigger = intent.getStringExtra("trigger");
                obsession = intent.getStringExtra("obsession");
            }
    }

    private void getCompulsionList() {
        // todo: add logic to fetch the values from the remote database
        compulsionList = new ArrayList<>();
        compulsionList.add("Washing and cleaning");
        compulsionList.add("Checking");
        compulsionList.add("Repeating");
        compulsionList.add("Mental Compulsions");
        compulsionList.add("other");
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        customAdapter = new JournalListAdapter(compulsionList, txtInputCompulsion);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void onClickBackArrow(){
        backArrow.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void onClickCrossButton() {
        crossButton.setOnClickListener(view -> {
            Intent intent = new Intent(JournalEntryCompulsionActivity.this, TaskWeeklyActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void onClickBtnProceed() {
        btnProceed.setOnClickListener(view -> {
            String compulsionName = txtInputCompulsionName.getText().toString().trim();
            String compulsion = txtInputCompulsion.getText().toString().trim();
            String selectedCompulsion = customAdapter.getSelectedOption();
            if(!compulsionName.isEmpty() && !selectedCompulsion.isEmpty()){
                if(selectedCompulsion.equals("other")) {
                    if (compulsion.isEmpty()) {
                        Toast.makeText(JournalEntryCompulsionActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(JournalEntryCompulsionActivity.this, JournalEntryFrequencyActivity.class);
                        intent.putExtra("trigger", trigger);
                        intent.putExtra("obsession", obsession);
                        intent.putExtra("compulsionName", compulsionName);
                        intent.putExtra("compulsion", compulsion);
                        startActivity(intent);
                    }
                }
                else {
                    Intent intent = new Intent(JournalEntryCompulsionActivity.this, JournalEntryFrequencyActivity.class);
                    intent.putExtra("trigger", compulsionName);
                    intent.putExtra("obsession", obsession);
                    intent.putExtra("compulsion", selectedCompulsion);
                    intent.putExtra("compulsionName", compulsionName);
                    startActivity(intent);
                }
            }
            else{
                Toast.makeText(JournalEntryCompulsionActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
