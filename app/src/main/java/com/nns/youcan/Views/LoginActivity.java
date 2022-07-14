package com.nns.youcan.Views;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.home.HomeActivity;
import com.firebase.ui.auth.AuthUI;
import com.nns.youcan.Views.signup.SignUpActivity;
import com.nns.youcan.WorkManager.CustomWorkManager;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private UsersFireStoreViewModel usersFireStoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signUp=findViewById(R.id.signup_txt);
        usersFireStoreViewModel=new UsersFireStoreViewModel();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        ImageView googleLogin=findViewById(R.id.google_signIn);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build());
                 // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                        RC_SIGN_IN
                );
            }
        });
        ImageView faceBookLogin=findViewById(R.id.faceBook_signIn);

        faceBookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.FacebookBuilder().build());
                 // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                        RC_SIGN_IN
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                MutableLiveData<String> liveData=usersFireStoreViewModel.RegistrationBySocialMedia();
                liveData.observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equalsIgnoreCase(UsersFireStoreViewModel.LOGIN_SUCCESS)){
                            MutableLiveData<String> mliveData=usersFireStoreViewModel.addDefaultGoal("0","0","0");
                            mliveData.observe(LoginActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    if (s.equalsIgnoreCase(UsersFireStoreViewModel.OPERATION_SUCCESS)){
                                        CustomWorkManager customWorkManager=new CustomWorkManager(LoginActivity.this);
                                        customWorkManager.setWaterAlarm();
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else if (s.equalsIgnoreCase(UsersFireStoreViewModel.REGISTRATION_SUCCESS)){
                            CustomWorkManager customWorkManager=new CustomWorkManager(LoginActivity.this);
                            customWorkManager.setWaterAlarm();
                            startActivity(new Intent(LoginActivity.this, OnboardingActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }
    }

    public void manualSignIn(View view) {
        TextInputEditText inputPhoneNumber=findViewById(R.id.edt_phone_number);
        TextInputEditText inputPassword=findViewById(R.id.edt_password);
        if (inputPhoneNumber.getText().toString().equals("")){
            inputPhoneNumber.setError("Empty phone number");
        }else if (inputPassword.getText().toString().equals("")){
            inputPassword.setError("Empty password");
        }else {
            MutableLiveData<String> loginLiveData=usersFireStoreViewModel.manualLogin(inputPhoneNumber.getText().toString(),inputPassword.getText().toString());
            loginLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equalsIgnoreCase(UsersFireStoreViewModel.LOGIN_SUCCESS)){
                        CustomWorkManager customWorkManager=new CustomWorkManager(LoginActivity.this);
                        customWorkManager.setWaterAlarm();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }else if (s.equalsIgnoreCase(UsersFireStoreViewModel.INVALID_PASSWORD)){
                        Toast.makeText(getBaseContext(), UsersFireStoreViewModel.INVALID_PASSWORD, Toast.LENGTH_SHORT).show();

                    }else if (s.equalsIgnoreCase(UsersFireStoreViewModel.ACCOUNT_IS_NOT_EXIST)){
                        Toast.makeText(getBaseContext(), UsersFireStoreViewModel.ACCOUNT_IS_NOT_EXIST, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }


    }
}
