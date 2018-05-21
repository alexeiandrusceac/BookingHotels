package com.bookinghotels.app.mainActivity.User.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Buildings.Buildings;
import com.bookinghotels.app.mainActivity.MainActivity;
import com.bookinghotels.app.mainActivity.Type;
import com.bookinghotels.app.mainActivity.User.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Informatia despre Baza de date
    private static String DB_NAME = "BookingHotels";
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
    private static String BUILDING_CHECKIN = "Check_in";
    private static String BUILDING_CHECKOUT = "Check_out";
    private static String BUILDING_PRICE = "Price";
    private static String BUILDING_TYPE = "Type";
    private static String BUILDING_IMAGE = "Image";
    // Tabelul cu locuinte

    //Tabelul cu rezervari
    private static String RESERV_TABLE_NAME = "ReservationTable";
    private static String RESERV_ID = "Id_res";
    private static String RESERV_BUILDING = "Building";
    private static String RESERV_DATE_IN = "DateFrom";
    private static String RESERV_DATE_OUT = "DateTo";
    private static String RESERV_NR_PERS = "NrPers";
    private static String RESERV_GUEST_NAME = "GuestName";
    private static String RESERV_GUEST_PRENAME = "GuestPrename";
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
            BUILDING_RATING + " REAL, " + BUILDING_PRICE + " INTEGER, " + BUILDING_TYPE + " TEXT, " + BUILDING_CHECKIN + " TEXT, " + BUILDING_CHECKOUT + " TEXT, " + BUILDING_IMAGE + " INT" + ")";

    private String INSERT_BUILDING_TABLE =
            "INSERT INTO " + BUILDING_TABLE_NAME + "(" + BUILDING_TITLE + ", " + BUILDING_RATING + ", " + BUILDING_ADDRESS
                    + ", " + BUILDING_PRICE + ", " + BUILDING_TYPE + " , "+BUILDING_CHECKIN +" , "+BUILDING_CHECKOUT +" , " + BUILDING_IMAGE + ")" +
                    "VALUES (" + "' Hotelul Codru' " + ", " + "'4.99'" + " , " + "' Vlaicu Pircalab 56'" + " , " + "'150'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.hotel_codru + "), " +
                    "(" + "'Hotelul National'" + ", " + "'5.00'" + " , " + "'Grigore Vieru 34'" + " , " + "'375'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.hotel_national  + "), " +
                    "(" + "'Hotelul Grand Palace'" + ", " + " '4.89'" + " , " + "'Vasile Alexandri 57'" + " , " + "'275'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.grand_palace + "), " +
                    "(" + "'Apartament in chirie Balti'" + ", " + "'4.65'" + " , " + "'Alexandru Cel Bun 46'" + " , " + "'160'" + " , " + "'apartament'" + " , " + "'00:00'" + " , " + "'00:00'" + " , " + R.drawable.apartament_balti + "), " +
                    "(" + "'Apartament in chirie Comrat'" + ", " + "'4.78'" + " , " + "'Grigore Ureche 4'" + " , " + "'160'" + " , " + "'apartament'" + " , " + "'00:00'" + " , " + "'00:00'" + " , " + R.drawable.apartament_comrat + ")," +
                    "(" + "'Apartament in chirie Ialoveni'" + ", " + "'4.88'" + " , " + "'Alexei Mateevici 33'" + " , " + "'170'" + " , " + "'apartament'" + " , " + "'00:00'" + " , " + "'00:00'" + " , " + R.drawable.apartament_ialoveni + ")," +
                    "(" + "'Motelul Green Land'" + ", " + "'4.90'" + " , " + "'Bucuresti  44'" + " , " + "'200'" + " , " + "'motel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.gl_motel + "), " +
                    "(" + "'Bazar Motel'" + ", " + "'4.50'" + " , " + "'Ismail 12'" + " , " + "'300'" + " , " + "'motel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.bazar_motel + "), " +
                    "(" + "'Kalyan Hotel'" + ", " + "'4.78'" + " , " + "'Braila89'" + " , " + "'140'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.kalyan_motel + "), " +
                    "(" + "'Hotel ZIMBRU'" + ", " + "'4.89'" + " , " + "'Calea Iesilor 6'" + " , " + "'210'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.zimbru + "), " +
                    "(" + "'Melrose Hostel'" + ", " + "'5.00'" + " , " + "'Stefan Cel Mare 145'" + " , " + "'190'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.melrose_hostel + "), " +
                    "(" + "'Chisinau Hostel'" + ", " + "'4.00'" + " , " + "'Calea Iesilor 8'" + " , " + "'159'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.chisinau_hostel + "), " +
                    "(" + "'Be my Guest Hostel'" + ", " + "'4.25'" + " , " + "'Grigore Vieru 4'" + " , " + "'140'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.bemy_guest + "), " +
                    "(" + "'The Backpackshack'" + ", " + "'4.88'" + " , " + "'Vlaicu Pircalab 2'" + " , " + "'160'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.thebackpachshack + "), " +
                    "(" + "'Paris Hostel'" + ", " + "'4.56'" + " , " + "'Codrului 45'" + " , " + "'180'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.paris_hostel + "), " +
                    "(" + "'Apartament in chirie Chisinau'" + ", " + "'5.00'" + " , " + "'Pacii 26'" + " , " + "'300'" + " , " + "'apartament'" + " , " + "'00:00'" + " , " + "'00:00'" + " , " + R.drawable.ap_chisinau + "), " +
                    "(" + "'Funky Mamaliga Hostel'" + ", " + "'4.67'" + " , " + "'Florilor 9'" + " , " + "'190'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.funky_hostel + "), " +
                    "(" + "'Chisianu Chill Hostel'" + ", " + "'4.98'" + " , " + "'Viilor 7'" + " , " + "'170'" + " , " + "'hostel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.chis_chill + "), " +
                    "(" + "'Daima Hotel'" + ", " + "'4.96'" + " , " + "'Vasile Alexandri 3'" + " , " + "'280'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.daima + "), " +
                    "(" + "'Adam & Eva Hotel'" + ", " + "'4.78'" + " , " + "'Puskin 49'" + " , " + "'170'" + " , " + "'hotel'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.adam_eva + "), " +
                    "(" + "'Apartament in chirie Balti'" + ", " + "'4.88'" + " , " + "'Florilor 45'" + " , " + "'195'" + " , " + "'apartament'" + " , " + "'12:00'" + " , " + "'13:00'" + " , " + R.drawable.apartament_balti_2 + ")";
    private String CREATE_RESERV_TABLE = "CREATE TABLE " + RESERV_TABLE_NAME + "(" + RESERV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RESERV_BUILDING + " TEXT," + RESERV_DATE_IN + " TEXT," + RESERV_DATE_OUT + " TEXT," +
            RESERV_GUEST_NAME + " TEXT, " +RESERV_GUEST_PRENAME +" TEXT"+ ")";

    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_PRENAME
            + " TEXT," + USER_EMAIL + " TEXT," + USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TYPE_TABLE = "CREATE TABLE " + TYPE_TABLE_NAME + "(" + TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_TITLE + " TEXT" + ")";
    private String INSERT_TYPE_TABLE = "INSERT INTO " + TYPE_TABLE_NAME + "(" + TYPE_TITLE + ")" + "VALUES (" + "'apartament'" + "), " +
            "(" + "'hostel'" + ")," + "(" + "'hotel'" + ")," + "(" + "'motel'" + ")";
    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private String DROP_BUILDING_TABLE = "DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME;
    private String DROP_TYPE_TABLE = "DROP TABLE IF EXISTS " + TYPE_TABLE_NAME;
    private String DROP_RESERV_TABLE = "DROP TABLE IF EXISTS " + RESERV_TABLE_NAME;
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

    public boolean checkUserOnLogin(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID},
                USER_EMAIL + " =?",
                new String[]{email},
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
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_BUILDING_TABLE);
        db.execSQL(INSERT_BUILDING_TABLE);
        db.execSQL(CREATE_TYPE_TABLE);
        db.execSQL(INSERT_TYPE_TABLE);
        db.execSQL(CREATE_RESERV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_BUILDING_TABLE);
        db.execSQL(DROP_TYPE_TABLE);
        db.execSQL(DROP_RESERV_TABLE);
        onCreate(db);
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/
    public List<Buildings> getBuildings() {
        List<Buildings> listOfBuilding = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + BUILDING_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Buildings buildings = new Buildings();
                buildings.Id_build = cursor.getInt(cursor.getColumnIndex(BUILDING_ID));
                buildings.Title = cursor.getString(cursor.getColumnIndex(BUILDING_TITLE));
                buildings.Rating = cursor.getFloat(cursor.getColumnIndex(BUILDING_RATING));
                buildings.Address = cursor.getString(cursor.getColumnIndex(BUILDING_ADDRESS));
                buildings.Price = cursor.getInt(cursor.getColumnIndex(BUILDING_PRICE));
                buildings.Type = cursor.getString(cursor.getColumnIndex(BUILDING_TYPE));
                buildings.CheckIn = cursor.getString(cursor.getColumnIndex(BUILDING_CHECKIN));
                buildings.CheckOut = cursor.getString(cursor.getColumnIndex(BUILDING_CHECKOUT));
                buildings.Image = cursor.getInt(cursor.getColumnIndex(BUILDING_IMAGE));
                listOfBuilding.add(buildings);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfBuilding;

    }

    public void insertBuilding(String title, /* float rating,*/ String address, int price, String type, int image, int checkIn,int checkOut) {
        ContentValues values = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        values.put(BUILDING_TITLE, title);
        values.put(BUILDING_ADDRESS, address);
        values.put(BUILDING_RATING, rating);
        values.put(BUILDING_CHECKIN, checkIn);
        values.put(BUILDING_CHECKOUT,checkOut);
        values.put(BUILDING_PRICE, price);
        values.put(BUILDING_TYPE, type);
        values.put(BUILDING_IMAGE,image);
        //values.put(BUILDING_IMAGE,image);
        sqLiteDatabase.insert(BUILDING_TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public List<Buildings> getHotelsByFilter(Buildings bFilter) {
        List<Buildings> listOfBuilding = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + BUILDING_TABLE_NAME + " WHERE Type = ? ", new String[]{bFilter.Type});
        if (cursor.moveToFirst()) {
            do {
                Buildings buildings = new Buildings();
                buildings.Id_build = cursor.getInt(cursor.getColumnIndex(BUILDING_ID));
                buildings.Title = cursor.getString(cursor.getColumnIndex(BUILDING_TITLE));
                buildings.Rating = cursor.getFloat(cursor.getColumnIndex(BUILDING_RATING));
                buildings.Address = cursor.getString(cursor.getColumnIndex(BUILDING_ADDRESS));
                buildings.Price = cursor.getInt(cursor.getColumnIndex(BUILDING_PRICE));
                buildings.Type = cursor.getString(cursor.getColumnIndex(BUILDING_TYPE));
                buildings.CheckIn = cursor.getString(cursor.getColumnIndex(BUILDING_CHECKIN));
                buildings.CheckOut = cursor.getString(cursor.getColumnIndex(BUILDING_CHECKOUT));
                buildings.Image= cursor.getInt(cursor.getColumnIndex(BUILDING_IMAGE));
                listOfBuilding.add(buildings);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfBuilding;

    }
    /*************************************************************************HOTELS ACTIONS*********************************************************/


    /*************************************************************************TYPE ACTIONS*********************************************************/

    public List<Type> getAllTypes() {
        List<Type> listOfType = new ArrayList<>();

        Cursor cursorType = sqLiteDatabase.rawQuery("SELECT * FROM " + TYPE_TABLE_NAME, null);
        if (cursorType.moveToFirst()) {
            do {
                Type type = new Type();
                type.IDType = cursorType.getInt(cursorType.getColumnIndex(TYPE_ID));
                type.Type = cursorType.getString(cursorType.getColumnIndex(TYPE_TITLE));

                listOfType.add(type);

            }
            while (cursorType.moveToNext());
            sqLiteDatabase.close();
        }
        return listOfType;

    }

    /*************************************************************************TYPE ACTIONS*********************************************************/
    public void makeReservation(String titleBuilding, String guestName,String guestPrename, int maxPers, String fromDate,String toDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESERV_BUILDING,titleBuilding);
        contentValues.put(RESERV_GUEST_NAME,guestName);
        contentValues.put(RESERV_GUEST_PRENAME,guestPrename);
        contentValues.put(RESERV_DATE_IN,fromDate);
        contentValues.put(RESERV_DATE_OUT, toDate);
        contentValues.put(RESERV_NR_PERS,maxPers);

        sqLiteDatabase.insert(RESERV_TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

}
