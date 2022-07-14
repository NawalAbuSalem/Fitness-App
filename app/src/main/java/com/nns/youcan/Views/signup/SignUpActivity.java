package com.nns.youcan.Views.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.personalInfo.PersonlInfoActivity;
import com.nns.youcan.Views.signup.signupfragment.MobileNumberSignUpFragment;
import com.nns.youcan.Views.signup.signupfragment.MobileNumberVerificationFragment;
import com.nns.youcan.Views.signup.signupfragment.ResendMobileNumberFragment;
import com.nns.youcan.Views.signup.signupfragment.StartingPersonnalInfoFragment;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface{
    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final String USER_PHONE_NUM ="user phone signup" ;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
     private String codeSent;
     private String phoneNumber;
     private UsersFireStoreViewModel usersFireStoreViewModel;
    PhoneAuthProvider.ForceResendingToken token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usersFireStoreViewModel=new UsersFireStoreViewModel();
        mAuth=FirebaseAuth.getInstance();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.sign_up_container_fragment,new MobileNumberSignUpFragment()).commit();


    }

    @Override
    public void getMobileNumber(String mobileNumber) {
        phoneNumber=mobileNumber;
        MutableLiveData<Boolean>liveData=usersFireStoreViewModel.isPhoneNumberIsExist(mobileNumber);
        liveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    sendVerificationCode( mobileNumber);
                    ///go to next step sign_up verification
                    fragmentManager.beginTransaction().replace(R.id.sign_up_container_fragment,new MobileNumberVerificationFragment()).commit();
                }else {
                    Toast.makeText(SignUpActivity.this, "phone number is already exist", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void sendVerificationCode(String mobileNumber) {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeSent=s;
                token=forceResendingToken;
            }


        };


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,        // Phone number to verify
                15,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                SignUpActivity.this,               // Activity (for callback binding)
                mCallbacks,token);

    }

    @Override
    public void finishVerification(String code) {
        verifySigup(code);

    }

    private void verifySigup(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fragmentManager.beginTransaction().replace(R.id.sign_up_container_fragment,new StartingPersonnalInfoFragment()).commit();
                            //TODO :FirebaseUser user = task.getResult().getUser();
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignUpActivity.this, "invalid verification code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void resendMobileNumber() {
        fragmentManager.beginTransaction().replace(R.id.sign_up_container_fragment,new ResendMobileNumberFragment()).commit();
    }

    @Override
    public void startAskPersonalInformation() {
        Intent intent=new Intent(this, PersonlInfoActivity.class);
        intent.putExtra(USER_PHONE_NUM,phoneNumber);
        startActivity(intent);
        finish();

    }
}
