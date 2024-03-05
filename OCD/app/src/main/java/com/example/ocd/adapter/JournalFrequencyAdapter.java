package com.example.ocd.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ocd.R;

public class JournalFrequencyAdapter extends RecyclerView.Adapter<JournalFrequencyAdapter.ViewHolder> {
    private List<String> list;

    private int selectedItem = -1; // Variable to keep track of the selected item


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.cell);
        }
    }

    public JournalFrequencyAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public JournalFrequencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_frequency_list_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String obsession = list.get(position);
        holder.textName.setText(obsession);

        // Apply the style change only to the selected item
        if (selectedItem == position) {
            holder.textName.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_border_indigo_400_radius_10);
            holder.textName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.textName.setBackgroundResource(R.drawable.rectangle_bg_gray_100_border_gray_300_radius_10);
            holder.textName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }

        holder.itemView.setOnClickListener(view -> {
            selectedItem = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean isOptionSelected() {
        return selectedItem != -1;
    }

    public String getSelectedOption() {
        if (selectedItem == -1) {
            return null;
        }
        return list.get(selectedItem);
    }
}
