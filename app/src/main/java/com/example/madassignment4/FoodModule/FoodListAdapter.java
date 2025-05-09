package com.example.madassignment4.FoodModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment4.FoodModule.FoodBean.FoodBean;
import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> implements Filterable {

    private Context context;
    private List<FoodBean> mList;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint != null ? constraint.toString().toLowerCase() : "";
                List<FoodBean> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(mList);
                } else {
                    for (FoodBean foodBean : mList) {
                        if (context.getString(foodBean.getDetail()).toLowerCase().contains(query)) {
                            filteredList.add(foodBean);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList.clear();
                mList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    // 定义点击事件接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public FoodListAdapter(Context context, List<FoodBean> foodBeans) {
        this.context = context;
        this.mList = foodBeans;

    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_list, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        int title = mList.get(position).getDetail();
        int image = mList.get(position).getImage();
        holder.title.setText(context.getResources().getString(title));
        holder.image.setImageResource(image);
        holder.itemView.setOnClickListener(v -> {
            int clickedPosition = holder.getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        FoodViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}