package com.nns.youcan.Views.personalInfo.personalInfoFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nns.youcan.R;
import com.nns.youcan.Views.personalInfo.PersonalInfoInterface;
import com.nns.youcan.Views.personalInfo.PersonlInfoActivity;

public class GoalWeightFragment extends Fragment {
    PersonalInfoInterface personalInfoInterface;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PersonalInfoInterface){
            personalInfoInterface= (PersonalInfoInterface) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.goal_weight_fragment, container, false);
        final NumberPicker picker=view.findViewById(R.id.goal_weight_numberPicker);
        Button continueButton=view.findViewById(R.id.question6_btn);
        TextView idealweight=view.findViewById(R.id.suggestion_idealweight);
        picker.setMinValue(40);
        picker.setMaxValue(200);

        double height=getArguments().getInt(PersonlInfoActivity.HEIGHT);
        Boolean isMale=getArguments().getBoolean(PersonlInfoActivity.GENDER);
        int goalWeight=0;
        if (isMale){
            goalWeight= (int) (50+0.91*(height-152.4));
        }
        else {
            goalWeight= (int) (45.5+0.91*(height-152.4));
        }
        idealweight.setText(String.valueOf(goalWeight)+ " "+getString(R.string.kg));
        picker.setValue(goalWeight);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalInfoInterface.getGoalWeight(picker.getValue());
            }
        });
        return view;
    }
}
