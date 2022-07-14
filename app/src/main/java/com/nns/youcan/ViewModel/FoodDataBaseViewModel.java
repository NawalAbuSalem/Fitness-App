package com.nns.youcan.ViewModel;

import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nns.youcan.DataBase.FoodRepository;

import com.nns.youcan.Models.MyMeal;
import java.util.List;
public class FoodDataBaseViewModel  {
    FoodRepository repository;
    public FoodDataBaseViewModel(Context context) {
        repository =  FoodRepository.getInstance(context);
    }
    public void addNewMeal(MyMeal myMeal){
       repository.addNewMeal(myMeal);
    }
    public MutableLiveData<List<MyMeal>> getBreakfastMeals(){

        return repository.getBreakfastMeals();
    }
    public MutableLiveData<List<MyMeal>> getLaunchMeals(){
        return repository.getLaunchMeals();
    }
    public MutableLiveData<List<MyMeal>> getSnacksMeals(){

        return repository.getSnacksMeals();
    }
    public MutableLiveData<List<MyMeal>> getDinnerMeals(){
        return repository.getDinnerMeals();
    }
    public MutableLiveData<Double> getTotalCalories(){

        return repository.getTotalCalories();
    }
    public MutableLiveData<Double> getTotalFat(){

        return repository.getTotalFat();
    }
    public MutableLiveData<Double> getTotalCarbs(){
        return repository.getTotalCarbs();
    }
    public MutableLiveData<Double> getTotalProtein(){
        return repository.getTotalProtein();
    }
    public void addNewAmountOfWater(double waterAmount){
       repository.addNewAmountOfWater(waterAmount);
    }
    public int updateDailyAmountOfWater(double waterAmount){
        return repository.updateDailyAmountOfWater(waterAmount);
    }
    public MutableLiveData<Double> getDailyAmountOfWater(){
        return repository.getDailyAmountOfWater();
    }
}
