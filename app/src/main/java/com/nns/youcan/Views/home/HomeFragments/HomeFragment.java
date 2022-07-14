package com.nns.youcan.Views.home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.FoodDataBaseViewModel;
import com.nns.youcan.ViewModel.GoogleFitAPIViewModel;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.AddMeal.AddMealActivity;

import com.nns.youcan.Views.StepTracking.StepActivity;
import com.nns.youcan.Views.WaterReminder.WaterTrackingActivity;

public class HomeFragment extends Fragment  {
    private View[] runProgress;
    private View[] goalProgress;
    private EditWeightBottomSheet bottomSheet;
    private UsersFireStoreViewModel fireStoreViewModel;
    private GoogleFitAPIViewModel googleFitAPIViewModel;
    private FoodDataBaseViewModel dataBaseViewModel;
    private TextView BMITextView;
    private ImageView BMIPointer;
    private TextView walkDurationTextView;
    private TextView remindedDistanceTextView;
    private TextView userNameTextView;
    private TextView dailyGoalPercentageTextView;
    private TextView mainGoalPercentageTextView;
    private ProgressBar mainGoalProgress;
    private float bmiPointerPosition;
    private double height;
    private double currentDistance;
    private double currentCalories;
    private double currentCarbs;
    private double currentFat;
    private double currentProtein;
    private double currentWaterAmount;
    private double currentWeight;
    private double dailyPercentage;
    private double divisor;
    private double goalWeight;
    private double goalDistance;
    private double goalCalories;
    private double goalCarbs;
    private double goalFat;
    private double goalProtein;
    private double goalWaterAmount;
    private boolean isMyGoalArrived;
    private boolean isCurrentDistanceArrived;
    private boolean isCurrentWaterAmountArrived;
    private boolean isCurrentCaloriesArrived;
    private boolean isCurrentProteinArrived;
    private boolean isCurrentFatArrived;
    private boolean isCurrentCarbsArrived;
    private MutableLiveData<User> userMutableLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        BMITextView = view.findViewById(R.id.home_bmi_value);
        BMIPointer = view.findViewById(R.id.home_bmi_pointer);
        userNameTextView = view.findViewById(R.id.home_name_txt);
        walkDurationTextView = view.findViewById(R.id.home_running_duration_txt);
        remindedDistanceTextView = view.findViewById(R.id.home_reminded_main_goal);
        dailyGoalPercentageTextView = view.findViewById(R.id.home_goals_txt);
        mainGoalPercentageTextView = view.findViewById(R.id.home_percentage_main_goal);
        mainGoalProgress = view.findViewById(R.id.home_progressBar_main_goal);
        dataBaseViewModel = new FoodDataBaseViewModel(getContext());
        bottomSheet = new EditWeightBottomSheet();
        fireStoreViewModel = new UsersFireStoreViewModel();
        googleFitAPIViewModel = new GoogleFitAPIViewModel(getContext());
        updateBMIPointer();
        view.findViewById(R.id.home_see_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "this will be active in the next version", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.edit_weight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
            }
        });
        view.findViewById(R.id.home_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StepActivity.class));
            }
        });
        view.findViewById(R.id.recipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMealActivity.class));
            }
        });
        view.findViewById(R.id.water_track).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WaterTrackingActivity.class));
            }
        });
        runProgress = new View[]{view.findViewById(R.id.v1),
                view.findViewById(R.id.v2),
                view.findViewById(R.id.v3),
                view.findViewById(R.id.v4),
                view.findViewById(R.id.v5),
                view.findViewById(R.id.v6),
                view.findViewById(R.id.v7),
                view.findViewById(R.id.v8),
                view.findViewById(R.id.v9),
                view.findViewById(R.id.v10),
                view.findViewById(R.id.v11),
                view.findViewById(R.id.v12),
                view.findViewById(R.id.v13),
                view.findViewById(R.id.v14),
                view.findViewById(R.id.v15),
                view.findViewById(R.id.v16),
                view.findViewById(R.id.v17),
                view.findViewById(R.id.v18),
                view.findViewById(R.id.v19),
                view.findViewById(R.id.v20),
                view.findViewById(R.id.v21),
                view.findViewById(R.id.v22),
                view.findViewById(R.id.v23),
                view.findViewById(R.id.v24)};
        goalProgress = new View[]{view.findViewById(R.id.va),
                view.findViewById(R.id.vb),
                view.findViewById(R.id.vc),
                view.findViewById(R.id.vd),
                view.findViewById(R.id.ve),
                view.findViewById(R.id.vf),
                view.findViewById(R.id.vg),
                view.findViewById(R.id.vh),
                view.findViewById(R.id.vi),
                view.findViewById(R.id.vj),
                view.findViewById(R.id.vk),
                view.findViewById(R.id.vl),
                view.findViewById(R.id.vm),
                view.findViewById(R.id.vn),
                view.findViewById(R.id.vo),
                view.findViewById(R.id.vp),
                view.findViewById(R.id.vq),
                view.findViewById(R.id.vr),
                view.findViewById(R.id.vs),
                view.findViewById(R.id.vt),
                view.findViewById(R.id.vu),
                view.findViewById(R.id.vv),
                view.findViewById(R.id.vw),
                view.findViewById(R.id.vx)};

        MutableLiveData<MyGoal> myGoalLiveData = fireStoreViewModel.getMyGoal();
        myGoalLiveData.observe(getActivity(), new Observer<MyGoal>() {
            @Override
            public void onChanged(MyGoal myGoal) {
                goalDistance = Double.parseDouble(myGoal.getGoalDistance());
                goalWeight = Double.parseDouble(myGoal.getGoalWeight());
                goalCalories = Double.parseDouble(myGoal.getCalories());
                goalCarbs = Double.parseDouble(myGoal.getCarbs());
                goalFat = Double.parseDouble(myGoal.getFat());
                goalProtein = Double.parseDouble(myGoal.getProtein());
                goalWaterAmount = Double.parseDouble(myGoal.getGoalWaterAmount());
                isMyGoalArrived = true;
                updateDistanceInformation();
                updateGoalPercentage();

            }
        });
        if (googleFitAPIViewModel.isRegistered()) {
            MutableLiveData<String> distanceLiveData = googleFitAPIViewModel.getDistance();
            distanceLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    currentDistance = Double.parseDouble(s) / 1000;
                    isCurrentDistanceArrived = true;
                    updateDistanceInformation();
                    updateGoalPercentage();
                }
            });
            MutableLiveData<String> movementDurationLiveData = googleFitAPIViewModel.getMovementDuration();
            movementDurationLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    walkDurationTextView.setText(s);
                }
            });

        }
        LiveData<Double> dailyAmountOfWaterLiveData = dataBaseViewModel.getDailyAmountOfWater();
        dailyAmountOfWaterLiveData.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentWaterAmount = aDouble;
                isCurrentWaterAmountArrived = true;
                updateGoalPercentage();

            }
        });
        LiveData<Double> caloriesLiveData = dataBaseViewModel.getTotalCalories();
        caloriesLiveData.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentCalories = aDouble;
                isCurrentCaloriesArrived = true;
                updateGoalPercentage();

            }
        });
        LiveData<Double> ProteinLiveData = dataBaseViewModel.getTotalProtein();
        ProteinLiveData.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentProtein = aDouble;
                isCurrentProteinArrived = true;
                updateGoalPercentage();

            }
        });
        LiveData<Double> fatLiveData = dataBaseViewModel.getTotalFat();
        fatLiveData.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentFat = aDouble;
                isCurrentFatArrived = true;
                updateGoalPercentage();

            }
        });
        LiveData<Double> carbsLiveData = dataBaseViewModel.getTotalCarbs();
        carbsLiveData.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentCarbs = aDouble;
                isCurrentCarbsArrived = true;
                updateGoalPercentage();

            }
        });
        return view;
    }

    private void updateBMIPointer() {
        userMutableLiveData = fireStoreViewModel.getCurrentUserInformation();
        userMutableLiveData.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String[] userName = user.getUserName().split(" ");
                userNameTextView.setText(userName[0]);
                if (user.getUserHeight() != null && !user.getUserHeight().equals("")) {
                    height = Integer.parseInt(user.getUserHeight());
                }
                if (user.getUserWeight() != null && !user.getUserWeight().equals("")) {
                    currentWeight = Integer.parseInt(user.getUserWeight());
                }
                if (height != 0) {
                    double bmi = (currentWeight / (height * height)) * 10000;
                    BMITextView.setText(String.format("%.1f", bmi));
                    setBMIPosition(bmi);
                }
            }
        });

    }


    private void setRunProgressAnimation(final double goalDistance, final double walkedDistance) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio = 0;
                if (goalDistance != 0) {
                    if (walkedDistance >= goalDistance) {
                        fillRatio = 24;
                    } else {
                        fillRatio = (int) Math.ceil(((walkedDistance / goalDistance) * 24));
                    }
                }
                for (int i = 0; i < fillRatio; i++) {
                    try {
                        Thread.sleep(80);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (runProgress[finalI]!=null){
                                    runProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_red));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setGoalProgressAnimation(final double actual, final double done) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio = (int) ((done / actual) * 24);
                if (fillRatio > 24) {
                    fillRatio = 24;
                }
                for (int i = 0; i < fillRatio; i++) {
                    try {
                        Thread.sleep(80);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (goalProgress[finalI]!=null){
                                    goalProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_primary));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setBMIPosition(double bmi) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                float fillRatio = 0;
                if (bmi < 15) {
                } else if (bmi >= 15 && bmi <= 18.5) {
                    fillRatio = 30 * getContext().getResources().getDisplayMetrics().density;
                } else if (bmi >= 18.9 && bmi <= 25) {
                    fillRatio = 120 * getContext().getResources().getDisplayMetrics().density;
                } else if (bmi >= 25.1 && bmi <= 30) {
                    fillRatio = 210 * getContext().getResources().getDisplayMetrics().density;
                } else if (bmi >= 30.1) {
                    fillRatio = 300 * getContext().getResources().getDisplayMetrics().density;
                }
                if (bmiPointerPosition!=0){
                   BMIPointer.setTranslationX( -bmiPointerPosition);
                    BMITextView.setTranslationX( -bmiPointerPosition);
                   
                } 
                 bmiPointerPosition=fillRatio;
                for (int i = 0; i < fillRatio; i += 5) {
                    try {
                        Thread.sleep(5);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (BMIPointer != null && BMITextView != null) {
                                    BMIPointer.setTranslationX(finalI);
                                    BMITextView.setTranslationX(finalI);
                                }

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateDistanceInformation() {
        if (isMyGoalArrived && isCurrentDistanceArrived) {
            setRunProgressAnimation(goalDistance, currentDistance);
            double remindedDistance = (goalDistance) - (currentDistance);
            if (remindedDistance <= 0) {
                remindedDistanceTextView.setText("Great,You walked goal distance");
            } else {
                remindedDistanceTextView.setText("You have to walk " + String.format("%.2f", remindedDistance) + " km more");
            }
        }

    }

    private void incrementDailyPercentage(double current, double goal) {
        if (current < goal) {
            dailyPercentage += (current / goal) * 100;
        } else {
            dailyPercentage += 100; // if user exceed the goal we ignore the amount of increase goal result
        }
        divisor += 100;   //  total number of score ,if the user ignore any daily goal (like daily water Amount =0 , so this calculation  will not include this goal)
    }

    private void updateGoalPercentage() {
        if (isMyGoalArrived && isCurrentWaterAmountArrived && isCurrentDistanceArrived && isCurrentCaloriesArrived
                && isCurrentCarbsArrived && isCurrentFatArrived && isCurrentProteinArrived) {
            if (goalCalories != 0) {
                incrementDailyPercentage(currentCalories, goalCalories);
            }
            if (goalCarbs != 0) {
                incrementDailyPercentage(currentCarbs, goalCarbs);
            }
            if (goalFat != 0) {
                incrementDailyPercentage(currentFat, goalFat);
            }
            if (goalProtein != 0) {
                incrementDailyPercentage(currentProtein, goalProtein);
            }
            if (goalDistance != 0) {
                incrementDailyPercentage(currentDistance, goalDistance);
            }
            if (goalWaterAmount != 0) {
                incrementDailyPercentage(currentWaterAmount, goalWaterAmount);
            }
            if (divisor != 0) {
                if (dailyPercentage < divisor) {
                    double result=(dailyPercentage / divisor) * 100;
                    dailyGoalPercentageTextView.setText((int) result + "%");
                    setGoalProgressAnimation(100, result);
                } else {
                    dailyGoalPercentageTextView.setText("100%");
                    setGoalProgressAnimation(100, 100);
                }
                if (goalWeight!=0&&currentWeight!=goalWeight){
                    if (currentWeight<goalWeight){
                        dailyPercentage+=(currentWeight/goalWeight)*100;
                    }else {
                        dailyPercentage+=(100-(goalWeight/currentWeight)*100);
                    }
                    divisor+=100;
                    mainGoalPercentageTextView.setText((int)((dailyPercentage/divisor)*100)+"%");
                    mainGoalProgress.setProgress((int)((dailyPercentage/divisor)*100));
                }else {
                    if (dailyPercentage<divisor){
                        mainGoalPercentageTextView.setText((int)((dailyPercentage/divisor)*100)+"%");
                        mainGoalProgress.setProgress((int)((dailyPercentage/divisor)*100));
                    }else{
                        mainGoalPercentageTextView.setText("100%");
                        mainGoalProgress.setProgress(100);
                    }
                }
                divisor=0;
                dailyPercentage=0;

            }
        }
    }


}