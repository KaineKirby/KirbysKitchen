package android.project.kirbyskitchen.Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//creating a class that handles the recipe info table and its relevant operations
public class DatabaseRecipeActivity extends SQLiteOpenHelper {
    public DatabaseRecipeActivity(Context context) {
        super(context, "recipeInfo.db", null, 2);
    }
    //defining variables for the various attributes of the recipe object
    private static final String recipeID = "id";
    private static final String recipeName = "name";
    private static final String recipeYield = "yield";
    private static final String recipeIngredients = "ingredients";
    private static final String recipeDirections = "directions";
    private static final String recipeEquipment = "equipment";
    //create the table in an onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table recipes(id integer primary key autoincrement, name, yield, ingredients, directions, equipment)");
    }

    //create an onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists recipes");
    }

    //create a method that handles adding a recipe to the database
    public void add(Recipe recipe) {
        SQLiteDatabase recipeDatabase = this.getWritableDatabase();
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(recipeName, recipe.getName());
        recipeValues.put(recipeYield, recipe.getYield());
        recipeValues.put(recipeIngredients, recipe.getIngredients());
        recipeValues.put(recipeDirections, recipe.getDirections());
        recipeValues.put(recipeEquipment, recipe.getEquipment());

        recipeDatabase.insert("recipes",null, recipeValues);
        recipeDatabase.close();
    }

    //create a method that handles retrieving a recipe
    public Recipe retrieveRecipe(int id) {
        SQLiteDatabase recipeDatabase = this.getReadableDatabase();
        Recipe currRecipe;
        Cursor c = recipeDatabase.query("recipes",
                new String[]{recipeID, recipeName, recipeYield, recipeIngredients, recipeDirections, recipeEquipment},
                recipeID + "=?", new String[]{String.valueOf(id)},null, null, null, null);

        //if the cursor is not null pull the recipe information at each column for each attribute
        if(c != null) {
            c.moveToFirst();
            currRecipe = new Recipe(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5));
            c.close();
            return currRecipe;
        }
        else{
            return null;
        }
    }

    //create a method that handles retrieving the list of recipes
    public List<Recipe> retrieveRecipeList() {
        SQLiteDatabase recipeDatabase = this.getReadableDatabase();
        List<Recipe> recipeList = new ArrayList<>();

        Cursor c = recipeDatabase.rawQuery("SELECT * FROM recipes", null);

        //for each recipe set the information that it corresponds to
        if(c.moveToFirst()) {
            do{
                Recipe currRecipe = new Recipe();
                currRecipe.setId(Integer.parseInt(c.getString(0)));
                currRecipe.setName(c.getString(1));
                currRecipe.setYield(c.getString(2));
                currRecipe.setIngredients(c.getString(3));
                currRecipe.setDirections(c.getString(4));
                currRecipe.setEquipment(c.getString(5));
                recipeList.add(currRecipe);
            } while (c.moveToNext());
        }
        //return the array list once it has been filled
        return recipeList;
    }
    //Create a method that counts the amount of recipes within the database
    public int countRecipes() {
        SQLiteDatabase recipeDatabase = getReadableDatabase();
        Cursor c = recipeDatabase.rawQuery("SELECT * FROM recipes", null);
        return c.getCount();
    }
    //Create a method that allows the removal of a recipe to be performed
    public void deleteRecipeInfo(Recipe recipe) {
        SQLiteDatabase recipeDatabase = getWritableDatabase();
        recipeDatabase.delete("recipes", recipeID + " = ?", new String[]{String.valueOf(recipe.getId())});
        recipeDatabase.close();
    }

    //create a method that allows a recipe to be updated
    public int updateRecipeInfo(Recipe recipe) {
        SQLiteDatabase recipeDatabase = getWritableDatabase();
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(recipeName, recipe.getName());
        recipeValues.put(recipeYield, recipe.getYield());
        recipeValues.put(recipeIngredients, recipe.getIngredients());
        recipeValues.put(recipeDirections, recipe.getDirections());
        recipeValues.put(recipeEquipment, recipe.getEquipment());

        return recipeDatabase.update("recipes", recipeValues,  recipeID + " = ?", new String[]{String.valueOf(recipe.getId())});
    }
}
