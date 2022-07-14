package com.nns.youcan.Views.WaterReminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.FoodDataBaseViewModel;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;

public class WaterTrackingActivity extends AppCompatActivity {
    private float start;
    private float end;
    private LottieAnimationView animationView;
    private UsersFireStoreViewModel fireStoreViewModel;
    private TextView goalAmountOfWaterTextView;
    private TextView idealAmountOfWaterTextView;
    private TextView cupOneTextView;
    private TextView cupTwoTextView;
    private TextView cupThreeTextView;
    private TextView cupFourTextView;
    private TextView cupFiveTextView;
    private TextView cupSixTextView;
    private TextView cupSevenTextView;
    private double totalAmountOfWater;
    private FoodDataBaseViewModel dataBaseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracking);
        fireStoreViewModel=new UsersFireStoreViewModel();
        dataBaseViewModel=new FoodDataBaseViewModel(this);
        animationView=findViewById(R.id.water_track_cup);
        goalAmountOfWaterTextView =findViewById(R.id.goal_water);
        idealAmountOfWaterTextView =findViewById(R.id.ideal_water_text_view);
        cupOneTextView=findViewById(R.id.one_cup_textView);
        cupTwoTextView=findViewById(R.id.two_cup_textView);
        cupThreeTextView=findViewById(R.id.three_cup_textView);
        cupFourTextView=findViewById(R.id.four_cup_textView);
        cupFiveTextView=findViewById(R.id.five_cup_textView);
        cupSixTextView=findViewById(R.id.six_cup_textView);
        cupSevenTextView=findViewById(R.id.seven_cup_textView);
        MutableLiveData<User>userInformationLiveData=fireStoreViewModel.getCurrentUserInformation();
        userInformationLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user!=null){
                    if (!user.getUserWeight().equals("")){
                        double currentWeight=Double.parseDouble(user.getUserWeight());
                        if (currentWeight>=0&&currentWeight<=45){
                            idealAmountOfWaterTextView.setText("1900");
                        }else if (currentWeight>45&&currentWeight<=50){
                            idealAmountOfWaterTextView.setText("2100");
                        } else if (currentWeight>50&&currentWeight<=55){
                            idealAmountOfWaterTextView.setText("2300");
                        } else if (currentWeight>55&&currentWeight<=60){
                            idealAmountOfWaterTextView.setText("2500");
                        } else if (currentWeight>60&&currentWeight<=65){
                            idealAmountOfWaterTextView.setText("2700");
                        }else if (currentWeight>65&&currentWeight<=70){
                            idealAmountOfWaterTextView.setText("2900");
                        }else if (currentWeight>70&&currentWeight<=75){
                            idealAmountOfWaterTextView.setText("3100");
                        }else if (currentWeight>75&&currentWeight<=80){
                            idealAmountOfWaterTextView.setText("3300");
                        }else if (currentWeight>80&&currentWeight<=85){
                            idealAmountOfWaterTextView.setText("3500");
                        }else if (currentWeight>85&&currentWeight<=90){
                            idealAmountOfWaterTextView.setText("3700");
                        }else if (currentWeight>90&&currentWeight<=85){
                            idealAmountOfWaterTextView.setText("3900");
                        }else if (currentWeight>95&&currentWeight<=100){
                            idealAmountOfWaterTextView.setText("4100");
                        }else if (currentWeight>100){
                            idealAmountOfWaterTextView.setText("4300");
                        }
                    }else{
                        idealAmountOfWaterTextView.setText("2000");
                    }
                }
            }
        });

        MutableLiveData<MyGoal>myGoalMutableLiveData=fireStoreViewModel.getMyGoal();
        myGoalMutableLiveData.observe(this, new Observer<MyGoal>() {
            @Override
            public void onChanged(MyGoal myGoal) {

                totalAmountOfWater=Double.parseDouble(myGoal.getGoalWaterAmount());
                goalAmountOfWaterTextView.setText(myGoal.getGoalWaterAmount());
                cupOneTextView.setText(String.valueOf((int)(totalAmountOfWater*.125)));
                cupTwoTextView.setText(String.valueOf((int)(totalAmountOfWater*.25)));
                cupThreeTextView.setText(String.valueOf((int)(totalAmountOfWater*.375)));
                cupFourTextView.setText(String.valueOf((int)(totalAmountOfWater*.5)));
                cupFiveTextView.setText(String.valueOf((int)(totalAmountOfWater*.625)));
                cupSixTextView.setText(String.valueOf((int)(totalAmountOfWater*.75)));
                cupSevenTextView.setText(String.valueOf((int)(totalAmountOfWater*.825)));
                MutableLiveData<Double>drankWaterAmount=dataBaseViewModel.getDailyAmountOfWater();
                drankWaterAmount.observe(WaterTrackingActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (totalAmountOfWater!=0){
                            end= (float) (aDouble/totalAmountOfWater);
                        }
                            animationView.setMinAndMaxProgress(start, end);
                            if (end!=0){
                                animationView.playAnimation();
                            }
                    }
                });
            }
        });



    }

    public void addNewCup(View view) {
        if (end<1){
            start=end;
            end+=.062f;
            animationView.setMinAndMaxProgress(start, end);
            animationView.playAnimation();
        }
    }

    public void minusACup(View view) {
        if(end>0){
            end=start;
            start-=.062f;
            animationView.setMinAndMaxProgress(start, end);
            animationView.playAnimation();
        }
    }

    public void save(View view) {
        int i=dataBaseViewModel.updateDailyAmountOfWater(totalAmountOfWater*animationView.getProgress());
        if (i<=0){
            dataBaseViewModel.addNewAmountOfWater(totalAmountOfWater*animationView.getProgress());
        }
    }

}
