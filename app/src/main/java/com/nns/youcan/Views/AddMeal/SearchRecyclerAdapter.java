package com.nns.youcan.Views.AddMeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nns.youcan.Models.RecipesModel.Recipe;

import com.nns.youcan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchMealItemHolder>  {
    public interface OnRecycleritemListener{
       void onRecyclerSearchItemListener(Recipe recipe);
    }
    private List<Recipe> list;
    private OnRecycleritemListener onRecycleritemListener;
    public SearchRecyclerAdapter(List<Recipe>list, Context context) {
        this.list=list;
        if (context instanceof OnRecycleritemListener){
            onRecycleritemListener= (OnRecycleritemListener) context;
        }
    }
    @NonNull
    @Override
    public SearchMealItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (!list.isEmpty()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_data_found_layout, parent, false);
        }
        SearchMealItemHolder viewHolder = new SearchMealItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMealItemHolder holder, final int position) {
        if(holder.mealName!=null&&holder.mealTotalCalories!=null&&holder.mealTotalTime!=null){
          holder.mealName.setText(list.get(position).getLabel());
          holder.mealTotalCalories.setText(String.valueOf((int)(Math.ceil(list.get(position).getCalories()))));
          holder.mealTotalTime.setText(String.valueOf((int)(Math.ceil(list.get(position).getTotalTime()))));
          Picasso.get().load(list.get(position).getImage()).into(holder.mealImage);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  onRecycleritemListener.onRecyclerSearchItemListener(list.get(position));
              }
          });
        }
    }
    @Override
    public int getItemCount() {
//        if(list.size() == 0){
//            return 1;
//        }
        return list.size();

    }

    class SearchMealItemHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        TextView mealTotalTime;
        TextView mealTotalCalories;
        ImageView mealImage;
        public SearchMealItemHolder(@NonNull View itemView) {
            super(itemView);
           mealName=itemView.findViewById(R.id.recipe_item_name_txt);
           mealTotalCalories=itemView.findViewById(R.id.recipe_item_calories_txt);
           mealTotalTime=itemView.findViewById(R.id.recipe_item_time_txt);
           mealImage=itemView.findViewById(R.id.recipe_item_image);
        }
    }

    public void filter(List<Recipe> newList) {
        list.addAll(newList);
        notifyItemRangeInserted(0,newList.size());
    }
    public void clearPervious(){
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
}
