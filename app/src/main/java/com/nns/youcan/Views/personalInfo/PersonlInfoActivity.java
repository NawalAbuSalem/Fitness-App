package com.nns.youcan.Views.personalInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.LoginActivity;
import com.nns.youcan.Views.home.HomeActivity;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.FinishAskingFragment;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.GoalWeightFragment;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.HeightFragment;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.IdentificationQuestionFragment;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.NameQuestionFragment;
import com.nns.youcan.Views.personalInfo.personalInfoFragment.WeightFragment;
import com.nns.youcan.Views.signup.SignUpActivity;
import com.nns.youcan.WorkManager.CustomWorkManager;

public class PersonlInfoActivity extends AppCompatActivity implements PersonalInfoInterface{
   private FragmentManager fragmentManager;
   private ProgressBar progressBar;
   private int height;
   private int weight;
    private int goalWeight;
    private Boolean gender;
   private User user;
   private UsersFireStoreViewModel usersFireStoreViewModel;
   public static final String HEIGHT="height";
    public static final String WEIGHT="weight";
    public static final String GENDER="gender";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        user=new User();
        usersFireStoreViewModel=new UsersFireStoreViewModel();
        setContentView(R.layout.activity_personl_info);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,new NameQuestionFragment()).commit();
        progressBar=findViewById(R.id.progress_personal_info);
    }


    @Override
    public void getName(String name) {
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,new IdentificationQuestionFragment()).commit();
        progressBar.setProgress(32);
        user.setUserName(name);

    }

    @Override
    public void getGender(Boolean gender) {
        this.gender=gender;
        if (gender){
            user.setUserGender("male");
        }else{
            user.setUserGender("female");
        }
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,new HeightFragment()).commit();
        progressBar.setProgress(48);
    }



    @Override
    public void getHeight(int height) {
        user.setUserHeight(String.valueOf(height));
        this.height=height;
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,new WeightFragment()).commit();
        progressBar.setProgress(77);
    }

    @Override
    public void getWeight(int weight) {
        this.weight=weight;
        user.setUserWeight(String.valueOf(weight));
        Bundle bundle=new Bundle();
        bundle.putInt(HEIGHT,height);
        bundle.putInt(WEIGHT,weight);
        bundle.putBoolean(GENDER,gender);
        GoalWeightFragment goalWeightFragment=new GoalWeightFragment();
        goalWeightFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,goalWeightFragment).commit();
        progressBar.setProgress(90);

    }

    @Override
    public void getGoalWeight(int goalWeight) {
        this.goalWeight=goalWeight;
        progressBar.setVisibility(View.GONE);
        fragmentManager.beginTransaction().replace(R.id.personal_info_container,new FinishAskingFragment()).commit();


    }

    @Override
    public void finishAsking() {
        user.setUserPhoneNumber(getIntent().getStringExtra(SignUpActivity.USER_PHONE_NUM));
        MutableLiveData<String> liveData=usersFireStoreViewModel.addNewUser(user);
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase(UsersFireStoreViewModel.LOGIN_SUCCESS)){
                    MutableLiveData<String> liveData=usersFireStoreViewModel.RegistrationBySocialMedia();
                    liveData.observe(PersonlInfoActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equalsIgnoreCase(UsersFireStoreViewModel.LOGIN_SUCCESS)){
                                MutableLiveData<String> mliveData=usersFireStoreViewModel.addDefaultGoal(String.valueOf(weight),String.valueOf(goalWeight),String.valueOf(height));
                                mliveData.observe(PersonlInfoActivity.this, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        if (s.equalsIgnoreCase(UsersFireStoreViewModel.OPERATION_SUCCESS)){
                                            CustomWorkManager customWorkManager=new CustomWorkManager(PersonlInfoActivity.this);
                                            customWorkManager.setWaterAlarm();
                                            startActivity(new Intent(getBaseContext(), HomeActivity.class));
                                            finish();
                                        }else {
                                            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
