package android.project.kirbyskitchen.Recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.project.kirbyskitchen.R;
import android.project.kirbyskitchen.RecipeActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipeActivity extends AppCompatActivity {
    //creating variables to be used for add class
    EditText nameTxt, yieldTxt, ingredientsTxt, directionsTxt, equipTxt;
    Button addBtn;
    DatabaseRecipeActivity recipeDatabase;
    Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        context = this;
        recipeDatabase = new DatabaseRecipeActivity(context);

        nameTxt = findViewById(R.id.recipeName);
        yieldTxt = findViewById(R.id.recipeYield);
        ingredientsTxt = findViewById(R.id.recipeIngredients);
        directionsTxt = findViewById(R.id.recipeDirections);
        equipTxt = findViewById(R.id.recipeEquipment);

        addBtn = findViewById(R.id.addBtn);

        //add button for submitting the values within the add contact fields
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = nameTxt.getText().toString();
                String recipeYield = "\n" + yieldTxt.getText().toString();
                String recipeIngredients = "\n" + ingredientsTxt.getText().toString();
                String recipeDirections = "\n" + directionsTxt.getText().toString();
                String recipeEquipment = "\n" + equipTxt.getText().toString();

                //if any of the fields are empty then print an error message, if not add them
                if(!TextUtils.isEmpty(recipeName) && !TextUtils.isEmpty(recipeYield) && !TextUtils.isEmpty(recipeIngredients) && !TextUtils.isEmpty(recipeDirections) && !TextUtils.isEmpty(recipeEquipment)) {
                    Recipe recipe = new Recipe(recipeName, recipeYield, recipeIngredients, recipeDirections, recipeEquipment);
                    recipeDatabase.add(recipe);
                    startActivity(new Intent(context, RecipeActivity.class));
                }
                else {
                    Toast invalidAdd = Toast.makeText(getApplicationContext(), "Please enter valid recipe information.", Toast.LENGTH_SHORT);
                    TextView loginView = (TextView) invalidAdd.getView().findViewById(android.R.id.message);
                    if(loginView != null){
                        invalidAdd.setGravity(Gravity.CENTER, 0, 250);
                        loginView.setGravity(Gravity.CENTER);
                        invalidAdd.show();
                    }
                }
            }
        });

    }
}
