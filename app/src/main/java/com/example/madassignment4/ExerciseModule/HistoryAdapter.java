package com.example.madassignment4.ExerciseModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final ArrayList<HashMap<String, String>> historyList;

    public HistoryAdapter(ArrayList<HashMap<String, String>> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HashMap<String, String> exerciseLog = historyList.get(position);

        holder.tvDate.setText(exerciseLog.get("Date"));
        holder.tvExerciseType.setText(exerciseLog.get("ExerciseType"));
        holder.tvAttribute.setText(exerciseLog.get("Attributes"));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvExerciseType, tvAttribute;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvExerciseType = itemView.findViewById(R.id.tvExerciseType);
            tvAttribute = itemView.findViewById(R.id.tvAttribute);
        }
    }
}
