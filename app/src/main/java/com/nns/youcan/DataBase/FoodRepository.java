package com.nns.youcan.DataBase;

import android.content.Context;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nns.youcan.Models.DailyEatenFood;
import com.nns.youcan.Models.MyMeal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class FoodRepository {
   private static FoodRepository foodRepository;
   private static Context context;
   private static String currentDate ;
   private static DailyEatenFoodDataBase foodDataBase;
   private static FirebaseUser currentUser ;
    private FoodRepository() {
    }
    public  static FoodRepository getInstance(Context mcontext){
        if (foodRepository==null) {
            foodRepository = new FoodRepository();
        }
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        context=mcontext;
        foodDataBase=DailyEatenFoodDataBase.getInstance(context);
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return foodRepository;
    }
    public void addNewMeal(MyMeal myMeal){
        DailyEatenFood food=new DailyEatenFood();
        food.setDate(currentDate);
        food.setMyMeal(myMeal);
        food.setUserID(currentUser.getUid());
        foodDataBase.getEatenFoodDao().addNewDailyFood(food);
    }
    public MutableLiveData<List<MyMeal>> getBreakfastMeals(){
        MutableLiveData<List<MyMeal>>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                List<MyMeal>breakfastMeals=new ArrayList<>();
                for (int i=0;i<dailyEatenFoods.size();i++){
                    if (dailyEatenFoods.get(i).getMyMeal()!=null&&dailyEatenFoods.get(i).getMyMeal().getType()==0){
                        breakfastMeals.add(dailyEatenFoods.get(i).getMyMeal());
                    }

                }
                mLiveData.setValue(breakfastMeals);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<List<MyMeal>> getLaunchMeals(){
        MutableLiveData<List<MyMeal>>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                List<MyMeal>launchMeals=new ArrayList<>();
                for (int i=0;i<dailyEatenFoods.size();i++){
                    if (dailyEatenFoods.get(i).getMyMeal()!=null&&dailyEatenFoods.get(i).getMyMeal().getType()==1){
                        launchMeals.add(dailyEatenFoods.get(i).getMyMeal());
                    }

                }
                mLiveData.setValue(launchMeals);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<List<MyMeal>> getSnacksMeals(){
        MutableLiveData<List<MyMeal>>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                List<MyMeal>snacksMeals=new ArrayList<>();
                for (int i=0;i<dailyEatenFoods.size();i++){
                    if (dailyEatenFoods.get(i).getMyMeal()!=null&&dailyEatenFoods.get(i).getMyMeal().getType()==2){
                        snacksMeals.add(dailyEatenFoods.get(i).getMyMeal());
                    }

                }
                mLiveData.setValue(snacksMeals);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<List<MyMeal>> getDinnerMeals(){
        MutableLiveData<List<MyMeal>>mLiveData=new MutableLiveData<>();

        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                List<MyMeal>dinnerMeals=new ArrayList<>();
                for (int i=0;i<dailyEatenFoods.size();i++){
                    if (dailyEatenFoods.get(i).getMyMeal()!=null&&dailyEatenFoods.get(i).getMyMeal().getType()==3){
                        dinnerMeals.add(dailyEatenFoods.get(i).getMyMeal());
                    }
                }
                mLiveData.setValue(dinnerMeals);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<Double> getTotalCalories(){
        MutableLiveData<Double>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                double caloriesIntake=0;
                if (dailyEatenFoods!=null){
                    for (int i=0;i<dailyEatenFoods.size();i++){
                        if (dailyEatenFoods.get(i).getMyMeal()!=null){
                            caloriesIntake+=(dailyEatenFoods.get(i).getMyMeal().getCalories())/dailyEatenFoods.get(i).getMyMeal().getServing();
                        }

                    }
                }

                mLiveData.setValue(caloriesIntake);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<Double> getTotalFat(){
        MutableLiveData<Double>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
               double fatIntake=0;
                if (dailyEatenFoods!=null){
                    for (int i=0;i<dailyEatenFoods.size();i++){
                        if (dailyEatenFoods.get(i).getMyMeal()!=null){
                            fatIntake+=(dailyEatenFoods.get(i).getMyMeal().getFat())/dailyEatenFoods.get(i).getMyMeal().getServing();
                        }
                    }
                }
                mLiveData.setValue(fatIntake);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<Double> getTotalCarbs(){
        MutableLiveData<Double>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                double carbsIntake=0;
                if (dailyEatenFoods!=null){
                    for (int i=0;i<dailyEatenFoods.size();i++){
                        if (dailyEatenFoods.get(i).getMyMeal()!=null){
                            carbsIntake+=(dailyEatenFoods.get(i).getMyMeal().getCarbs())/dailyEatenFoods.get(i).getMyMeal().getServing();

                        }
                    }
                }
                mLiveData.setValue(carbsIntake);
            }
        });
        return mLiveData;
    }
    public MutableLiveData<Double> getTotalProtein(){
        MutableLiveData<Double>mLiveData=new MutableLiveData<>();
        LiveData<List<DailyEatenFood>>liveData=foodDataBase.getEatenFoodDao().getDailyEatenFood(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<List<DailyEatenFood>>() {
            @Override
            public void onChanged(List<DailyEatenFood> dailyEatenFoods) {
                 double proteinIntake=0;
                if (dailyEatenFoods!=null){
                    for (int i=0;i<dailyEatenFoods.size();i++){
                        if (dailyEatenFoods.get(i).getMyMeal()!=null){
                            proteinIntake+=(dailyEatenFoods.get(i).getMyMeal().getProtien())/dailyEatenFoods.get(i).getMyMeal().getServing();
                        }
                    }
                }
                mLiveData.setValue(proteinIntake);
            }
        });
        return mLiveData;
    }
    public void addNewAmountOfWater(double waterAmount){
        DailyEatenFood food=new DailyEatenFood();
        food.setDate(currentDate);
        food.setUserID(currentUser.getUid());
        food.setWaterDrankAmount(waterAmount);
        foodDataBase.getEatenFoodDao().addNewDailyFood(food);
    }
    public int updateDailyAmountOfWater(double waterAmount){
        return foodDataBase.getEatenFoodDao().updateDailyFood(waterAmount,currentDate,currentUser.getUid());
    }
    public MutableLiveData<Double> getDailyAmountOfWater(){
        MutableLiveData<Double>mLiveData=new MutableLiveData<>();
        LiveData<Double>liveData=foodDataBase.getEatenFoodDao().getDailyWaterAmount(currentDate,currentUser.getUid());
        liveData.observe((LifecycleOwner) context, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble!=null){
                    mLiveData.setValue(aDouble);
                }else {
                    mLiveData.setValue(0.0);
                }

            }
        });
        return mLiveData;
    }

}
