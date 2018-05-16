package com.bookinghotels.app.FragmentsMap.User.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class UserDataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "User.db";
    private static String TABLE_NAME = "UserTable";
    private static String ID_USER = "IDUser";
    private static String USER_NAME = "Name";
    private static String USER_PRENAME = "Prename";
    private static String EMAIL = "Email";
    private static String PASSWORD = "Password";

    private static int DB_VERSION = 1;


    public UserDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_PRENAME + " TEXT,"
            + EMAIL + " TEXT," + PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public void registerNewUser(String userName, String userPrename, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PRENAME, userPrename);
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        long id = database.insert(TABLE_NAME, null, values);
        database.close();
        Log.d(TAG, String.format("Utilizatorul cu numele " + USER_NAME + " " + USER_PRENAME + " s-a inregistrat cu succes"));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        onCreate(sqLiteDatabase);
    }
    public boolean checkUserOnLogin(String email, String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                                                   new String[] {ID_USER},
                                            EMAIL+" =?"+" AND "+PASSWORD+" =?",
                                                    new String[]{email,password},
                                                        null,null,ID_USER);
        cursor.close();
        if(cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }

}
