package com.nns.youcan.Views.Recipe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nns.youcan.R;

public class RecipeBottomSheet extends BottomSheetDialogFragment {
    private  int meal_type;
    private AddSearchedMeal addSearchedMeal;
    public interface AddSearchedMeal{
        void onAddSearchMeal(int mealType);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddSearchedMeal){
            addSearchedMeal= (AddSearchedMeal) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.recipe_bottom_sheet,container,false);
        TextView recipeIngredient=view.findViewById(R.id.recipe_ingredient);
        TextView recipeServing=view.findViewById(R.id.recipe_serving);
        Spinner  addAsSpinner=view.findViewById(R.id.recipe_type_spinner);
        addAsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 System.out.println(position+"");
                 meal_type=position;
             }
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
        String ingredient =getArguments().getString(RecipeActivity.RECIPE_INGGREDIENT);
        if (ingredient!=null){
            recipeIngredient.setText(ingredient);
        }
        double serving=getArguments().getDouble(RecipeActivity.RECIPE_SERVING);
          recipeServing.setText(String.valueOf((int)(Math.ceil(serving))));

        view.findViewById(R.id.recipe_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSearchedMeal.onAddSearchMeal(meal_type);
            }
        });
        return view;
    }
}
