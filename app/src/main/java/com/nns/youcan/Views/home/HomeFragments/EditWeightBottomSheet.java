package com.nns.youcan.Views.home.HomeFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;

import ke.tang.ruler.RulerView;

public class EditWeightBottomSheet extends BottomSheetDialogFragment {
  private RulerView currentWeightRulerView;
  private RulerView goalWeightRulerView;
  private TextView idealWeightTextView;
  private TextView BMITextView;
  private Button submitButton;
  UsersFireStoreViewModel fireStoreViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_weight_bottom_sheet, container, false);
        currentWeightRulerView=view.findViewById(R.id.current_weight_ruler_picker);
        goalWeightRulerView=view.findViewById(R.id.goal_weight_ruler_picker);
        idealWeightTextView=view.findViewById(R.id.ideal_weight_textView);
        BMITextView=view.findViewById(R.id.bmi_textView);
        submitButton=view.findViewById(R.id.submit_new_Weight_button);
        fireStoreViewModel=new UsersFireStoreViewModel();
        MutableLiveData<User> liveData=fireStoreViewModel.getCurrentUserInformation();
        liveData.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                int idealWeight=0;
                double height=0;
                int currentWeight=0;
                if (user.getUserHeight()!=null&&!user.getUserHeight().equals("")){
                   height=Double.parseDouble(user.getUserHeight());
                }
                if (user.getUserWeight()!=null&&!user.getUserWeight().equals("")){
                    currentWeight=Integer.parseInt(user.getUserWeight());
                    currentWeightRulerView.setValue(currentWeight);
                }
                if (user.getUserGender()!=null){
                    if (user.getUserGender().equalsIgnoreCase("male")){
                        idealWeight= (int) (50+0.91*(height-152.4));
                    }
                    else {
                        idealWeight= (int) (45.5+0.91*(height-152.4));
                    }
                }
                if (height!=0){
                    double bmi=(currentWeight/(height*height))*10000;
                    BMITextView.setText(String.format("%.1f",bmi));
                    idealWeightTextView.setText(String.valueOf(idealWeight));
                }else {
                    BMITextView.setText("0");
                    idealWeightTextView.setText("0");
                }
            }


        });
        MutableLiveData<MyGoal>myGoalMutableLiveData=fireStoreViewModel.getMyGoal();
        myGoalMutableLiveData.observe(getActivity(), new Observer<MyGoal>() {
            @Override
            public void onChanged(MyGoal myGoal) {
                int currentWeight=0;
                int goalWeight=0;
                double height=0;
                if (myGoal!=null){
                    if (myGoal.getHeight()!=null&&!myGoal.getHeight().equals("")){
                        height=Double.parseDouble(myGoal.getHeight())/100;
                    }
                    if (myGoal.getCurrentWeight()!=null&&!myGoal.getCurrentWeight().equals("")){
                        currentWeight=Integer.parseInt(myGoal.getCurrentWeight());
                        currentWeightRulerView.setValue(currentWeight);
                    }
                    if (myGoal.getGoalWeight()!=null&&!myGoal.getGoalWeight().equals("")){
                        goalWeight=Integer.parseInt(myGoal.getGoalWeight());
                        goalWeightRulerView.setValue(goalWeight);
                    }
                }

            }

        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireStoreViewModel.updateCurrentWeight(String.valueOf(currentWeightRulerView.getValue()));
                fireStoreViewModel.updateGoalWeight(String.valueOf(goalWeightRulerView.getValue()));
                dismiss();
            }
        });
        return view;
    }
}
