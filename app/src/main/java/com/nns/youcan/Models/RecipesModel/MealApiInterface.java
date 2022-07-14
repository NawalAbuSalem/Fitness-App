package com.nns.youcan.Models.RecipesModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiInterface {

    @GET("search?")
    Call<MealsRecipe> getMeals(@Query("q") String searchedMeal, @Query("app_id") String appId, @Query("app_key") String appKey);
}
