package com.nns.youcan.Views.signup.signupfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nns.youcan.R;
import com.nns.youcan.Views.signup.SignUpInterface;

public class StartingPersonnalInfoFragment extends Fragment {
    SignUpInterface signUpInterface;
    public StartingPersonnalInfoFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SignUpInterface){
            signUpInterface= (SignUpInterface) context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.starting_personal_info_fragment, container, false);
        view.findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpInterface.startAskPersonalInformation();
            }
        });
        return view;
    }

}
