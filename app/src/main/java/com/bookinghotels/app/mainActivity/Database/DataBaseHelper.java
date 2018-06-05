package com.bookinghotels.app.mainActivity.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.transition.CircularPropagation;
import android.util.Log;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.MainActivity;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;
import com.bookinghotels.app.mainActivity.Rooms.Rooms;
import com.bookinghotels.app.mainActivity.UserActions.User.User;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Informatia despre Baza de date
    private static String DB_NAME = "BookingHotels.db";
    private static int DB_VERSION = 1;
    private static DataBaseHelper databaseHelper;


    // Tabelul cu utilizatori
    private static String USER_TABLE_NAME = "Guest";
    private static String USER_ID = "Id_User";
    private static String USER_NAME = "FirstName";
    private static String USER_PRENAME = "LastName";
    private static String USER_EMAIL = "Email";
    private static String USER_PASSWORD = "Password";
    private static String USER_IMAGE = "Image";
    // Tabelul cu utilizatori

    // Tabelul cu hotele
    private static String HOTEL_TABLE_NAME = "Hotels";
    private static String HOTEL_ID = "Id_Hotel";
    private static String HOTEL_ADMIN_ID = "Id_AdminHotel";
    private static String HOTEL_ROOM_ID = "Id_RoomHotel";
    private static String HOTEL_TITLE = "Title";
    private static String HOTEL_RATING = "Rating";
    private static String HOTEL_ADDRESS = "Address";
    private static String HOTEL_ZIP = "Zip";
    private static String HOTEL_PHONE = "Phone";
    private static String HOTEL_IMAGE = "Image";
    // Tabelul cu hotele

    //Tabelul cu rezervari
    private static String RESERV_TABLE_NAME = "Reservation";
    private static String RESERV_ID = "Id_Reserv";
    private static String RESERV_GUEST_ID = "Id_GuestReserv";
    private static String RESERV_ROOM_ID = "Id_RoomReserv";
    private static String RESERV_HOTEL_ID = "Id_HotelReserv";
    private static String RESERV_DATE_IN = "DateIn";
    private static String RESERV_DATE_OUT = "DateOut";
    private static String RESERV_NR_PERS = "Nr_Person";
    //Tabelul cu rezervari

    //Tabelul cu camere
    private static String ROOM_TABLE_NAME = "Room";
    private static String ROOM_ID = "Id_Room";
    private static String ROOM_HOTEL_ID = "Id_HotelRoom";
    private static String ROOM_NUMBER = "RoomNr";
    private static String ROOM_TYPE = "RoomType";
    private static String ROOM_PRICE = "RoomPrice";

    //Tabelul cu camere
    // Operation to Reservation tabel
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DataBaseHelper(context.getApplicationContext());
        }
        return databaseHelper;
    }

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    @SuppressLint("NewApi")
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    private final String GET_RESERVATIONS =
            "SELECT * FROM " + RESERV_TABLE_NAME /*+
                    " INNER JOIN " + HOTEL_TABLE_NAME + " ON " + RESERV_HOTEL_ID + " = " + HOTEL_ID +
                    " INNER JOIN " + ROOM_TABLE_NAME + " ON " + RESERV_ROOM_ID + " = " + ROOM_ID +
                    " INNER JOIN " + USER_TABLE_NAME + " ON " + RESERV_GUEST_ID + " = " + USER_ID + " WHERE " + RESERV_GUEST_ID + " =? " + " ORDER BY " + HOTEL_TITLE + " ASC "*/;
    // Operation to Reservation tabel

    private final String GET_AVAILABLEROOM =
            "SELECT * FROM " + ROOM_TABLE_NAME; /*+ " INNER JOIN " + RESERV_TABLE_NAME + " ON " + ROOM_ID + " != " + RESERV_ROOM_ID + " WHERE "
                    + ROOM_HOTEL_ID + "= ?" + " AND " + ROOM_PRICE + " BETWEEN ? AND ?";
*/

    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /********************************************************************HOTELS_TABLE********************************************************************************************/
    private String CREATE_HOTEL_TABLE = "CREATE TABLE " + HOTEL_TABLE_NAME + "(" + HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HOTEL_ADMIN_ID + " INTEGER," + HOTEL_ROOM_ID + " INTEGER," + HOTEL_TITLE + " TEXT, " + HOTEL_ADDRESS + " TEXT, " + HOTEL_ZIP + " TEXT, " + HOTEL_PHONE + " TEXT, " +
            HOTEL_RATING + " REAL, " + HOTEL_IMAGE + " BLOB" + ")";
    /********************************************************************HOTELS_TABLE********************************************************************************************/

    /********************************************************************RESERVATIONS_TABLE********************************************************************************************/
    private String CREATE_RESERV_TABLE = "CREATE TABLE " + RESERV_TABLE_NAME + "(" + RESERV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RESERV_HOTEL_ID + " INTEGER," + RESERV_ROOM_ID + " INTEGER," + RESERV_GUEST_ID + " INTEGER, " + RESERV_DATE_IN + " TEXT," + RESERV_DATE_OUT + " TEXT, "
            + RESERV_NR_PERS + " INTEGER" + ")";
    /********************************************************************RESERVATIONS_TABLE********************************************************************************************/

    /********************************************************************USERS_TABLE********************************************************************************************/
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " + USER_PRENAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_IMAGE + " BLOB " + ")";
    /********************************************************************USERS_TABLE********************************************************************************************/

    /********************************************************************ROOMS_TABLE********************************************************************************************/
    private String CREATE_ROOM_TABLE = "CREATE TABLE " + ROOM_TABLE_NAME + "(" + ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ROOM_HOTEL_ID + " INTEGER, " + ROOM_NUMBER + " INTEGER," + ROOM_TYPE + " TEXT," + ROOM_PRICE + " INTEGER " + ")";
    /********************************************************************ROOMS_TABLE********************************************************************************************/


    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private String DROP_HOTEL_TABLE = "DROP TABLE IF EXISTS " + HOTEL_TABLE_NAME;
    private String DROP_RESERV_TABLE = "DROP TABLE IF EXISTS " + RESERV_TABLE_NAME;
    private String DROP_ROOM_TABLE = "DROP TABLE IF EXISTS " + ROOM_TABLE_NAME;
    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/


    /***************************************************************************UTILIZATOR***********************************************************/
    /***************************************************************************UTILIZATOR***********************************************************/
    public boolean checkUserOnLogin(String name, String password) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID}, USER_NAME + " = ? " + " AND " + USER_PASSWORD + " = ? ", new String[]{name, password}, null, null, USER_ID);

        if (cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUserOnLogin(String name) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID}, USER_NAME + " = ?", new String[]{name},
                null, null, USER_ID);
        int count = cursor.getCount();

        if (count > 0) {
            return true;
        }
        return false;
    }

    public void registerNewUser(User user) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.Name);
        values.put(USER_PRENAME, user.Prename);
        values.put(USER_EMAIL, user.Email);
        values.put(USER_PASSWORD, user.Password);
        values.put(USER_IMAGE, user.Image);
        long id = sqLiteDatabase.insert(USER_TABLE_NAME, null, values);

        Log.d(TAG, String.format("Utilizatorul cu numele " + USER_NAME + " " + USER_PRENAME + " s-a inregistrat cu succes"));
    }

    public User getUser(String userName, String password) {
        User user = new User();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curUser = sqLiteDatabase.rawQuery("SELECT * from " + USER_TABLE_NAME + " WHERE " +
                USER_NAME + " =? " + " AND " + USER_PASSWORD + " = ? ", new String[]{userName, password});
        if (curUser.moveToFirst()) {
            do {

                user.ID_User = curUser.getInt(curUser.getColumnIndex(USER_ID));
                user.Name = curUser.getString(curUser.getColumnIndex(USER_NAME));
                user.Prename = curUser.getString(curUser.getColumnIndex(USER_PRENAME));
                user.Email = curUser.getString(curUser.getColumnIndex(USER_EMAIL));
                user.Password = curUser.getString(curUser.getColumnIndex(USER_PASSWORD));
                user.Image = curUser.getBlob(curUser.getColumnIndex(USER_IMAGE));

            } while (curUser.moveToNext());

        }

        return user;
    }

    /*******************************************************************************UTILIZATOR************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(CREATE_RESERV_TABLE);
        db.execSQL(CREATE_ROOM_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DROP_ROOM_TABLE);
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_HOTEL_TABLE);
            db.execSQL(DROP_RESERV_TABLE);
            onCreate(db);
        }
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/
    public List<Hotels> getAllHotels() {
        List<Hotels> listOfHotels = new ArrayList<>();
        String getHotels = "SELECT * FROM Hotels";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(getHotels,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Hotels hotels = new Hotels();
                    hotels.Id_Hotel = cursor.getInt(cursor.getColumnIndex(HOTEL_ID));
                    hotels.Id_AdminHotel = cursor.getInt(cursor.getColumnIndex(HOTEL_ADMIN_ID));
                    hotels.Id_RoomHotel = cursor.getInt(cursor.getColumnIndex(HOTEL_ROOM_ID));

                    hotels.Title = cursor.getString(cursor.getColumnIndex(HOTEL_TITLE));
                    hotels.Rating = cursor.getFloat(cursor.getColumnIndex(HOTEL_RATING));
                    hotels.Address = cursor.getString(cursor.getColumnIndex(HOTEL_ADDRESS));
                    hotels.Zip = cursor.getString(cursor.getColumnIndex(HOTEL_ZIP));

                    hotels.Phone = cursor.getString(cursor.getColumnIndex(HOTEL_PHONE));
                    hotels.Image = cursor.getBlob(cursor.getColumnIndex(HOTEL_IMAGE));
                    listOfHotels.add(hotels);
                }
                while (cursor.moveToNext());
            }
        } catch
                (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return listOfHotels;


    }

    public List<Hotels> getHotels(int currUserID) {

        List<Hotels> listOfHotels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursorHotels = sqLiteDatabase.rawQuery("SELECT * FROM " + HOTEL_TABLE_NAME + " WHERE " + HOTEL_ADMIN_ID + " =?", new String[]{String.valueOf(currUserID)});
        if (cursorHotels.moveToFirst()) {
            do {
                Hotels hotels = new Hotels();
                hotels.Id_Hotel = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ID));
                hotels.Id_RoomHotel = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ROOM_ID));
                hotels.Id_AdminHotel = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ADMIN_ID));
                hotels.Title = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = cursorHotels.getFloat(cursorHotels.getColumnIndex(HOTEL_RATING));
                hotels.Address = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_ZIP));
                hotels.Image = cursorHotels.getBlob(cursorHotels.getColumnIndex(HOTEL_IMAGE));
                hotels.Phone = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_PHONE));

                listOfHotels.add(hotels);
            }
            while (cursorHotels.moveToNext());
        }

        return listOfHotels;

    }

    public void insertRooms(List<Rooms> listOfRooms) {
        ContentValues roomValue = new ContentValues();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        for (Rooms room : listOfRooms) {
            roomValue.put(ROOM_HOTEL_ID, room.Id_Hotel);
            roomValue.put(ROOM_NUMBER, room.RoomNumber);
            roomValue.put(ROOM_PRICE, room.RoomPrice);
            roomValue.put(ROOM_TYPE, room.RoomType);
            sqLiteDatabase.insert(ROOM_TABLE_NAME, null, roomValue);
        }

    }

    public void insertPost(Hotels hotels) {

        ContentValues hotelValues = new ContentValues();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        hotelValues.put(HOTEL_ROOM_ID, hotels.Id_RoomHotel);
        hotelValues.put(HOTEL_ADMIN_ID, hotels.Id_AdminHotel);
        hotelValues.put(HOTEL_TITLE, hotels.Title);
        hotelValues.put(HOTEL_ADDRESS, hotels.Address);
        hotelValues.put(HOTEL_RATING, hotels.Rating);
        hotelValues.put(HOTEL_ZIP, hotels.Zip);
        hotelValues.put(HOTEL_IMAGE, hotels.Image);
        hotelValues.put(HOTEL_PHONE, hotels.Phone);
        sqLiteDatabase.insert(HOTEL_TABLE_NAME, null, hotelValues);

    }

    public List<Rooms> getAllRooms() {
        List<Rooms> listOfRooms = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursorRoom = sqLiteDatabase.rawQuery("Select * from " + ROOM_TABLE_NAME, null);
        if (cursorRoom.moveToFirst()) {
            do {
                Rooms room = new Rooms();
                room.Id_Hotel = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_HOTEL_ID));
                room.Id_Room = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_ID));
                room.RoomNumber = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_NUMBER));
                room.RoomType = cursorRoom.getString(cursorRoom.getColumnIndex(ROOM_TYPE));
                room.RoomPrice = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_PRICE));
                listOfRooms.add(room);
            }
            while (cursorRoom.moveToNext());
        }

        return listOfRooms;
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/


    public void makeReservation(int guestID, int roomID, int hotelID, int nrPers, String fromDate, String toDate) {
        ContentValues reservValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        reservValues.put(RESERV_GUEST_ID, guestID);
        reservValues.put(RESERV_ROOM_ID, roomID);
        reservValues.put(RESERV_HOTEL_ID, hotelID);
        reservValues.put(RESERV_DATE_IN, fromDate);
        reservValues.put(RESERV_DATE_OUT, toDate);
        reservValues.put(RESERV_NR_PERS, nrPers);

        sqLiteDatabase.insert(RESERV_TABLE_NAME, null, reservValues);

    }

    public List<Reservations> getReservations(int curUserId) {
        List<Reservations> reservationsList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursorReserv = sqLiteDatabase.rawQuery(GET_RESERVATIONS, new String[]{String.valueOf(curUserId)});

        if (cursorReserv.moveToFirst()) {
            do {
                Reservations reservation = new Reservations();
                reservation.Id_Reserv = cursorReserv.getInt(cursorReserv.getColumnIndex(RESERV_ID));
                reservation.HotelAddress = cursorReserv.getString(cursorReserv.getColumnIndex(HOTEL_ADDRESS));
                reservation.HotelTitle = cursorReserv.getString(cursorReserv.getColumnIndex(HOTEL_TITLE));
                reservation.RoomNumber = cursorReserv.getInt(cursorReserv.getColumnIndex(ROOM_NUMBER));
                reservation.RoomPrice = cursorReserv.getInt(cursorReserv.getColumnIndex(ROOM_PRICE));
                reservation.RoomType = cursorReserv.getString(cursorReserv.getColumnIndex(ROOM_TYPE));
                reservation.DateIn = cursorReserv.getString(cursorReserv.getColumnIndex(RESERV_DATE_IN));
                reservation.DateOut = cursorReserv.getString(cursorReserv.getColumnIndex(RESERV_DATE_OUT));
                reservation.NrPers = cursorReserv.getInt(cursorReserv.getColumnIndex(RESERV_NR_PERS));
                reservationsList.add(reservation);
            }
            while (cursorReserv.moveToNext());
        }

        return reservationsList;
    }

    public List<String> getRooms(int hotelID, int fromPrice, int toPrice) {
        List<String> listOfRooms = new ArrayList<String>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursorRoom = sqLiteDatabase.rawQuery(GET_AVAILABLEROOM, new String[]{String.valueOf(hotelID), String.valueOf(fromPrice), String.valueOf(toPrice)});
        if (cursorRoom.moveToFirst()) {
            do {
                String room = cursorRoom.getString(cursorRoom.getColumnIndex(ROOM_NUMBER));
                listOfRooms.add(room);
            } while (cursorRoom.moveToNext());
        }

        return listOfRooms;
    }

    /*public HashMap<String, String> getAvailableRooms(int hotelID, int fromPrice, int toPrice) {
        Rooms room = new Rooms();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorRoom = sqLiteDatabase.rawQuery(GET_AVAILABLEROOM, new String[]{String.valueOf(hotelID), String.valueOf(fromPrice), String.valueOf(toPrice)});
        //List<Rooms> listOfdata = new ArrayList<>();
        HashMap<String, String> mapOfData = new HashMap<String, String>();
        if (cursorRoom.moveToFirst()) {

            do {
                room.Id_Room = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_ID));
                room.Id_Hotel = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_HOTEL_ID));
                room.RoomNumber = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_NUMBER));
                room.RoomType = cursorRoom.getString(cursorRoom.getColumnIndex(ROOM_TYPE));
                room.RoomPrice = cursorRoom.getInt(cursorRoom.getColumnIndex(ROOM_PRICE));
                listOfdata.add(room);

            }
            while (cursorRoom.moveToNext());

        }

        sqLiteDatabase.close();
        return listOfdata;
    }*/
}
