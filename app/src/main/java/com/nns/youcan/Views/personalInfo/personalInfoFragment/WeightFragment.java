package com.nns.youcan.Views.personalInfo.personalInfoFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nns.youcan.R;
import com.nns.youcan.Views.personalInfo.PersonalInfoInterface;

public class WeightFragment extends Fragment {
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
        final View view = inflater.inflate(R.layout.weight_fragment, container, false);
        final NumberPicker picker=view.findViewById(R.id.weight_numberPicker);
        picker.setMinValue(40);
        picker.setMaxValue(150);
        picker.setValue(60);
        Button continueButton=view.findViewById(R.id.question5_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalInfoInterface.getWeight(picker.getValue());
            }
        });

        return view;
    }
}
