package com.example.madassignment4.FoodModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.madassignment4.FoodModule.FoodBean.FoodBean;
import com.example.madassignment4.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodBean> mList;

    public FoodAdapter(List<FoodBean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.ivIcon.setImageResource(mList.get(position).getImage());
        holder.tvName.setText(mList.get(position).getName());
        holder.tvCount.setText(mList.get(position).getCount()+"g");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvCount;
        TextView tvName;
        ImageView ivIcon;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
