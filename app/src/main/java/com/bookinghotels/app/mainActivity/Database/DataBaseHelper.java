package com.bookinghotels.app.mainActivity.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.UserActions.User.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Informatia despre Baza de date
    private static String DB_NAME = "BookingHotels";
    private static int DB_VERSION = 1;

    // Tabelul cu utilizatori
    private static String USER_TABLE_NAME = "Guest";
    private static String USER_ID = "Id_User";
    private static String USER_NAME = "FirstName";
    private static String USER_PRENAME = "LastName";
    private static String USER_EMAIL = "Email";
    private static String USER_PASSWORD = "Password";
    private static String USER_IMAGE= "Image";
    // Tabelul cu utilizatori

    // Tabelul cu hotele
    private static String HOTEL_TABLE_NAME = "Hotels";
    private static String HOTEL_ID = "Id_Hotel";
    private static String HOTEL_ADMIN_ID = "Id_Admin";
    private static String HOTEL_ROOM_ID = "Id_Room";
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
    private static String RESERV_GUEST_ID = "Id_Guest";
    private static String RESERV_ROOM_ID = "Id_Room";
    private static String RESERV_HOTEL_ID = "Id_Hotel";
    private static String RESERV_DATE_IN = "DateIn";
    private static String RESERV_DATE_OUT = "DateOut";
    private static String RESERV_NR_PERS = "Nr_Person";
    //Tabelul cu rezervari

    //Tabelul cu camere
    private static String ROOM_TABLE_NAME = "Room";
    private static String ROOM_ID = "Id_Room";
    private static String ROOM_HOTEL_ID = "Id_Hotel";
    private static String ROOM_NUMBER = "RoomNr";
    private static String ROOM_TYPE = "RoomType";
    private static String ROOM_PRICE = "RoomPrice";
    //Tabelul cu camere
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /********************************************************************HOTELS_TABLE********************************************************************************************/
    private String CREATE_HOTEL_TABLE = "CREATE TABLE " + HOTEL_TABLE_NAME + "(" + HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HOTEL_ADMIN_ID + " INTEGER," + HOTEL_ROOM_ID + " INTEGER," + HOTEL_TITLE + " TEXT, " + HOTEL_ADDRESS + " TEXT, " + HOTEL_ZIP + " TEXT, " + HOTEL_PHONE + " TEXT, " +
            HOTEL_RATING + " REAL, " + HOTEL_IMAGE + " INTEGER" + ")";

    private String INSERT_HOTEL_TABLE =
            "INSERT INTO " + HOTEL_TABLE_NAME + "(" + HOTEL_ROOM_ID + ", " + HOTEL_ADMIN_ID + ", " + HOTEL_RATING + " , " + HOTEL_TITLE + ", " + HOTEL_ADDRESS + ", " + HOTEL_ZIP + ", " + HOTEL_PHONE + ", " + HOTEL_IMAGE + ")" +
                    "VALUES (" + "'1'" + ", " + "'1'" + " , " + "'4.59'" + " , " + "' Hotelul Codru'" + ", " + "' Vlaicu Pircalab 56'" + " , " + "'2514'" + " , " + "'022-145-124'" + " , " + R.drawable.hotel_codru + "), " +
                    "(" + "'2'" + ", " + "'2'" + ", " + "'5.00'" + " , " + "'Hotelul National'" + ", " + "'Grigore Vieru 34'" + " , " + "'6805'" + " , " + "'022-452-275'" + " , " + R.drawable.hotel_national + "), " +
                    "(" + "'3'" + ", " + "'1'" + ", " + "'4.89'" + " , " + "'Hotelul Grand Palace'" + ", " + "'Vasile Alexandri 57'" + " , " + "'6805'" + " , " + "'022-632-012'" + " , " + R.drawable.grand_palace + "), " +
                    "(" + "'4'" + ", " + "'2'" + ", " + "'4.65'" + " , " + "'Hotelul Eftalia'" + ", " + "'Alexandru Cel Bun 46'" + " , " + "'7805'" + " , " + "'022-412-714'" + " , " + R.drawable.eftalia + "), " +
                    "(" + "'5'" + ", " + "'1'" + ", " + "'4.56'" + " , " + "'Hotelul Elenite'" + ", " + "'Grigore Ureche 4'" + "," + "'8801'" + " , " + "'022-125-132'" + " , " + R.drawable.elenite + ")," +
                    "(" + "'6'" + ", " + "'2'" + ", " + "'4.88'" + " , " + "'Hotelul Cosmos Gonaives'" + ", " + "'Alexei Mateevici 33'" + ", " + "'6801'" + " , " + "'022-145-256'" + ", " + R.drawable.cosmos + ")," +
                    "(" + "'7'" + ", " + "'1'" + ", " + "'4.90'" + " , " + "'Hotelul JOLLY ALLON'" + ", " + "'Bucuresti  44'" + " , " + "'6801'" + " , " + "'022-122-122'" + " , " + R.drawable.gl_motel + "), " +
                    "(" + "'8'" + ", " + "'2'" + ", " + "'4.50'" + " , " + "'Bazar Hotel'" + ", " + "'Ismail 12'" + " , " + "'5800'" + " , " + "'022-145-541'" + " , " + R.drawable.bazar_hotel + "), " +
                    "(" + "'9'" + ", " + "'1'" + ", " + "'4.78'" + " , " + "'Kalyan Hotel'" + ", " + "'Braila 89'" + " , " + "'5801'" + " , " + "'022-122-365'" + " , " + R.drawable.kalyan_motel + ")";

    /********************************************************************HOTELS_TABLE********************************************************************************************/


    /********************************************************************RESERVATIONS_TABLE********************************************************************************************/
    private String CREATE_RESERV_TABLE = "CREATE TABLE " + RESERV_TABLE_NAME + "(" + RESERV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RESERV_HOTEL_ID + " INTEGER," + RESERV_ROOM_ID + " INTEGER," + RESERV_GUEST_ID + " INTEGER, " + RESERV_DATE_IN + " TEXT," + RESERV_DATE_OUT + " TEXT, "
            + RESERV_NR_PERS + " INTEGER" + ")";
    /********************************************************************RESERVATIONS_TABLE********************************************************************************************/


    /********************************************************************USERS_TABLE********************************************************************************************/
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_PRENAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_IMAGE + " INTEGER "+ ")";
    private String INSERT_USER_TABLE = "INSERT INTO " + USER_TABLE_NAME + "(" + USER_NAME + ", " + USER_PRENAME + ", " + USER_EMAIL + ", " + USER_PASSWORD + ", " + USER_IMAGE +")" +
            "VALUES (" + "'Ion'" + ", " + "'Andronachi'" + ", " + "'ionandronachi@gmail.com'" + ", " + "'pass1'"+", " + R.drawable.ic_account_circle_black_24dp +"), " +
            "(" + "'Vadim'" + ", " + "'Stirba'" + ", " + "'vadimstirba@gmail.com'" + ", " + "'vadim'" +", "+  R.drawable.vadim+  ")";
    /********************************************************************USERS_TABLE********************************************************************************************/

    /********************************************************************ROOMS_TABLE********************************************************************************************/
    private String CREATE_ROOM_TABLE = "CREATE TABLE " + ROOM_TABLE_NAME + "(" + ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROOM_HOTEL_ID + "INTEGER, " + ROOM_NUMBER + " INTEGER," + ROOM_TYPE + " TEXT," + ROOM_PRICE + " REAL " + ")";
    private String INSERT_ROOM_TABLE = "INSERT INTO " + ROOM_TABLE_NAME + "(" + ROOM_HOTEL_ID + ", " + ROOM_NUMBER + ", " + ROOM_TYPE + ", " + ROOM_PRICE + ")" +
            "VALUES (" + "'1'" + ", " + "'100'" + ", " + "'standard'" + ", " + "'150'" + ")," +
            "(" + "'1'" + ", " + "'101'" + ", " + "'standard'" + ", " + "'170'" + ")," +
            "(" + "'2'" + ", " + "'201'" + ", " + "'lux'" + ", " + "'250'" + ")," +
            "(" + "'2'" + ", " + "'202'" + ", " + "'lux'" + ", " + "'280'" + ")," +
            "(" + "'3'" + ", " + "'302'" + ", " + "'lux'" + ", " + "'280'" + ")," +
            "(" + "'3'" + ", " + "'303'" + ", " + "'lux'" + ", " + "'290'" + ")," +
            "(" + "'4'" + ", " + "'400'" + ", " + "'standard'" + ", " + "'175'" + ")," +
            "(" + "'4'" + ", " + "'403'" + ", " + "'standard'" + ", " + "'155'" + ")," +
            "(" + "'5'" + ", " + "'502'" + ", " + "'standard'" + ", " + "'150'" + ")," +
            "(" + "'5'" + ", " + "'503'" + ", " + "'standard'" + ", " + "'170'" + ")," +
            "(" + "'6'" + ", " + "'603'" + ", " + "'lux'" + ", " + "'290'" + ")," +
            "(" + "'6'" + ", " + "'604'" + ", " + "'lux'" + ", " + "'300'" + ")," +
            "(" + "'7'" + ", " + "'705'" + ", " + "'lux'" + ", " + "'350'" + ")," +
            "(" + "'7'" + ", " + "'706'" + ", " + "'lux'" + ", " + "'450'" + ")," +
            "(" + "'8'" + ", " + "'809'" + ", " + "'standard'" + ", " + "'225'" + ")," +
            "(" + "'8'" + ", " + "'810'" + ", " + "'standard'" + ", " + "'260'" + ")," +
            "(" + "'9'" + ", " + "'915'" + ", " + "'lux'" + ", " + "'400'" + ")," +
            "(" + "'9'" + ", " + "'916'" + ", " + "'lux'" + ", " + "'290'" + ")";


    /********************************************************************ROOMS_TABLE********************************************************************************************/


    /***************************************************************CREAREA TABELELOR*****************************************************************************************/

    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private String DROP_HOTEL_TABLE = "DROP TABLE IF EXISTS " + HOTEL_TABLE_NAME;
    private String DROP_RESERV_TABLE = "DROP TABLE IF EXISTS " + RESERV_TABLE_NAME;
    private String DROP_ROOM_TABLE = "DROP TABLE IF EXISTS " + ROOM_TABLE_NAME;
    /***************************************************************STERGEREA TABELELOR*****************************************************************************************/


    /***************************************************************************UTILIZATOR***********************************************************/
    public boolean checkUserOnLogin(String name, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID},USER_NAME + " = ? "+ " AND " + USER_PASSWORD + " = ? ",new String[]{name,password},null,null, USER_ID);

        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean checkUserOnLogin(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE_NAME,
                new String[]{USER_ID}, USER_NAME + " = ?", new String[]{name},
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
        values.put(USER_IMAGE, user.Image);
        long id = database.insert(USER_TABLE_NAME, null, values);
        database.close();
        Log.d(TAG, String.format("Utilizatorul cu numele " + USER_NAME + " " + USER_PRENAME + " s-a inregistrat cu succes"));
    }
    public User getUser(String userName, String password)
    {
        User user = new User();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor curUser = sqLiteDatabase.query(USER_TABLE_NAME , new String[]{USER_ID},USER_NAME + " = ? "+ " AND " + USER_PASSWORD + " = ? ",new String[]{userName,password},null,null, USER_ID);
        if(curUser.moveToFirst())
        {do {

            user.Image = curUser.getInt(curUser.getColumnIndex(USER_IMAGE));
            user.Name = curUser.getString(curUser.getColumnIndex(USER_NAME));
            user.Prename = curUser.getString(curUser.getColumnIndex(USER_PRENAME));
            user.Email = curUser.getString(curUser.getColumnIndex(USER_EMAIL));
            user.Password = curUser.getString(curUser.getColumnIndex(USER_PASSWORD));

        }while(curUser.moveToNext());

        }

        sqLiteDatabase.close();
        return user;
    }
    /*******************************************************************************UTILIZATOR************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(CREATE_USER_TABLE);
        db.execSQL(INSERT_USER_TABLE);
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(INSERT_HOTEL_TABLE);
        //db.execSQL(CREATE_RESERV_TABLE);
        //db.execSQL(CREATE_ROOM_TABLE);
       // db.execSQL(INSERT_ROOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ROOM_TABLE);
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_HOTEL_TABLE);
        db.execSQL(DROP_RESERV_TABLE);
        onCreate(db);
    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/
    public List<Hotels> getHotels() {
        List<Hotels> listOfHotels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorHotels = sqLiteDatabase.rawQuery("SELECT * FROM " + HOTEL_TABLE_NAME, null);
        if (cursorHotels.moveToFirst()) {
            do {
                Hotels hotels = new Hotels();
                hotels.Id_hotel = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ID));
                hotels.Id_room = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ROOM_ID));
                hotels.Id_admin = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_ADMIN_ID));
                hotels.Title = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = cursorHotels.getFloat(cursorHotels.getColumnIndex(HOTEL_RATING));
                hotels.Address = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_ZIP));
                hotels.Image = cursorHotels.getInt(cursorHotels.getColumnIndex(HOTEL_IMAGE));
                hotels.Phone = cursorHotels.getString(cursorHotels.getColumnIndex(HOTEL_PHONE));

                listOfHotels.add(hotels);
            }
            while (cursorHotels.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfHotels;
//asduhaudha
    }

    public void insertHotel(int idRoom, int idAdmin, String title, float rating, String address, int image, String zip, String phone) {
        ContentValues hotelValues = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        hotelValues.put(HOTEL_ROOM_ID, idRoom);
        hotelValues.put(HOTEL_ADMIN_ID, idAdmin);
        hotelValues.put(HOTEL_TITLE, title);
        hotelValues.put(HOTEL_ADDRESS, address);
        hotelValues.put(HOTEL_RATING, rating);
        hotelValues.put(HOTEL_ZIP, zip);
        hotelValues.put(HOTEL_IMAGE, image);
        hotelValues.put(HOTEL_PHONE, phone);


        sqLiteDatabase.insert(HOTEL_TABLE_NAME, null, hotelValues);
        sqLiteDatabase.close();
    }

    /*public List<Hotels> getHotelsByName(String hName)
    {
        List<Hotels> listOfHotelsByName = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorListOFH = sqLiteDatabase.rawQuery("SELECT * FROM "+ HOTEL_TABLE_NAME+" WHERE " + HOTEL_TITLE + " = ?", new String[]{hName});
        if(cursorListOFH.moveToFirst())
        {
            do{
                Hotels hotels = new Hotels();
                hotels.Id_hotel = cursorListOFH.getInt(cursorListOFH.getColumnIndex(HOTEL_ID));
                hotels.Title = cursorListOFH.getString(cursorListOFH.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = cursorListOFH.getFloat(cursorListOFH.getColumnIndex(HOTEL_RATING));
                hotels.Address = cursorListOFH.getString(cursorListOFH.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = cursorListOFH.getString(cursorListOFH.getColumnIndex(HOTEL_ZIP));
                hotels.NrRooms = cursorListOFH.getInt(cursorListOFH.getColumnIndex(HOTEL_ROOM_NR));
                hotels.Price = cursorListOFH.getInt(cursorListOFH.getColumnIndex(HOTEL_PRICE));
                hotels.Image = cursorListOFH.getInt(cursorListOFH.getColumnIndex(HOTEL_IMAGE));
                hotels.Phone = cursorListOFH.getString(cursorListOFH.getColumnIndex(HOTEL_PHONE));
                listOfHotelsByName.add(hotels);
            }while(cursorListOFH.moveToNext());
        }
        sqLiteDatabase.close();
        return listOfHotelsByName;
    }*/

    public List<Hotels> getHotelsByFilter(Hotels hFilter, int priceFrom, int priceTo) {
        List<Hotels> listOfHotels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor hotelsFilter = sqLiteDatabase.rawQuery("SELECT * FROM " + HOTEL_TABLE_NAME + " WHERE Rating = ? OR ? <= Price <= ? ", new String[]{String.valueOf(hFilter.Rating), String.valueOf(priceFrom), String.valueOf(priceTo)});
        if (hotelsFilter.moveToFirst()) {
            do {
                Hotels hotels = new Hotels();
                hotels.Id_hotel = hotelsFilter.getInt(hotelsFilter.getColumnIndex(HOTEL_ID));
                hotels.Id_room = hotelsFilter.getInt(hotelsFilter.getColumnIndex(HOTEL_ROOM_ID));
                hotels.Id_admin = hotelsFilter.getInt(hotelsFilter.getColumnIndex(HOTEL_ADMIN_ID));
                hotels.Title = hotelsFilter.getString(hotelsFilter.getColumnIndex(HOTEL_TITLE));
                hotels.Rating = hotelsFilter.getFloat(hotelsFilter.getColumnIndex(HOTEL_RATING));
                hotels.Address = hotelsFilter.getString(hotelsFilter.getColumnIndex(HOTEL_ADDRESS));
                hotels.Zip = hotelsFilter.getString(hotelsFilter.getColumnIndex(HOTEL_ZIP));

                hotels.Image = hotelsFilter.getInt(hotelsFilter.getColumnIndex(HOTEL_IMAGE));
                hotels.Phone = hotelsFilter.getString(hotelsFilter.getColumnIndex(HOTEL_PHONE));
                listOfHotels.add(hotels);
            }
            while (hotelsFilter.moveToNext());
        }
        sqLiteDatabase.close();

        return listOfHotels;

    }

    /*************************************************************************HOTELS ACTIONS*********************************************************/


    public void makeReservation(String guestID, String roomID, String hotelID, int nrPers, String fromDate, String toDate) {
        ContentValues reservValues = new ContentValues();

        reservValues.put(RESERV_GUEST_ID, guestID);
        reservValues.put(RESERV_ROOM_ID, roomID);
        reservValues.put(RESERV_HOTEL_ID, hotelID);
        reservValues.put(RESERV_DATE_IN, fromDate);
        reservValues.put(RESERV_DATE_OUT, toDate);
        reservValues.put(RESERV_NR_PERS, nrPers);

        sqLiteDatabase.insert(RESERV_TABLE_NAME, null, reservValues);
        sqLiteDatabase.close();
    }

}
