package com.bookinghotels.app.MainActivity.User.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bookinghotels.app.MainActivity.Buildings.Buildings;
import com.bookinghotels.app.MainActivity.Type;
import com.bookinghotels.app.MainActivity.User.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Informatia despre Baza de date
    private static String DB_NAME = "BookingHotels.db";
    private static int DB_VERSION = 1;

    // Tabelul cu utilizatori
    private static String USER_TABLE_NAME = "UserTable";
    private static String USER_ID = "IDUser";
    private static String USER_NAME = "Name";
    private static String USER_PRENAME = "Prename";
    private static String USER_EMAIL = "Email";
    private static String USER_PASSWORD = "Password";
    // Tabelul cu utilizatori

    // Tabelul cu locuinte
    private static String BUILDING_TABLE_NAME = "BuildingsTable";
    private static String BUILDING_ID = "Id_build";
    private static String BUILDING_TITLE = "Title";
    private static String BUILDING_RATING = "Rating";
    private static String BUILDING_ADDRESS = "Address";
    private static String BUILDING_PRICE = "Price";
    private static String BUILDING_TYPE = "Type";
    // Tabelul cu locuinte

    //Tabelul cu rezervari
    private static String RESERV_TABLE_NAME = "ReservationTable";
    private static String RESERV_ID = "Id_res";
    private static String DATE_IN = "DateIn";
    private static String DATE_OUT = "DateOut";
    private static String GUEST = "Guest";
    //Tabelul cu rezervari

    //Tabelul cu tipuri de locuinte
    private static String TYPE_TABLE_NAME = "TypeTable";
    private static String TYPE_ID = "Id_type";
    private static String TYPE_TITLE = "Title";
    //Tabelul cu tipuri de locuinte

    private SQLiteDatabase sqLiteDatabase;
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /***************************************************************CREAREA TABELELOR*****************************************************************************************/


    private String CREATE_BUILDING_TABLE = "CREATE TABLE " + BUILDING_TABLE_NAME + "(" + BUILDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BUILDING_TITLE + " TEXT," + BUILDING_ADDRESS + " TEXT," +
            BUILDING_RATING + " FLOAT, " +  BUILDING_PRICE + "FLOAT" + ")";
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_PRENAME
            + " TEXT," + USER_EMAIL + " TEXT," + USER_PASSWORD + " TEXT" + ")";
    private String CREATE_TYPE_TABLE = "CREATE TABLE " + TYPE_TABLE_NAME + "(" + TYPE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_TITLE + "TEXT" + ")";
    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private String DROP_BUILDING_TABLE = "DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME;
    private String DROP_TYPE_TABLE = "DROP TABLE IF EXISTS "+ TYPE_TABLE_NAME;
    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/


    /***************************************************************************UTILIZATOR***********************************************************/
    public boolean checkUserOnLogin(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID},
                USER_EMAIL + " =?" + " AND " + USER_PASSWORD + " =?",
                new String[]{email, password},
                null, null, USER_ID);
        cursor.close();
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void registerNewUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.Name);
        values.put(USER_PRENAME, user.Prename);
        values.put(USER_EMAIL, user.Email);
        values.put(USER_PASSWORD, user.Password);
        long id = database.insert(USER_TABLE_NAME, null, values);
        database.close();
        Log.d(TAG, String.format("Utilizatorul cu numele " + USER_NAME + " " + USER_PRENAME + " s-a inregistrat cu succes"));
    }

    /*******************************************************************************UTILIZATOR************************************************************/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUILDING_TABLE);
        sqLiteDatabase.execSQL(CREATE_TYPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        sqLiteDatabase.execSQL(DROP_BUILDING_TABLE);
        sqLiteDatabase.execSQL(DROP_TYPE_TABLE);
        onCreate(sqLiteDatabase);
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/
    public List<Buildings> getHotels() {
        List<Buildings> listOfBuilding = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BuildingsTable", null);
        if (cursor.moveToFirst()) {
            do {
                Buildings buildings = new Buildings();
                buildings.Id_building = cursor.getString(0);
                buildings.Title = cursor.getString(1);
                buildings.Rating = cursor.getFloat(2);
                buildings.Address = cursor.getString(3);

                buildings.Room = cursor.getInt(4);
                buildings.Price = cursor.getFloat(5);
                buildings.MaxPers = cursor.getInt(6);
                buildings.Type = cursor.getString(7);

                listOfBuilding.add(buildings);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfBuilding;

    }
    public void insertHotels()
    {
        ContentValues values = new ContentValues();
        values.put("Title","");
        values.put("Rating","");
        values.put("Address","");
        values.put("Price","");
        values.put("Type","");

        sqLiteDatabase.insert("BuildingsTable",null,values);
        sqLiteDatabase.close();
    }
    public List<Buildings> getHotelsByFilter(Buildings bFilter) {
        List<Buildings> listOfBuilding = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BuildingsTable WHERE Type = ? ", new String[]{bFilter.Type});
        if (cursor.moveToFirst()) {
            do {
                Buildings buildings = new Buildings();
                buildings.Id_building = cursor.getString(0);
                buildings.Title = cursor.getString(1);
                buildings.Rating = cursor.getFloat(2);
                buildings.Address = cursor.getString(3);

                buildings.Room = cursor.getInt(4);
                buildings.Price = cursor.getFloat(5);
                buildings.MaxPers = cursor.getInt(6);
                buildings.Type = cursor.getString(7);

                listOfBuilding.add(buildings);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfBuilding;

    }
    /*************************************************************************HOTELS ACTIONS*********************************************************/


    /*************************************************************************TYPE ACTIONS*********************************************************/

    public List<Type> getAllTypes()
    {
        List<Type> listOfType= new ArrayList<>();

        Cursor cursorType = sqLiteDatabase.rawQuery("SELECT * FROM TypeTable",null);
        if(cursorType.moveToFirst())
        {
            do{
                Type type = new Type();
                type.IDType = cursorType.getInt(0);
                type.Type = cursorType.getString(1);

                listOfType.add(type);

            }
            while(cursorType.moveToNext());
                sqLiteDatabase.close();
        }
        return listOfType;

    }
    /*************************************************************************TYPE ACTIONS*********************************************************/
}
