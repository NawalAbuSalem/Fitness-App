package com.nns.youcan.Views.personalInfo.personalInfoFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nns.youcan.R;
import com.nns.youcan.Views.personalInfo.PersonalInfoInterface;

public class IdentificationQuestionFragment extends Fragment {
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
        final View view = inflater.inflate(R.layout.identification_question_fragment, container, false);
        Button continueButton=view.findViewById(R.id.question2_btn);
        final RadioButton male=view.findViewById(R.id.male);
        final RadioButton female=view.findViewById(R.id.female);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isChecked()){
                    personalInfoInterface.getGender(true);
                }else if (female.isChecked()){
                    personalInfoInterface.getGender(false);
                }else {
                    Toast.makeText(getContext(), getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
