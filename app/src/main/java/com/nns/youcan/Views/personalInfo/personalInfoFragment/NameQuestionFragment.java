package com.nns.youcan.Views.personalInfo.personalInfoFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nns.youcan.R;
import com.nns.youcan.Views.personalInfo.PersonalInfoInterface;

public class NameQuestionFragment  extends Fragment {
    PersonalInfoInterface personalInfoInterface;

    public NameQuestionFragment() {
    }

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
        View view = inflater.inflate(R.layout.name_question_fragment, container, false);
        final EditText name=view.findViewById(R.id.name_edt);
        Button question=view.findViewById(R.id.question1_btn);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")){
                    name.setError(getString(R.string.emptymobilenumber));
                }
                else{
                    personalInfoInterface.getName(name.getText().toString());
                }
            }
        });
        return view;
    }
}
