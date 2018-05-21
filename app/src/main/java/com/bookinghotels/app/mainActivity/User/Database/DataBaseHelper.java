package com.bookinghotels.app.mainActivity.User.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.Type;
import com.bookinghotels.app.mainActivity.User.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Informatia despre Baza de date
    private static String DB_NAME = "BookingHotels";
    private static int DB_VERSION = 1;

    // Tabelul cu utilizatori
    private static String USER_TABLE_NAME = "Guest";
    private static String USER_ID = "Id_user";
    private static String USER_NAME = "FirstName";
    private static String USER_PRENAME = "LastName";
    private static String USER_EMAIL = "Email";
    private static String USER_PASSWORD = "Password";
    // Tabelul cu utilizatori

    // Tabelul cu locuinte
    private static String HOTEL_TABLE_NAME = "Hotels";
    private static String HOTEL_ID = "Id_hotel";
    private static String HOTEL_TITLE = "Title";
    private static String HOTEL_RATING = "Rating";
    private static String HOTEL_ADDRESS = "Address";
    private static String HOTEL_ZIP = "Zip";
    private static String HOTEL_ROOM_NR = "RoomNr";
    private static String HOTEL_PHONE = "Phone";
    private static String HOTEL_IMAGE = "Image";
    private static String HOTEL_PRICE= "Price";
    // Tabelul cu locuinte

    //Tabelul cu rezervari
    private static String RESERV_TABLE_NAME = "Reservation";
    private static String RESERV_ID = "Reserv_id";
    private static String RESERV_GUEST_ID = "Guest_id";
    private static String RESERV_ROOM_ID = "Room_id";
    private static String RESERV_HOTEL_ID = "HotelID";
    private static String RESERV_DATE_IN = "DateIn";
    private static String RESERV_DATE_OUT = "DateOut";
    private static String RESERV_NR_PERS = "Nr_Person";
    //Tabelul cu rezervari

    private SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /***************************************************************CREAREA TABELELOR*****************************************************************************************/


    private String CREATE_HOTEL_TABLE = "CREATE TABLE " + HOTEL_TABLE_NAME + "(" + HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HOTEL_TITLE + " TEXT, " + HOTEL_ADDRESS + " TEXT, " + HOTEL_ZIP + " TEXT, " + HOTEL_PHONE + " TEXT, " +
            HOTEL_RATING + " REAL, " + HOTEL_IMAGE + " INT," + HOTEL_ROOM_NR + " INT,"+ HOTEL_PRICE +" REAL" + ")";

    private String INSERT_HOTEL_TABLE =
            "INSERT INTO " + HOTEL_TABLE_NAME + "(" + HOTEL_TITLE + ", " + HOTEL_ADDRESS + ", " + HOTEL_ZIP + ", " + HOTEL_PHONE + ", " + HOTEL_RATING +
                    " , " + HOTEL_IMAGE + ", " + HOTEL_ROOM_NR +", "+ HOTEL_PRICE+ ")" +
                    "VALUES (" + "' Hotelul Codru'" + ", " + "' Vlaicu Pircalab 56'" + " , " + "'2514'" + " , " + "'022-145-124'" + " , " + "'4.59'" + " , " + R.drawable.hotel_codru + " , "+ "'3'" + ", " +"'300'"+ "), " +
                    "(" + "'Hotelul National'" + ", " + "'Grigore Vieru 34'" + " , " + "'6805'" + " , " + "'022-452-275'" + " , " + "'5.00'" + " , " + R.drawable.hotel_national + " , "+ "'4'" + ", " +"'550'"+ "), " +
                    "(" + "'Hotelul Grand Palace'" + ", " + "'Vasile Alexandri 57'" + " , " + "'6805'" + " , " + "'022-632-012'" + " , " + "'4.89'" + " , " + R.drawable.grand_palace +" , "+ "'3'" + ", " + "'400'"+"), " +
                    "(" + "'Hotelul Eftalia'" + ", " + "'Alexandru Cel Bun 46'" + " , " + "'7805'" + " , " + "'022-412-714'" + " , " + "'4.65'" + " , " + R.drawable.eftalia +" , "+ "'4'" + ", " + "'450'"+ "), " +
                    "(" + "'Hotelul Elenite'" + ", " + "'Grigore Ureche 4'" + "," + "'8801'" + " , " + "'022-125-132'" + " , " + "'4.56'" + " , " + R.drawable.elenite + " , "+ "'1'" + ", "+ "'150'"+")," +
                    "(" + "'Hotelul Cosmos Gonaives'" + ", " + "'Alexei Mateevici 33'" + ", " + "'6801'" + " , " + "'022-145-256'" + ", " + "'4.88'" + " , " + R.drawable.cosmos + " , "+ "'4'"+ ", " + "'500'"+")," +
                    "(" + "'Hotelul JOLLY ALLON'" + ", " + "'Bucuresti  44'" + " , " + "'6801'" + " , " + "'022-122-122'" + " , " + "'4.90'" + " , " + R.drawable.gl_motel + " , "+ "'5'" + ", "+"'600'"+"), " +
                    "(" + "'Bazar Hotel'" + ", " + "'Ismail 12'" + " , " + "'5800'" + " , " + "'022-145-541'" + " , " + "'4.50'" + " , " + R.drawable.bazar_hotel + " , "+ "'3'"+ ", " +"'350'"+ "), " +
                    "(" + "'Kalyan Hotel'" + ", " + "'Braila 89'" + " , " + "'5801'" + " , " + "'022-122-365'" + " , " + "'4.78'" + " , " + R.drawable.kalyan_motel +" , "+ "'2'"+ ", " +"'250'"+ ")";

    private String CREATE_RESERV_TABLE = "CREATE TABLE " + RESERV_TABLE_NAME + "(" + RESERV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RESERV_HOTEL_ID + " INTEGER," + RESERV_GUEST_ID + " INTEGER, " + RESERV_DATE_IN + " TEXT," + RESERV_DATE_OUT + " TEXT, "
            + RESERV_NR_PERS + " INTEGER" + ")";

  //  private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_PRENAME + " TEXT," + USER_EMAIL + " TEXT," + USER_PASSWORD + " TEXT" + ")";

    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/

    //private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private String DROP_HOTEL_TABLE = "DROP TABLE IF EXISTS " + HOTEL_TABLE_NAME;
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
       //db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(INSERT_HOTEL_TABLE);

        db.execSQL(CREATE_RESERV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_HOTEL_TABLE);
        db.execSQL(DROP_RESERV_TABLE);
        onCreate(db);
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/
    public List<Hotels> getHotels() {
        List<Hotels> listOfHotels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HOTEL_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Hotels hotels = new Hotels();
                hotels.Id_hotel = cursor.getInt(cursor.getColumnIndex(HOTEL_ID));
                hotels.Title = cursor.getString(cursor.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = cursor.getFloat(cursor.getColumnIndex(HOTEL_RATING));
                hotels.Address = cursor.getString(cursor.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = cursor.getString(cursor.getColumnIndex(HOTEL_ZIP));
                hotels.Image = cursor.getInt(cursor.getColumnIndex(HOTEL_IMAGE));
                hotels.Price = cursor.getInt(cursor.getColumnIndex(HOTEL_PRICE));
                hotels.Phone = cursor.getString(cursor.getColumnIndex(HOTEL_PHONE));
                hotels.NrRooms = cursor.getInt(cursor.getColumnIndex(HOTEL_ROOM_NR));
                listOfHotels.add(hotels);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfHotels;

    }

    public void insertBuilding(String title,  float rating, String address,  int image, String zip,String phone,String nrRooms,String price) {
        ContentValues values = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        values.put(HOTEL_TITLE, title);
        values.put(HOTEL_ADDRESS, address);
        values.put(HOTEL_RATING, rating);
        values.put(HOTEL_ZIP,zip);
        values.put(HOTEL_IMAGE, image);
        values.put(HOTEL_PHONE,phone);
        values.put(HOTEL_ROOM_NR,nrRooms);
        values.put(HOTEL_PRICE,price);

        sqLiteDatabase.insert(HOTEL_TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public List<Hotels> getHotelsByFilter(Hotels hFilter, int priceFrom, int priceTo) {
        List<Hotels> listOfHotels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HOTEL_TABLE_NAME + " WHERE Rating = ? OR ? <= Price <= ? ", new String[]{String.valueOf(hFilter.Rating),String.valueOf(priceFrom),String.valueOf(priceTo)});
        if (cursor.moveToFirst()) {
            do {
                Hotels hotels = new Hotels();
                hotels.Id_hotel = cursor.getInt(cursor.getColumnIndex(HOTEL_ID));
                hotels.Title = cursor.getString(cursor.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = cursor.getFloat(cursor.getColumnIndex(HOTEL_RATING));
                hotels.Address = cursor.getString(cursor.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = cursor.getString(cursor.getColumnIndex(HOTEL_ZIP));
                hotels.NrRooms = cursor.getInt(cursor.getColumnIndex(HOTEL_ROOM_NR));
                hotels.Price = cursor.getInt(cursor.getColumnIndex(HOTEL_PRICE));
                hotels.Image = cursor.getInt(cursor.getColumnIndex(HOTEL_IMAGE));
                hotels.Phone = cursor.getString(cursor.getColumnIndex(HOTEL_PHONE));
                listOfHotels.add(hotels);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfHotels;

    }
    /*************************************************************************HOTELS ACTIONS*********************************************************/



    public void makeReservation(String guestID, String roomID, String hotelID, int nrPers, String fromDate, String toDate) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RESERV_GUEST_ID, guestID);
        contentValues.put(RESERV_ROOM_ID,roomID);
        contentValues.put(RESERV_HOTEL_ID,hotelID);
        contentValues.put(RESERV_DATE_IN, fromDate);
        contentValues.put(RESERV_DATE_OUT, toDate);
        contentValues.put(RESERV_NR_PERS, nrPers);

        sqLiteDatabase.insert(RESERV_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

}
