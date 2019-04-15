/*
 * Austin Kirby
 * CS372
 * 04/15/19
 * Project - Kirby's Kitchen
 * Database userInfo Activity
 */

package android.project.kirbyskitchen.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLoginActivity extends SQLiteOpenHelper {
    public DatabaseLoginActivity(Context context) {
        super(context, "userInfo.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username primary key, password)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public boolean validUsername(String username) {
        SQLiteDatabase userDatabase = this.getReadableDatabase();
        Cursor c = userDatabase.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        if(c.getCount() > 0){
            return false;
        }else {
            return true;
        }
    }
     public boolean validLoginCredentials(String username, String password) {
        SQLiteDatabase userDatabase = this.getReadableDatabase();
        Cursor c = userDatabase.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        if(c.getCount() > 0) {
            return true;
        }
        else{
            return false;
        }
     }
    //write a method to insert username and password into table
    public boolean insert(String username, String password) {
        SQLiteDatabase userDatabase = this.getWritableDatabase();
        ContentValues userLogin = new ContentValues();
        userLogin.put("username", username);
        userLogin.put("password", password);
        long entry = userDatabase.insert("user", null, userLogin);
        //check if the entry has content, return boolean accordingly
        if(entry == -1) {
            return false;
        }
        else{
            return true;
        }
    }
}
