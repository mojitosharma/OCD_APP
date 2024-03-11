package com.example.ocd.JournalEntry;

import static com.example.ocd.CalendarUtils.daysInMonthArray;
import static com.example.ocd.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocd.CalendarUtils;
import com.example.ocd.R;
import com.example.ocd.TaskWeeklyActivity;
import com.example.ocd.adapter.CalendarAdapter;

import java.time.LocalDate;
import java.util.ArrayList;



public class TaskMonthlyActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button btnWeek;
    private Button btnDay;
    private Button previousMonthBtn;
    private Button nextMonthBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_monthly);
        initialize();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        onCLickBtnDay();
        onClickBtnWeek();
        onClickPreviousMonth();
        onClickNextMonth();
    }


    private void initialize() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        btnWeek = findViewById(R.id.btnWeek);
        btnDay = findViewById(R.id.btnDay);
        previousMonthBtn = findViewById(R.id.previousMonthBtn);
        nextMonthBtn = findViewById(R.id.nextMonthBtn);
    }

    private void onCLickBtnDay() {
        btnDay.setOnClickListener(view -> {
            startActivity(new Intent(this, JournalEntryCompulsionActivity.class));
            finish();
        });
    }

    private void onClickBtnWeek() {
        btnWeek.setOnClickListener(view -> {
            startActivity(new Intent(this, TaskWeeklyActivity.class));
            finish();
        });
    }

    private void onClickPreviousMonth() {
        previousMonthBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1).withDayOfMonth(1);
            setMonthView();
        });
    }

    private void onClickNextMonth() {
        nextMonthBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1).withDayOfMonth(1);
            setMonthView();
        });
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

}








