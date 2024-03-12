package com.example.ocd.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.ocd.CalendarUtils;
import com.example.ocd.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ArrayList<LocalDate> days;
        public final View parentView;
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;
        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
            super(itemView);
            parentView = itemView.findViewById(R.id.parentView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
            this.days = days;
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
        }
    }


    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.145);
        else // week view
            layoutParams.height = (int) (parent.getHeight()*0.85);

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);
        if (date == null) {
            holder.dayOfMonth.setText("");
            holder.dayOfMonth.setTextColor(Color.BLACK);
            holder.parentView.setBackgroundResource(0);
        } else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            if (date.equals(CalendarUtils.selectedDate)) {
                // Selected state
                holder.dayOfMonth.setTextColor(Color.WHITE);
                holder.parentView.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_10);
            } else {
                // Default state
                holder.dayOfMonth.setTextColor(Color.BLACK);
                holder.parentView.setBackgroundResource(0); // Set default background (null)
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
