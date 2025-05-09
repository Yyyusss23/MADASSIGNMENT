package com.example.madassignment4.DailyWellnessModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment4.R;

import java.util.List;

public class HydrationIntakeAdapter extends RecyclerView.Adapter<HydrationIntakeAdapter.ViewHolder> {
    private List<HydrationIntakeModel> hydrationList;

    public HydrationIntakeAdapter(List<HydrationIntakeModel> hydrationList) {
        this.hydrationList = hydrationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hydration_intake, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HydrationIntakeModel model = hydrationList.get(position);

        // Set data to views
        holder.intakeTimestampTextView.setText(model.getIntakeTimestamp());
        holder.quantityTextView.setText(String.valueOf(model.getQuantityOfWater()) + " ml");
    }

    @Override
    public int getItemCount() {
        return hydrationList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView intakeTimestampTextView;
        TextView quantityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            intakeTimestampTextView = itemView.findViewById(R.id.intakeTimestampTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
    }
}




