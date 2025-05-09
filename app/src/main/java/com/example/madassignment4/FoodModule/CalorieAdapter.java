package com.example.madassignment4.FoodModule;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.madassignment4.FoodModule.FoodBean.FoodBean;
import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.List;

public class CalorieAdapter extends RecyclerView.Adapter<CalorieAdapter.ViewHolder> {
    private List<FoodBean> mList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnTextChangeListener mOnTextChangeListener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        mOnTextChangeListener = onTextChangeListener;
    }


    public interface OnItemClickListener {
        void add(int position);

        void delete(int position);
    }
    public interface OnTextChangeListener {
        void changeName(int position,String name);

        void changeCount(int position,String count);
    }

    public CalorieAdapter(List<FoodBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (mList.size()>1&&position==mList.size()-1||mList.size()==1&&position==0){
            holder.ivdelete.setVisibility(View.GONE);
            holder.ivadd.setVisibility(View.VISIBLE);
        }else {
            holder.ivdelete.setVisibility(View.VISIBLE);
            holder.ivadd.setVisibility(View.GONE);
        }
        holder.ivdelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.delete(position);
            }
        });
        holder.ivadd.setOnClickListener(v -> {
            if (listener != null) {
                listener.add(position);
            }
        });
        holder.et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mOnTextChangeListener != null) {
                    mOnTextChangeListener.changeName(position,s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.et_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mOnTextChangeListener != null) {
                    mOnTextChangeListener.changeCount(position,s.toString());
                }
            }
        });
        holder.et_name.setText(mList.get(position).getName());
        holder.et_count.setText(mList.get(position).getCount());

    }
    @Override
    public long getItemId(int position) {
        return position;
    }





    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText et_name;
        private EditText et_count;
        private ImageView ivadd, ivdelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_name = itemView.findViewById(R.id.tv_name);
            et_count = itemView.findViewById(R.id.tv_weight);
            ivadd = itemView.findViewById(R.id.add_button);
            ivdelete = itemView.findViewById(R.id.delete_button);

        }
    }
}
