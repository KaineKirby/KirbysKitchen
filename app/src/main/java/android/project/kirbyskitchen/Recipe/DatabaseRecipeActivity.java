package android.project.kirbyskitchen.Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//creating a class that handles the contact info table and its relevant operations
public class DatabaseRecipeActivity extends SQLiteOpenHelper {
    public DatabaseRecipeActivity(Context context) {
        super(context, "recipeInfo.db", null, 2);
    }
    //defining variables for the various attributes of the contact object
    private static final String recipeID = "id";
    private static final String recipeName = "name";
    private static final String recipeYield = "yield";
    private static final String recipeIngredients = "ingredients";
    private static final String recipeDirections = "directions";
    private static final String recipeEquipment = "email";
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

    //create a method that handles adding a contact to the database
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

    //create a method that handles retrieving a contact
    public Recipe retrieveRecipe(int id) {
        SQLiteDatabase contactDatabase = this.getReadableDatabase();
        Recipe currContact;
        Cursor c = contactDatabase.query("contacts",
                new String[]{recipeID, recipeName, recipeYield, recipeIngredients, recipeDirections, recipeEquipment},
                recipeID + "=?", new String[]{String.valueOf(id)},null, null, null, null);

        //if the cursor is not null pull the contact information at each column for each attribute
        if(c != null) {
            c.moveToFirst();
            currContact = new Recipe(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5));
            c.close();
            return currContact;
        }
        else{
            return null;
        }
    }

    //create a method that handles retrieving the list of contacts
    public List<Recipe> retrieveRecipeList() {
        SQLiteDatabase recipeDatabase = this.getReadableDatabase();
        List<Recipe> recipeList = new ArrayList<>();

        Cursor c = recipeDatabase.rawQuery("SELECT * FROM contacts", null);

        //for each contact set the information that it corresponds to
        if(c.moveToFirst()) {
            do{
                Recipe currContact = new Recipe();
                currContact.setId(Integer.parseInt(c.getString(0)));
                currContact.setName(c.getString(1));
                currContact.setYield(c.getString(2));
                currContact.setIngredients(c.getString(3));
                currContact.setDirections(c.getString(4));
                currContact.setEquipment(c.getString(5));
                recipeList.add(currContact);
            } while (c.moveToNext());
        }
        //return the array list once it has been filled
        return recipeList;
    }
    //Create a method that counts the amount of contacts within the database
    public int countRecipes() {
        SQLiteDatabase contactDatabase = getReadableDatabase();
        Cursor c = contactDatabase.rawQuery("SELECT * FROM contacts", null);
        return c.getCount();
    }
    //Create a method that allows the removal of a contact to be performed
    public void deleteContactInfo(Recipe contact) {
        SQLiteDatabase contactDatabase = getWritableDatabase();
        contactDatabase.delete("contacts", recipeID + " = ?", new String[]{String.valueOf(contact.getId())});
        contactDatabase.close();
    }

    //create a method that allows a contact to be updated
    public int updateContactInfo(Recipe contact) {
        SQLiteDatabase contactDatabase = getWritableDatabase();
        ContentValues contactValues = new ContentValues();
        contactValues.put(recipeName, contact.getName());
        contactValues.put(recipeYield, contact.getYield());
        contactValues.put(recipeIngredients, contact.getIngredients());
        contactValues.put(recipeDirections, contact.getDirections());
        contactValues.put(recipeEquipment, contact.getEquipment());

        return contactDatabase.update("contacts", contactValues,  recipeID + " = ?", new String[]{String.valueOf(contact.getId())});
    }
}
