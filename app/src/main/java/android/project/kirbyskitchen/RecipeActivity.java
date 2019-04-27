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

            Recipe salmon = new Recipe("Pan-Roasted Salmon",
                    "\nMakes 4 servings",

                    "\n1 pint grape tomatoes, halved,\n" +
                            "1 medium shallot, thinly sliced,\n" +
                            "1 tablespoon drained capers,\n" +
                            "2 tablespoons red wine vinegar,\n" +
                            "Salt,\n" +
                            "3 tablespoons olive oil\n" +
                            "4 center-cut salmon fillets with skin\n" +
                            "Freshly ground pepper\n" +
                            "1/2 teaspoon ground cumin\n" +
                            "2 tablespoons canola oil\n" +
                            "1 tablespoon minced parsley\n" +
                            "1 tablespoon chopped basil",

                    "\nStep 1: Preheat the oven to 425°. In a bowl, toss the tomatoes with the shallot, capers, vinegar and 1/2 teaspoon of salt.\n" +
                            "\nStep 2: In a medium ovenproof skillet, heat 1 tablespoon of the olive oil. Season the salmon with salt and pepper and add it to the skillet, skin side up. Cook over moderately high heat until well-browned on the bottom, about 3 minutes. Carefully flip the fillets. Transfer the skillet to the oven and roast until the salmon is cooked through, about 7 minutes. Transfer the fish to plates and pour off any fat in the skillet." +
                            "\nStep 3: Place the skillet over moderate heat and add the tomato mixture along with the cumin, canola oil and the remaining 2 tablespoons of olive oil. Cook, scraping up any bits stuck to the skillet, until the tomatoes just soften, about 2 minutes. Pour the sauce over the salmon, sprinkle with the parsley and basil and serve right away.",
                    "\nSkillet, cutting boards, kitchen knives, and traditional oven");
            Recipe chicken = new Recipe("Roast Chicken with Aromatic Jus",
                    "\nMakes 4 servings",

                    "\nOne 3 1/2-pound chicken,\n" +
                            "1/2 small orange, quartered,\n" +
                            "3 garlic cloves, crushed,\n" +
                            "2 scallions, cut crosswise into 2-inch lengths,\n" +
                            "One 1-inch piece of fresh ginger, thinly sliced,\n" +
                            "2 teaspoons kosher salt\n" +
                            "1 teaspoon ground fennel seeds\n" +
                            "2 teaspoons Asian sesame oil\n" +
                            "1/2 cup low-sodium chicken broth\n",

                    "\nStep 1: Preheat the oven to 425°. Set the chicken, breast side up, in a medium roasting pan. Loosely stuff the cavity with the orange quarters, garlic, scallions and ginger. In a small bowl, mix the salt with the fennel and pepper. Rub the chicken with the sesame oil and season all over with the salt mixture.\n" +
                            "\nStep 2: Roast the chicken in the center of the oven for 1 hour, or until the juices run clear and an instant-read thermometer inserted in the inner thigh registers 160°. Carefully scoop the aromatics from the cavity into the roasting pan. Transfer the chicken to a cutting board and let rest for 10 minutes." +
                            "\nStep 3: Meanwhile, squeeze the juice from the orange quarters and discard the peels. Set the roasting pan over moderately high heat. Add the chicken broth and simmer for 1 minute, scraping up the browned bits from the bottom of the pan. Strain the pan juices into a gravy boat or small serving bowl and spoon off the fat. Carve the chicken and serve with the jus.",
                    "\nSkillet, cutting boards, kitchen knives, and traditional oven");
            recipeDatabase.add(pizza);
            recipeDatabase.add(salmon);
            recipeDatabase.add(chicken);
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
