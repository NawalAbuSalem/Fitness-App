package com.nns.youcan.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.nns.youcan.Models.RecipesModel.MealsRecipe;
import com.nns.youcan.Models.RecipesModel.NetworkUtils;
import com.nns.youcan.Models.RecipesModel.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealViewModel {
    private NetworkUtils networkUtils;
    private List<Recipe> myMealList;
    private MutableLiveData<List<Recipe>> listLiveData;

    public MealViewModel() {
        networkUtils= NetworkUtils.getInstance();
        listLiveData=new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getMeals(String searchMealName){
        myMealList=new ArrayList<>();
        Call<MealsRecipe>mealsApiCall=networkUtils.getMealApiInterface().getMeals(searchMealName,"621772cc","6f617d62fa21cc75a611504f3e1d5a00");
        mealsApiCall.enqueue(new Callback<MealsRecipe>() {
            @Override
            public void onResponse(Call<MealsRecipe> call, Response<MealsRecipe> response) {
                if (response.code()==200){
                    for(int i=0;i<response.body().getHits().size();i++){
                        myMealList.add(response.body().getHits().get(i).getRecipe());
                    }

                }
                listLiveData.setValue(myMealList);
            }
            @Override
            public void onFailure(Call<MealsRecipe> call, Throwable t) {
                System.out.println(t.toString());

            }
        });
        return listLiveData;
    }
}
