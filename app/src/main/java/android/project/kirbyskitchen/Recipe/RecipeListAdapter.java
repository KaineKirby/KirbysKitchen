package android.project.kirbyskitchen.Recipe;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter <Recipe> {
    private static final String TAG = "RecipeListAdapter";
    private Context currContext;
    int currResource;

    public RecipeListAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        currContext = context;
        currResource = resource;
    }
}
