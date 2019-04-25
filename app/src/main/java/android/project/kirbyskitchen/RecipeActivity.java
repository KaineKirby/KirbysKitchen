package android.project.kirbyskitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private ListView recipesList;
    private DatabaseRecipeActivity recipeDatabase;
    //private Button add;
    private List<Recipe> recipes;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        recipeDatabase = new DatabaseRecipeActivity(context);

        recipesList = findViewById(R.id.recipesList);

        recipes = new ArrayList<>();
        recipes = recipeDatabase.retrieveRecipeList();

        //Create sample recipes
        Recipe pizza = new Recipe("Pepperoni",
                "Makes 6 servings (serving size: 1 slice)",

                "1 cup tomato-and-basil pasta sauce, " +
                "1 (10-oz.) package prebaked whole wheat thin Italian pizza crust," +
                " 1/4 cup turkey pepperoni slices (about 24), " +
                "1 1/2 cups (6oz.) part-skim mozzarella cheese",

                "Step 1: Spoon tomato-and-basil pasta sauce evenly over crust," +
                        " leaving a 1-inch border around edges. Top with half of " +
                        "pepperoni slices. Sprinkle with cheese. " +
                        "Top with remaining pepperoni." +
                "Step 2: Bake pizza at 450° directly on oven rack 11 to 12 minutes " +
                        "or until crust is golden and cheese is melted. Cut into 6 slices." +
                        " Serve immediately.",
                "Pizza cutter, Pizza pan, and traditional oven");
        recipeDatabase.add(pizza);
        recipes = recipeDatabase.retrieveRecipeList();


        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.adapter_view_layout, (ArrayList<Recipe>) recipes);
        recipesList.setAdapter(adapter);

        //Create arrays to hold the values of the recipes
        final Integer[] recipeIDs = new Integer[recipes.size()];
        final String[] recipeNames = new String[recipes.size()];
        final String[] recipeYields = new String[recipes.size()];
        final String[] recipeIngredients = new String[recipes.size()];
        final String[] recipeDirections = new String[recipes.size()];
        final String[] recipeEquipment = new String[recipes.size()];

        //populate arrays
        for(int i = 0; i < recipes.size(); i++) {
            recipeIDs[i] = recipes.get(i).getId();

            recipeNames[i] = recipes.get(i).getName();
            recipeYields[i] = recipes.get(i).getYield();
            recipeIngredients[i] = recipes.get(i).getIngredients();
            recipeDirections[i] = recipes.get(i).getDirections();
            recipeEquipment[i] = recipes.get(i).getEquipment();
        }

        //Create an adapter that handles showing each recipe's information
//        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, recipeNames) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//
//                text1.setText(recipeNames[position]);
//                text2.setText("Phone: " + recipeYields[position] + "\nAddress: " + recipeDirections[position] + "\nEmail: " + recipeEquipment[position]);
//                return view;
//            }
//        };
//        recipesList.setAdapter(adapter);

        //Passing values to next activity if a modify is performed
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            case R.id.logoutBtn:
                finish();
                System.exit(0);
                return true;
        }
        return true;
    }
}
