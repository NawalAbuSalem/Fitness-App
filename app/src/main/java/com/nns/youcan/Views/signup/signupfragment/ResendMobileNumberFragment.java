package com.nns.youcan.Views.signup.signupfragment;

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
import com.nns.youcan.Views.signup.SignUpInterface;

public class ResendMobileNumberFragment extends Fragment {
    SignUpInterface signUpInterface;

    public ResendMobileNumberFragment() {
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
        View view=inflater.inflate(R.layout.resend_mobile_number_fragment,container,false);
        final EditText editText=view.findViewById(R.id.resend_phone_number_edt);

        Button continueButton =view.findViewById(R.id.resend_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")){
                    editText.setError(getString(R.string.emptymobilenumber));
                }
                else{
                    signUpInterface.getMobileNumber(editText.getText().toString());
                }
            }
        });
        return view;
    }
}
