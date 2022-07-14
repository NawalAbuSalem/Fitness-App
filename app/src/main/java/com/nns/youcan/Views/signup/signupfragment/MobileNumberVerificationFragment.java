package com.nns.youcan.Views.signup.signupfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.nns.youcan.R;
import com.nns.youcan.Views.signup.SignUpInterface;

public class MobileNumberVerificationFragment extends Fragment {
   SignUpInterface signUpInterface;
    public MobileNumberVerificationFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.verification_fragment,container,false);
        PinEntryEditText editText=view.findViewById(R.id.txt_pin_entry);

        Button verify=view.findViewById(R.id.verify_btn);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")) {
                    signUpInterface.finishVerification(editText.getText().toString());
                }else {
                    editText.setError("Enter Verification code");
                }
            }
        });
        TextView resend=view.findViewById(R.id.resend_txt);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpInterface.resendMobileNumber( );
            }
        });
        return view;
    }
}
