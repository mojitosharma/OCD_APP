package com.example.ocd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.ocd.adapter.ObsessionListAdapter;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> obsessionList;
    private TaskActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initialize();
        setRecyclerView();
    }

    private void initialize() {
        recyclerView = (RecyclerView) findViewById(R.id.obsessionListRecycler);
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
        ObsessionListAdapter customAdapter = new ObsessionListAdapter(obsessionList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}