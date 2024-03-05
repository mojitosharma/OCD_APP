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
import com.example.ocd.TaskActivity;
import com.example.ocd.adapter.JournalListAdapter;


public class JournalEntryObsessionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText txtInputTrigger, txtInputObsession;
    private Button btnProceed;
    private ArrayList<String> obsessionList;
    private JournalListAdapter customAdapter;
    private ImageView backArrow, crossButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry_obsession);

        initialize();
        getObsessionList();
        setRecyclerView();
        onClickBackArrow();
        onClickCrossButton();
        onClickBtnProceed();
    }


    private void initialize() {
        backArrow = findViewById(R.id.backArrow);
        crossButton = findViewById(R.id.crossButton);
        txtInputTrigger = findViewById(R.id.txtInputTrigger);
        txtInputObsession = findViewById(R.id.txtInputObsession);
        txtInputObsession.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.obsessionListRecycler);
        btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
    }

    private void getObsessionList() {
        // todo: add logic to fetch the values from the remote database
        obsessionList = new ArrayList<>();
        obsessionList.add("Contamination");
        obsessionList.add("Losing Control");
        obsessionList.add("Harm");
        obsessionList.add("Unwanted Sexual Thoughts");
        obsessionList.add("Perfectionism");
        obsessionList.add("Religious Obsessions");
        obsessionList.add("other");
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        customAdapter = new JournalListAdapter(obsessionList, txtInputObsession);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

//    private void setTextWatcher() {
//        txtInputTrigger.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                // Not needed in this case, but required to implement the TextWatcher interface
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                // Not needed in this case, but required to implement the TextWatcher interface
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String trigger = txtInputTrigger.getText().toString().trim();
//                String obsession = customAdapter.getSelectedOption();
//                boolean isOptionSelected = customAdapter.isOptionSelected();
//                String selectedObsession = customAdapter.getSelectedOption();
//                if(!trigger.isEmpty() && isOptionSelected && (selectedObsession.equals("other") || !obsession.isEmpty())){
//                btnProceed.setEnabled(true);
//                btnProceed.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
//                }
//                else {
//                    btnProceed.setEnabled(false);
//                    btnProceed.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5);
//                }
//            }
//        });
//    }

    private void onClickBackArrow(){
        backArrow.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void onClickCrossButton() {
        crossButton.setOnClickListener(view -> {
            Intent intent = new Intent(JournalEntryObsessionActivity.this, TaskActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void onClickBtnProceed() {
        btnProceed.setOnClickListener(view -> {
            String trigger = txtInputTrigger.getText().toString().trim();
            String obsession = txtInputObsession.getText().toString().trim();
            String selectedObsession = customAdapter.getSelectedOption();
            if(!trigger.isEmpty() && !selectedObsession.isEmpty()){
                if(selectedObsession.equals("other")) {
                    if (obsession.isEmpty()) {
                        Toast.makeText(JournalEntryObsessionActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(JournalEntryObsessionActivity.this, JournalEntryCompulsionActivity.class);
                        intent.putExtra("trigger", trigger);
                        intent.putExtra("obsession", obsession);
                        startActivity(intent);
                    }
                }
                else {
                    Intent intent = new Intent(JournalEntryObsessionActivity.this, JournalEntryCompulsionActivity.class);
                    intent.putExtra("trigger", trigger);
                    intent.putExtra("obsession", selectedObsession);
                    startActivity(intent);
                }
            }
            else{
                Toast.makeText(JournalEntryObsessionActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
