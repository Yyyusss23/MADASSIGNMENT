package com.example.madassignment4.FoodModule;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.madassignment4.FoodModule.FoodBean.FoodBean;
import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.List;

public class CalorieResultAdapter extends RecyclerView.Adapter<CalorieResultAdapter.ViewHolder> {
    private List<FoodBean> mList = new ArrayList<>();

    public CalorieResultAdapter(List<FoodBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calorie_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodBean foodBean = mList.get(position);
        int calorie = foodBean.getCalorie();
        holder.tvCalorie.setText(calorie==0?"Calorie":calorie+"");
        String name = foodBean.getName();
        holder.tvName.setText(TextUtils.isEmpty(name)?"Item":name);
        String count = foodBean.getCount();
        holder.tvCount.setText(TextUtils.isEmpty(count)?"Weight":count+"g");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCount;
        private TextView tvCalorie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.food_name);
            tvCount = itemView.findViewById(R.id.weight);
            tvCalorie = itemView.findViewById(R.id.calories);

        }
    }
}
