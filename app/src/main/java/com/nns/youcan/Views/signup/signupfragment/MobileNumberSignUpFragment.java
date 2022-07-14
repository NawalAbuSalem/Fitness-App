package com.nns.youcan.Views.signup.signupfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.nns.youcan.R;
import com.nns.youcan.Views.signup.SignUpInterface;
import com.hbb20.CountryCodePicker;


public class MobileNumberSignUpFragment extends Fragment {
    SignUpInterface signUpInterface;
    String mobileNumber;
    public MobileNumberSignUpFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SignUpInterface){
            signUpInterface= (SignUpInterface) context;

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mobile_number_sign_up,container,false);
         final EditText editText=view.findViewById(R.id.editText_carrierNumber);
        CountryCodePicker countryCodePicker=view.findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(editText);
        Button continueButton =view.findViewById(R.id.continue_tbn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")){
                     editText.setError(getString(R.string.emptymobilenumber));
                }
                else{

                    signUpInterface.getMobileNumber(countryCodePicker.getFullNumberWithPlus());

                }
            }
        });
        return view;
    }
}
