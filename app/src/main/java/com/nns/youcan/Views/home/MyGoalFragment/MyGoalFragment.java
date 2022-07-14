package com.nns.youcan.Views.home.MyGoalFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;

public class MyGoalFragment extends Fragment {
    private View view;
    private TextView caloriesTextView;
    private TextView carbsTextView;
    private TextView fatTextView;
    private TextView proteinTextView;
    private TextView currentWeightTextView;
    private TextView goalWeightTextView;
    private TextView distanceTextView;
    private TextView waterAmountTextView;
    private AlertDialog macroDialog;
    private AlertDialog weightDialog;
    private AlertDialog distanceDialog;
    private AlertDialog waterDialog;
    private UsersFireStoreViewModel fireStoreViewModel;
    private MutableLiveData<MyGoal>myGoalLliveData;
    MutableLiveData<User>userLiveData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_goal_fragment, container, false);
        fireStoreViewModel=new UsersFireStoreViewModel();
        caloriesTextView =view.findViewById(R.id.calories_goal_textView);
        carbsTextView =view.findViewById(R.id.carbs_goal_textView);
        fatTextView =view.findViewById(R.id.fat_goal_textView);
        proteinTextView =view.findViewById(R.id.protien_goal_textView);
        currentWeightTextView =view.findViewById(R.id.current_weight_goal_textView);
        goalWeightTextView =view.findViewById(R.id.goal_weight_goal_textView);
        distanceTextView =view.findViewById(R.id.distance_goal_textView);
        waterAmountTextView =view.findViewById(R.id.water_drink_goal_textView);
        myGoalLliveData=fireStoreViewModel.getMyGoal();
        userLiveData=fireStoreViewModel.getCurrentUserInformation();
        updateGoals();
        view.findViewById(R.id.edit_macro_goal_textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View marcoLayout=getLayoutInflater().inflate(R.layout.edit_macro_dailog,null);
                Button updateMacroButton=marcoLayout.findViewById(R.id.update_macro_button);
                EditText caloriesEditText=marcoLayout.findViewById(R.id.calories_goal_edit_text);
                EditText carbsEditText=marcoLayout.findViewById(R.id.carbs_goal_edit_text);
                EditText fatEditText=marcoLayout.findViewById(R.id.fat_goal_edit_text);
                EditText proteinEditText=marcoLayout.findViewById(R.id.protien_goal_edit_text);

                macroDialog= new AlertDialog.Builder(getContext()).setView(marcoLayout).create();
                macroDialog.show();
                updateMacroButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String calories=caloriesEditText.getText().toString();
                        String carbs=carbsEditText.getText().toString();
                        String fat= fatEditText.getText().toString();
                        String protein=proteinEditText.getText().toString();
                        if (!calories.equals("")&&!carbs.equals("")&&!fat.equals("")&&!protein.equals("")){
                            fireStoreViewModel.updateMacro(calories,fat,protein,carbs);
                            caloriesTextView.setText(calories);
                            carbsTextView.setText(carbs);
                            fatTextView.setText(fat);
                            proteinTextView.setText(protein);
                       }
                        macroDialog.dismiss();

                    }
                });
            }
        });
        view.findViewById(R.id.edit_weight_goal_textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View weightLayout=getLayoutInflater().inflate(R.layout.edit_weight_dailog,null);
                Button updateWeightButton=weightLayout.findViewById(R.id.update_weight_button);
                EditText currentWeightEditText=weightLayout.findViewById(R.id.current_weight_goal_edit_text);
                EditText goalWeightEditText=weightLayout.findViewById(R.id.goal_weight_goal_edit_text);
                weightDialog= new AlertDialog.Builder(getContext()).setView(weightLayout).create();
                weightDialog.show();
                updateWeightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currentWeight=currentWeightEditText.getText().toString();
                        String goalWeight=goalWeightEditText.getText().toString();
                        if (currentWeight!=null&&!currentWeight.equals("")){
                            fireStoreViewModel.updateCurrentWeight(currentWeight);
                            currentWeightTextView.setText(currentWeight);
                        }
                        if (goalWeight!=null&&!goalWeight.equals("")){
                            fireStoreViewModel.updateGoalWeight(goalWeight);
                            goalWeightTextView.setText(goalWeight);
                        }
                        weightDialog.dismiss();
                    }
                });

            }
        });
        view.findViewById(R.id.edit_distance_goal_textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View distanceLayout=getLayoutInflater().inflate(R.layout.edit_distance_dailog,null);
                Button updateWeightButton=distanceLayout.findViewById(R.id.update_distance_button);
                EditText distanceEditText=distanceLayout.findViewById(R.id.distance_goal_edit_text);
                distanceDialog= new AlertDialog.Builder(getContext()).setView(distanceLayout).create();
                distanceDialog.show();
                updateWeightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (distanceEditText.getText().toString()!=null&&!distanceEditText.getText().toString().equals("")){
                            fireStoreViewModel.updateDistance(distanceEditText.getText().toString());
                            distanceTextView.setText(distanceEditText.getText().toString());
                        }
                        distanceDialog.dismiss();
                    }
                });
            }
        });
        view.findViewById(R.id.edit_water_goal_textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View waterLayout=getLayoutInflater().inflate(R.layout.edit_water_dailog,null);
                Button updateWaterButton=waterLayout.findViewById(R.id.update_water_button);
                EditText waterEditText=waterLayout.findViewById(R.id.water_drink_goal_edit_text);
                waterDialog= new AlertDialog.Builder(getContext()).setView(waterLayout).create();
                waterDialog.show();
                updateWaterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (waterEditText.getText().toString()!=null&&!waterEditText.getText().toString().equals("")) {
                            fireStoreViewModel.updateWaterAmount(waterEditText.getText().toString());
                            waterAmountTextView.setText(waterEditText.getText().toString());
                        }
                        waterDialog.dismiss();


                    }
                });

            }
        });
        return view;
    }


    private void updateGoals() {
        myGoalLliveData.observe(getActivity(), new Observer<MyGoal>() {
            @Override
            public void onChanged(MyGoal myGoal) {
                caloriesTextView.setText(myGoal.getCalories());
                carbsTextView.setText(myGoal.getCarbs());
                fatTextView.setText(myGoal.getFat());
                proteinTextView.setText(myGoal.getProtein());
                goalWeightTextView.setText(myGoal.getGoalWeight());
                distanceTextView.setText(myGoal.getGoalDistance());
                waterAmountTextView.setText(myGoal.getGoalWaterAmount());
            }
        });
        userLiveData.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentWeightTextView.setText(user.getUserWeight());
            }
        });

    }

}
