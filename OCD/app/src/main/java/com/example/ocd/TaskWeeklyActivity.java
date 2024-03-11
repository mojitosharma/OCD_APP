package com.example.ocd;

import static com.example.ocd.CalendarUtils.daysInWeekArray;
import static com.example.ocd.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocd.JournalEntry.Event;
import com.example.ocd.JournalEntry.JournalEntryCompulsionActivity;
import com.example.ocd.JournalEntry.TaskDailyActivity;
import com.example.ocd.JournalEntry.TaskMonthlyActivity;
import com.example.ocd.adapter.CalendarAdapter;
import com.example.ocd.adapter.EventAdapter;

import java.time.LocalDate;
import java.util.ArrayList;


public class TaskWeeklyActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private Button btnMonth;
    private Button btnDay;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button previousWeekBtn;
    private Button nextWeekBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_weekly);
        initialize();
        onCLickBtnDay();
        onClickBtnMonth();
        onClickPreviousWeekBtn();
        onClickNextWeekBtn();
        setWeekView();
    }

    private void initialize() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        previousWeekBtn = findViewById(R.id.previousWeekBtn);
        nextWeekBtn = findViewById(R.id.nextWeekBtn);
        btnMonth = findViewById(R.id.btnMonth);
        btnDay = findViewById(R.id.btnDay);
    }

    private void onClickPreviousWeekBtn() {
        previousWeekBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });
    }
    private void onClickNextWeekBtn() {
        nextWeekBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        });
    }

    private void onCLickBtnDay() {
        btnDay.setOnClickListener(view -> {
            startActivity(new Intent(this, TaskDailyActivity.class));
            finish();
        });
    }

    private void onClickBtnMonth() {
        btnMonth.setOnClickListener(view -> {
            startActivity(new Intent(this, TaskMonthlyActivity.class));
            finish();
        });
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }



    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view) {
//            startActivity(new Intent(this, EventEditActivity.class));
    }
}

