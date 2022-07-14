package com.nns.youcan.Models.RecipesModel;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkUtils {
    private static final String BASE_URL="https://api.edamam.com/";
    private static NetworkUtils networkUtils;
    private MealApiInterface mealApiInterface;
    private Retrofit retrofit;
     private NetworkUtils(){
         retrofit = new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         mealApiInterface = retrofit.create(MealApiInterface.class);
     }
     public static NetworkUtils getInstance(){
          if (networkUtils==null) {
              networkUtils=new NetworkUtils();
          }
           return  networkUtils;

     }
    public MealApiInterface getMealApiInterface() {

         return mealApiInterface;
    }
}
