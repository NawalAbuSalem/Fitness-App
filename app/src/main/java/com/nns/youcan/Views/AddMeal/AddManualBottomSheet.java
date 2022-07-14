package com.nns.youcan.Views.AddMeal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.nns.youcan.DataBase.DailyEatenFoodDataBase;
import com.nns.youcan.Models.DailyEatenFood;
import com.nns.youcan.Models.MyMeal;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nns.youcan.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddManualBottomSheet extends BottomSheetDialogFragment {
    private EditText name;
    private EditText calories;
    private EditText fat;
    private EditText protien;
    private EditText serving;
    private EditText carbs;
    private MyMeal newMeal;
    public interface AddMealManual{
         void addNewManualMeal(MyMeal myMeal);
    }
    private AddMealManual addMealManual;
    private int mealType;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddMealManual){
            this.addMealManual= (AddMealManual) context;
        }
        mealType=getArguments().getInt(AddMealActivity.MEAL_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.meal_bottom_sheet_layout,container,false);
        name=view.findViewById(R.id.meal_name_edt);
        calories=view.findViewById(R.id.calories_edt);
        fat=view.findViewById(R.id.fat_edt);
        protien=view.findViewById(R.id.protien_edt);
        serving=view.findViewById(R.id.serving_edt);
        carbs=view.findViewById(R.id.carbs_edt);
        view.findViewById(R.id.add_maunal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 newMeal=new MyMeal();
                 checkEmptyFeild();
                 newMeal.setType(mealType);
                 addMealManual.addNewManualMeal(newMeal);
                 clear();
                 dismiss();

            }
        });
        return view;
    }

    private void checkEmptyFeild() {
        if (name.getText().toString().equals(""))
            newMeal.setName("new meal");
        else
            newMeal.setName(name.getText().toString());
        if (calories.getText().toString().equals(""))
            newMeal.setCalories(0);
        else
            newMeal.setCalories(Double.parseDouble(calories.getText().toString()));
        if (fat.getText().toString().equals(""))
            newMeal.setFat(0);
        else
            newMeal.setFat(Double.parseDouble(fat.getText().toString()));
        if (protien.getText().toString().equals(""))
            newMeal.setProtien(0);
        else
            newMeal.setProtien(Double.parseDouble(protien.getText().toString()));
        if (serving.getText().toString().equals(""))
            newMeal.setServing(1);
        else
            newMeal.setServing(Double.parseDouble(serving.getText().toString()));
        if (carbs.getText().toString().equals(""))
            newMeal.setCarbs(0);
        else
            newMeal.setCarbs(Double.parseDouble(carbs.getText().toString()));
    }

    private void clear() {
        name.setText("");
        calories.setText("");
        fat.setText("");
        protien.setText("");
        serving.setText("");
        carbs.setText("");
    }


}
