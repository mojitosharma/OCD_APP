package com.example.ocd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ocd.R;

import org.json.JSONException;

public class ObsessionListAdapter extends RecyclerView.Adapter<ObsessionListAdapter.ViewHolder>{
    private List<String> list;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;

        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.plantNameShapeCell);
        }
    }


    public ObsessionListAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ObsessionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obsession_list_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}


