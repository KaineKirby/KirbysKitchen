package android.project.kirbyskitchen.Recipe;

import android.content.Context;
import android.project.kirbyskitchen.R;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends ArrayAdapter <Recipe> {
    private static final String TAG = "RecipeListAdapter";
    private Context currContext;

    int currResource;

    public RecipeListAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        currContext = context;
        currResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the recipe's information
        String name = getItem(position).getName();
        String yield = getItem(position).getYield();
        String ingredients = getItem(position).getIngredients();
        String directions = getItem(position).getDirections();
        String equipment = getItem(position).getEquipment();

        //create the recipe object with the information
        Recipe recipe = new Recipe(name, yield, ingredients, directions, equipment);

        LayoutInflater inflater = LayoutInflater.from(currContext);
        convertView = inflater.inflate(currResource, parent, false);

        TextView recipeName = (TextView) convertView.findViewById(R.id.recipe_name);
        TextView recipeYield = (TextView) convertView.findViewById(R.id.recipe_yield);
        TextView recipeIngredients = (TextView) convertView.findViewById(R.id.recipe_ingredients);
        TextView recipeDirections = (TextView) convertView.findViewById(R.id.recipe_directions);
        TextView recipeEquipment = (TextView) convertView.findViewById(R.id.recipe_equipment);

        recipeName.setText(name + "\n");
        recipeYield.setText("Yield: " + yield + "\n");
        recipeIngredients.setText("Ingredients: " + ingredients + "\n");
        recipeDirections.setText("Directions: " + directions + "\n");
        recipeEquipment.setText("Equipment: " + equipment + "\n");

        return convertView;
    }
}
