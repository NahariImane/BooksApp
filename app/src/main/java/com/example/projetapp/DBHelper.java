package com.example.projetapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    // creating the Sqlite DB
    public static final String DBNAME="Login.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    // creating the User table with 4 fields userid, username, password and email
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users(username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Users");
    }

    // insert user data on our DB
    public Boolean insertData(String username, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("email", email);

        long result = db.insert("Users", null, values);
        return result != -1;

    }

    // function updating the username and email
    public Boolean updateusernameemail(String username, String newusername,String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", newusername);
        values.put("email", email);

        Cursor cursor = DB.rawQuery("Select * from Users where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            long result = DB.update("Users", values, "username=?", new String[]{username});
            return result != -1;
        } else {
            return false;
        }
    }

    // function updating the password
    public Boolean updatepassword(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("password", password);

        long result = db.update("Users", values, "username = ?", new String[]{username});
        return result != -1;
    }


    // checking the username
    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    // checking the username and password
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where username=? and password = ?", new String[]{username,password});
        return cursor.getCount() > 0;
    }


    // gets the current user information
    public User getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Users where username=?",new String[]{username});
        User user = new User();
        if(res.moveToFirst()){
            do{
                user.setUsername(res.getString(0));
                user.setPassword(res.getString(1));
                user.setEmail(res.getString(2));

            } while(res.moveToNext());
        }
        res.close();
        db.close();
        return user;
    }

}