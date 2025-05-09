package com.example.madassignment4.FoodModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.madassignment4.R;

import java.util.List;

public class WeekdayAdapter extends RecyclerView.Adapter<WeekdayAdapter.WeekdayViewHolder> {

    private List<String> weekdays;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 定义点击事件接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public WeekdayAdapter(List<String> weekdays) {
        this.weekdays = weekdays;
    }

    @NonNull
    @Override
    public WeekdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weekday, parent, false);
        return new WeekdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekdayViewHolder holder, int position) {
        holder.tvWeekday.setText(weekdays.get(position));
        // 设置点击监听器
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(clickedPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return weekdays.size();
    }

    public static class WeekdayViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeekday;

        public WeekdayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeekday = itemView.findViewById(R.id.tv_weekday);
        }
    }
}