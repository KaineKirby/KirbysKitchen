package android.project.kirbyskitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.project.kirbyskitchen.Recipe.AddRecipeActivity;
import android.project.kirbyskitchen.Recipe.DatabaseRecipeActivity;
import android.project.kirbyskitchen.Recipe.Recipe;
import android.project.kirbyskitchen.Recipe.RecipeListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private ListView recipesListView;
    private DatabaseRecipeActivity recipeDatabase;
    public List<Recipe> recipesList;
    //private Button add;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        context = this;
        recipeDatabase = new DatabaseRecipeActivity(context);

        recipesListView = findViewById(R.id.recipesList);

        //Create sample recipes
        if(recipeDatabase.countRecipes() == 0) {
            Recipe pizza = new Recipe("Pepperoni Pizza",
                    "\nMakes 6 servings (serving size: 1 slice)",

                    "\n1 cup tomato-and-basil pasta sauce,\n" +
                            "1 (10-oz.) package prebaked whole wheat thin Italian pizza crust,\n" +
                            "1/4 cup turkey pepperoni slices (about 24),\n" +
                            "1 1/2 cups (6oz.) part-skim mozzarella cheese",

                    "\nStep 1: Spoon tomato-and-basil pasta sauce evenly over crust,\n" +
                            "leaving a 1-inch border around edges. Top with half of " +
                            "pepperoni slices. Sprinkle with cheese. " +
                            "Top with remaining pepperoni.\n" +
                            "Step 2: Bake pizza at 450° directly on oven rack 11 to 12 minutes " +
                            "or until crust is golden and cheese is melted. Cut into 6 slices." +
                            " Serve immediately.",
                    "\nPizza cutter, Pizza pan, and traditional oven");
            recipeDatabase.add(pizza);
        }
        recipesList = recipeDatabase.retrieveRecipeList();

        //Create an adapter that handles showing each recipe's information
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.adapter_view_layout, recipesList);
        recipesListView.setAdapter(adapter);

        //Passing values to next activity if a modify is performed
        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    //creating menu options for logout and add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    //Handle options available on action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenuBtn:
                Intent addIntent = new Intent(context, AddRecipeActivity.class);
                startActivity(addIntent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
