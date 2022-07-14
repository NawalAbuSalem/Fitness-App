package com.nns.youcan.Views.AddMeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nns.youcan.DataBase.DailyEatenFoodDataBase;
import com.nns.youcan.DataBase.FoodRepository;
import com.nns.youcan.Models.DailyEatenFood;
import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.RecipesModel.Recipe;

import com.nns.youcan.Models.MyMeal;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.FoodDataBaseViewModel;
import com.nns.youcan.ViewModel.GoogleFitAPIViewModel;
import com.nns.youcan.ViewModel.MealViewModel;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.nns.youcan.Views.Recipe.RecipeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity implements AddManualBottomSheet.AddMealManual,SearchRecyclerAdapter.OnRecycleritemListener  {
    public static final String MEAL_TYPE = "meal type";
    public static final String SELECTED_MEAL ="selectedMeal" ;
    private static final int RECIPE_REQUEST_CODE = 0;
    private AddManualBottomSheet bottomSheet;
    private  Bundle bundle;
    private RecyclerView breakfastRecyclerView;
    private RecyclerView lunchRecyclerView;
    private RecyclerView snacksRecyclerView;
    private RecyclerView dinnerRecyclerView;
    private MealsRecyclerAdapter breakfastAdapter;
    private MealsRecyclerAdapter lunchAdapter;
    private MealsRecyclerAdapter snacksAdapter;
    private MealsRecyclerAdapter dinnerAdapter;
    private MaterialCardView nutritionCard;
    private MaterialCardView breakfastCard;
    private MaterialCardView lunchCard;
    private MaterialCardView snacksCard;
    private MaterialCardView dinnerCard;
    private LiveData<List<Recipe>> liveData ;
    private ProgressBar waitProgressBar;
    private TextView caloriesGainText;
    private TextView caloriesBurnedText;
    private TextView fatText;
    private TextView protienText;
    private TextView carbsText;
    private TextView remainedCaloriesText;
    private UsersFireStoreViewModel fireStoreViewModel;
    View[]intakeProgress;
    FoodDataBaseViewModel foodDataBaseViewModel;
    GoogleFitAPIViewModel fitAPIViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calroies_intake);
         foodDataBaseViewModel=new FoodDataBaseViewModel(this);
         fireStoreViewModel=new UsersFireStoreViewModel();
         fitAPIViewModel=new GoogleFitAPIViewModel(this);
        intakeProgress= new View[]{findViewById(R.id.vi1),
                findViewById(R.id.vi2),
                findViewById(R.id.vi3),
                findViewById(R.id.vi4),
                findViewById(R.id.vi5),
                findViewById(R.id.vi6),
                findViewById(R.id.vi7),
                findViewById(R.id.vi8),
                findViewById(R.id.vi9),
                findViewById(R.id.vi10),
                findViewById(R.id.vi11),
                findViewById(R.id.vi12),
                findViewById(R.id.vi13),
                findViewById(R.id.vi14),
                findViewById(R.id.vi15),
                findViewById(R.id.vi16),
                findViewById(R.id.vi17),
                findViewById(R.id.vi18),
                findViewById(R.id.vi19),
                findViewById(R.id.vi20),
                findViewById(R.id.vi21),
                findViewById(R.id.vi22),
                findViewById(R.id.vi23),
                findViewById(R.id.vi24)};
        caloriesGainText=findViewById(R.id.intake_gained_calories);
        caloriesBurnedText=findViewById(R.id.intake_burned_calories);
        remainedCaloriesText=findViewById(R.id.intake_calories_left_txt);
        fatText=findViewById(R.id.intake_fat);
        protienText=findViewById(R.id.intake_protien);
        carbsText=findViewById(R.id.intake_carbs);
        caloriesBurnedText=findViewById(R.id.intake_burned_calories);
        breakfastRecyclerView=findViewById(R.id.recyclerView_breakfast);
        lunchRecyclerView=findViewById(R.id.recyclerView_lunchs);
        snacksRecyclerView=findViewById(R.id.recyclerView_snacks);
        dinnerRecyclerView=findViewById(R.id.recyclerView_dinner);
        breakfastCard=findViewById(R.id.breakfast_card);
        lunchCard=findViewById(R.id.lunch_card);
        snacksCard=findViewById(R.id.snacks_card);
        dinnerCard=findViewById(R.id.dinner_card);
        nutritionCard=findViewById(R.id.nutrition_card);
        MutableLiveData<List<MyMeal>>breakfastLiveData=foodDataBaseViewModel.getBreakfastMeals();
        breakfastLiveData.observe(this, new Observer<List<MyMeal>>() {
            @Override
            public void onChanged(List<MyMeal> myMeals) {
                breakfastAdapter=new MealsRecyclerAdapter(myMeals);
                setAdapter(breakfastRecyclerView,breakfastAdapter);
            }
        });
        MutableLiveData<List<MyMeal>>lunchLiveData=foodDataBaseViewModel.getLaunchMeals();
        lunchLiveData.observe(this, new Observer<List<MyMeal>>() {
            @Override
            public void onChanged(List<MyMeal> myMeals) {
                lunchAdapter=new MealsRecyclerAdapter(myMeals);
                setAdapter(lunchRecyclerView,lunchAdapter);
            }
        });
        MutableLiveData<List<MyMeal>>snacksLiveData=foodDataBaseViewModel.getSnacksMeals();
        snacksLiveData.observe(this, new Observer<List<MyMeal>>() {
            @Override
            public void onChanged(List<MyMeal> myMeals) {
                snacksAdapter=new MealsRecyclerAdapter(myMeals);
                setAdapter(snacksRecyclerView,snacksAdapter);
            }
        });
        MutableLiveData<List<MyMeal>>dinnerLiveData=foodDataBaseViewModel.getDinnerMeals();
        dinnerLiveData.observe(this, new Observer<List<MyMeal>>() {
            @Override
            public void onChanged(List<MyMeal> myMeals) {
                dinnerAdapter=new MealsRecyclerAdapter(myMeals);
                setAdapter(dinnerRecyclerView,dinnerAdapter);
            }
        });
        MutableLiveData<Double>caloriesIntakeLiveDate=foodDataBaseViewModel.getTotalCalories();
        caloriesIntakeLiveDate.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                caloriesGainText.setText(String.valueOf( (int)aDouble.doubleValue()));
                MutableLiveData<MyGoal>myGoalLiveDate=fireStoreViewModel.getMyGoal();
                myGoalLiveDate.observe(AddMealActivity.this, new Observer<MyGoal>() {
                    @Override
                    public void onChanged(MyGoal myGoal) {
                        double remainedCalories=Double.parseDouble(myGoal.getCalories())-aDouble;
                        if (remainedCalories>=0){
                            remainedCaloriesText.setText(String.valueOf((int)remainedCalories));
                        }
                    }
                });

            }
        });
        MutableLiveData<String>caloriesBurnedLiveData=fitAPIViewModel.getCaloriesExpanded();
        caloriesBurnedLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                caloriesBurnedText.setText(String.valueOf((int) Double.parseDouble(s)));
            }
        });
        MutableLiveData<Double>fatLiveDate=foodDataBaseViewModel.getTotalFat();
        fatLiveDate.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
               fatText.setText(String.valueOf((int)aDouble.doubleValue()));
                MutableLiveData<MyGoal>myGoalLiveDate=fireStoreViewModel.getMyGoal();
                myGoalLiveDate.observe(AddMealActivity.this, new Observer<MyGoal>() {
                    @Override
                    public void onChanged(MyGoal myGoal) {
                       if (Double.parseDouble(myGoal.getFat())>0){
                           refreshFatProgress(aDouble.doubleValue(),Double.parseDouble(myGoal.getFat()));
                       }
                    }
                });
            }
        });
        MutableLiveData<Double>carbsLiveDate=foodDataBaseViewModel.getTotalCarbs();
        carbsLiveDate.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                carbsText.setText(String.valueOf((int)aDouble.doubleValue()));
                MutableLiveData<MyGoal>myGoalLiveDate=fireStoreViewModel.getMyGoal();
                myGoalLiveDate.observe(AddMealActivity.this, new Observer<MyGoal>() {
                    @Override
                    public void onChanged(MyGoal myGoal) {
                        if (Double.parseDouble(myGoal.getCarbs())>0){
                            refreshCarbsProgress(aDouble.doubleValue(),Double.parseDouble(myGoal.getCarbs()));
                        }
                    }
                });
            }
        });
        MutableLiveData<Double>proteinLiveDate=foodDataBaseViewModel.getTotalProtein();
        proteinLiveDate.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                protienText.setText(String.valueOf((int)aDouble.doubleValue()));
                MutableLiveData<MyGoal>myGoalLiveDate=fireStoreViewModel.getMyGoal();
                myGoalLiveDate.observe(AddMealActivity.this, new Observer<MyGoal>() {
                    @Override
                    public void onChanged(MyGoal myGoal) {
                        if (Double.parseDouble(myGoal.getProtein())>0){
                            refreshProteinProgress(aDouble.doubleValue(),Double.parseDouble(myGoal.getProtein()));
                        }
                    }
                });
            }
        });
        bottomSheet=new AddManualBottomSheet();
        addManualMeal();
        final List<Recipe> mealList=new ArrayList<>();
        final RecyclerView recyclerView=findViewById(R.id.search_recyclerview);
        waitProgressBar=findViewById(R.id.pro);
        final SearchRecyclerAdapter recyclerAdapter=new SearchRecyclerAdapter(mealList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MealViewModel viewModel=new MealViewModel();
        SearchView searchView=findViewById(R.id.search_view);
        recyclerView.setAdapter(recyclerAdapter);
        liveData=viewModel.getMeals("");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hideProgress();
                recyclerAdapter.clearPervious();
                showCards();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                liveData=viewModel.getMeals(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                        hideCard();
                if (s.equals("")){
                    hideProgress();
                    recyclerAdapter.clearPervious();
                }else {
                    showProgress();
                    liveData=viewModel.getMeals(s);
                }

                return false;
            }
        });

        liveData.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> myMeals) {
                recyclerAdapter.clearPervious();
                recyclerAdapter.filter(myMeals);
                hideProgress();
            }
        });

    }

    private void refreshCarbsProgress(double carbsIntake, double goalcarbs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio= (int) ((carbsIntake/goalcarbs) *8)+8;
                if (fillRatio>16){
                    fillRatio=16;
                }
                for (int i=8;i<fillRatio;i++){
                    try {
                        Thread.sleep(20);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (intakeProgress[finalI]!=null)
                                intakeProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_gold));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private void refreshProteinProgress(double proteinIntake, double goalProtein) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio= (int) ((proteinIntake/goalProtein) *8)+16;
                if (fillRatio>24){
                    fillRatio=24;
                }
                for (int i=16;i<fillRatio;i++){
                    try {
                        Thread.sleep(20);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (intakeProgress[finalI]!=null)
                                intakeProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_green));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void refreshFatProgress(double doubleValue, double fat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio= (int) ((doubleValue/fat) *8);
                if (fillRatio>8){
                    fillRatio=8;
                }
                for (int i=0;i<fillRatio;i++){
                    try {
                        Thread.sleep(20);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (intakeProgress[finalI]!=null){
                                    intakeProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_red));
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

    private void addManualMeal() {
        findViewById(R.id.add_maunal_breakfast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle=new Bundle();
                bundle.putInt(MEAL_TYPE,0);
                bottomSheet.setArguments(bundle);
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());

            }
        });
        findViewById(R.id.add_maunal_lunch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle=new Bundle();
                bundle.putInt(MEAL_TYPE,1);
                bottomSheet.setArguments(bundle);
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
            }
        });
        findViewById(R.id.add_maunal_snacks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle=new Bundle();
                bundle.putInt(MEAL_TYPE,2);
                bottomSheet.setArguments(bundle);
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
            }
        });
        findViewById(R.id.add_maunal_dinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle=new Bundle();
                bundle.putInt(MEAL_TYPE,3);
                bottomSheet.setArguments(bundle);
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
            }
        });

    }

    private void setAdapter(RecyclerView recyclerView,MealsRecyclerAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addNewManualMeal(MyMeal myMeal) {
        setNewitem(myMeal);
        bottomSheet.dismiss();

    }
    private void showCards(){
        nutritionCard.setVisibility(View.VISIBLE);
        breakfastCard.setVisibility(View.VISIBLE);
        lunchCard.setVisibility(View.VISIBLE);
        snacksCard.setVisibility(View.VISIBLE);
        dinnerCard.setVisibility(View.VISIBLE);
    }
    private void hideCard() {
        nutritionCard.setVisibility(View.GONE);
        breakfastCard.setVisibility(View.GONE);
        lunchCard.setVisibility(View.GONE);
        snacksCard.setVisibility(View.GONE);
        dinnerCard.setVisibility(View.GONE);
    }
    private void showProgress(){
        if (waitProgressBar!=null&&waitProgressBar.getVisibility()== View.GONE){
            waitProgressBar.setVisibility(View.VISIBLE);
        }
    }
    private void hideProgress(){
        if (waitProgressBar!=null&&waitProgressBar.getVisibility()==View.VISIBLE){
            waitProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRecyclerSearchItemListener(Recipe recipe) {
       String ingredientLines="";
       for (int i=0;i<recipe.getIngredientLines().size();i++){
           ingredientLines+=recipe.getIngredientLines().get(i);
           ingredientLines+="\n";
       }
        MyMeal meal=new MyMeal(recipe.getLabel(),
            recipe.getCalories(),
            recipe.getTotalNutrients().getFAT().getQuantity(),
            recipe.getTotalNutrients().getPROCNT().getQuantity(),
            recipe.getTotalNutrients().getCHOCDF().getQuantity(),
             recipe.getYield(),
             recipe.getImage(),
                ingredientLines);
       Intent intent=new Intent(this, RecipeActivity.class);
       intent.putExtra(SELECTED_MEAL,meal);
        startActivityForResult(intent,RECIPE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RECIPE_REQUEST_CODE){
            if (resultCode==RESULT_OK){
                MyMeal newShearcedMeal= (MyMeal) data.getSerializableExtra(RecipeActivity.SUBMITTED_MEAL);
                setNewitem(newShearcedMeal);

            }
        }
    }

    private void setNewitem(MyMeal myMeal) {
        foodDataBaseViewModel.addNewMeal(myMeal);
    }



}
