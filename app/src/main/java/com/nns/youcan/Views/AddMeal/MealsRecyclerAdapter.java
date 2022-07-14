package com.nns.youcan.Views.AddMeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nns.youcan.Models.MyMeal;
import com.nns.youcan.R;

import java.util.List;

public class MealsRecyclerAdapter extends RecyclerView.Adapter<MealsRecyclerAdapter.MealItemHolder> {
    List<MyMeal>list;
    public MealsRecyclerAdapter(List<MyMeal>list) {
        this.list=list;
    }

    @NonNull
    @Override
    public MealItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_meal_item, parent, false);
        MealItemHolder viewHolder = new MealItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemHolder holder, int position) {
        holder.mealName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


     class MealItemHolder extends RecyclerView.ViewHolder{
          TextView mealName;
        public MealItemHolder(@NonNull View itemView) {
            super(itemView);
            mealName=itemView.findViewById(R.id.meal_name_item);
        }
    }

}
