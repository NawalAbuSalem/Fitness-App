package com.nns.youcan.Views.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nns.youcan.Models.MyMeal;

import com.nns.youcan.R;
import com.nns.youcan.Views.AddMeal.AddMealActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity implements RecipeBottomSheet.AddSearchedMeal {
    public static final String RECIPE_INGGREDIENT = "recipeIngredient";
    public static final String RECIPE_SERVING ="recipeServing" ;
    public static final String SUBMITTED_MEAL ="submittedMeal" ;
    private  MyMeal selectedMeal;
   private ImageView recipeImage;
   private TextView recipeCalroies;
   private  TextView recipeFat;
   private TextView recipeName;
   private TextView recipeProtien;
   private TextView recipeCarbs;
   private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeImage=findViewById(R.id.recipe_image);
        recipeCalroies=findViewById(R.id.recipe_calories);
        recipeCarbs=findViewById(R.id.recipe_carbs);
        recipeFat=findViewById(R.id.recipe_fat);
        recipeName=findViewById(R.id.recipe_name);
        recipeProtien=findViewById(R.id.recipe_protien);
        bundle=new Bundle();
        findViewById(R.id.back_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectedMeal= (MyMeal) getIntent().getSerializableExtra(AddMealActivity.SELECTED_MEAL);
        if (selectedMeal!=null){
            Picasso.get().load(selectedMeal.getMealImage()).into(recipeImage);
            recipeName.setText(selectedMeal.getName());
            recipeFat.setText(String.valueOf((int)(Math.ceil(selectedMeal.getFat()))));
            recipeProtien.setText(String.valueOf((int)(Math.ceil(selectedMeal.getProtien()))));
            recipeCarbs.setText(String.valueOf((int)(Math.ceil(selectedMeal.getCarbs()))));
            recipeCalroies.setText(String.valueOf((int)(Math.ceil(selectedMeal.getCalories()))));
        }
        final RecipeBottomSheet bottomSheet=new RecipeBottomSheet();
        findViewById(R.id.main_view_recipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString(RECIPE_INGGREDIENT,selectedMeal.getIngredientLines());
                bundle.putDouble(RECIPE_SERVING,selectedMeal.getServing());
                bottomSheet.setArguments(bundle);
                bottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
            }
        });

    }
// when from searched bottom sheet submit meal
    @Override
    public void onAddSearchMeal(int mealType) {
      selectedMeal.setType(mealType);
      setResult(RESULT_OK,new Intent(this,AddMealActivity.class).putExtra(SUBMITTED_MEAL,selectedMeal));
      finish();
    }
}
