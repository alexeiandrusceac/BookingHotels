package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;


import com.bookinghotels.app.mainActivity.Rooms.Rooms;

import android.provider.MediaStore;
import android.sax.RootElement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Range;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;


import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Rooms.Rooms;
import com.bookinghotels.app.mainActivity.UserActions.User.User;
import com.bookinghotels.app.mainActivity.UserActions.UserSession;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener {

    private DataBaseHelper dbHelper;
    private static List<Hotels> listOfHotels;
    private FloatingActionButton floatingButton;
    private RecyclerHotelsAdapter adapter;
    private RecyclerView recHotelsView;
    private RecyclerView.LayoutManager layoutHotelsManager;
    public View postMainView;
    private ImageView imageView;
    private Toolbar toolbar;
    private String selectedCity;
    private DrawerLayout drawerLayout;
    protected FrameLayout frameLayout;
    private View view;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private LayoutInflater layoutInflater;
    private static int RESULT_LOAD_IMAGE = 1;
    UserSession session;
    private static String userEmail;
    private static byte[] userImage;
    private static int idUser;
    private static TextView user_name_text;
    private static Button logOut_button;
    private static TextView user_email_text;
    private static ImageView nav_header_imageView;
    private View header;

    private Spinner citySpinner;

    private List<Rooms> listOfRooms = new ArrayList<>();
    private List<Rooms> dbListOfRooms = new ArrayList<>();
    private String name;
    private HashMap<String, String> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

        /// Obtinerea datelor din Intent
        Bundle b = getIntent().getExtras();
        userEmail = b.getString("Email");
        userImage = b.getByteArray("Image");
        idUser = b.getInt("Id");

//https://github.com/thetonrifles/stackoverflow/blob/so-34848401/app/src/main/java/com/thetonrifles/stackoverflow/adapter/EventsAdapter.java
        //https://github.com/thetonrifles/stackoverflow/tree/so-34848401
        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        dbHelper = DataBaseHelper.getInstance(this);

        listOfHotels = dbHelper.getAllHotels();
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        header = (navigationView).getHeaderView(0);
        user_name_text = header.findViewById(R.id.user_name_text);
        logOut_button = header.findViewById(R.id.logOut_button);
        user_email_text = header.findViewById(R.id.user_email_text);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        view = layoutInflater.inflate(R.layout.main_content_activity, frameLayout);
        listOfRooms = dbHelper.getAllRooms();
        toolbar = view.findViewById(R.id.main_app_toolbar);
        setSupportActionBar(toolbar);

        floatingButton = view.findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });
        refreshHotelsData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       /* filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });*/
        session = new UserSession(getApplicationContext());
        session.checkLogin();
        userDetails = session.getUserDetails();
        name = userDetails.get(UserSession.KEY_NAME);
        user_name_text.setText(name);
        user_email_text.setText(userEmail);
        nav_header_imageView.setImageBitmap(BitmapFactory.decodeByteArray(userImage, 0, userImage.length));
    }


    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu();
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cautarea hotelelor");
        searchView.setOnQueryTextListener(MainActivity.this);
        searchView.setIconified(false);

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        } else if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshHotelsData() {
        recHotelsView = view.findViewById(R.id.recycleview);
        recHotelsView.setHasFixedSize(true);
        layoutHotelsManager = new LinearLayoutManager(MainActivity.this);
        recHotelsView.setLayoutManager(layoutHotelsManager);
        adapter = new RecyclerHotelsAdapter(MainActivity.this, listOfHotels, idUser);
        recHotelsView.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView = postMainView.findViewById(R.id.imageViewPost);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

    private void showPostDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        postMainView = layoutInflater.inflate(R.layout.post_main, null, false);
        citySpinner = new Spinner(postMainView.getContext());
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final EditText hotelTitle = postMainView.findViewById(R.id.titleInputPost);
        final EditText hotelAddress = postMainView.findViewById(R.id.addressInputPost);
        final EditText hotelZip = postMainView.findViewById(R.id.zipInputPost);
        final EditText hotelPhone = postMainView.findViewById(R.id.phoneInputPost);

        citySpinner = postMainView.findViewById(R.id.cityInputPost);
        imageView = (ImageView) postMainView.findViewById(R.id.imageViewPost);
        //final EditText nrRoom = postMainView.findViewById(R.id.roomInputPost);
        //roomLabelLayout = postMainView.findViewById(R.id.roomValueLayout);

        //citySpinner = new Spinner(MainActivity.this);
        citySpinner.setAdapter(new ArrayAdapter<Locality>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, Locality.values()
        ));
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this).setView(postMainView).setCancelable(false).setPositiveButton(
                "Posteaza", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }
        ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog ad = alertDialog.create();
        ad.show();
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                Hotels hotel = new Hotels();
                /*if (TextUtils.isEmpty(nrRoom.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Introduceti numarul de camere!", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                hotel.Id_AdminHotel = idUser;
                hotel.Title = hotelTitle.getText().toString();
                hotel.Address = hotelAddress.getText().toString();
                hotel.Image = baos.toByteArray();
                hotel.Phone = hotelPhone.getText().toString();
                hotel.Zip = hotelZip.getText().toString();
                hotel.City = selectedCity;
                 /* final  List<Rooms> ssdb = new ArrayList<>();
                for (int j = 0; j < roomLabelLayout.getChildCount(); j++) {
                    Rooms room = new Rooms();
                    LinearLayout linearLayout = (LinearLayout) roomLabelLayout.getChildAt(j);
                    for (int i = 0; i < linearLayout.getChildCount(); i++) {

                        View view = linearLayout.getChildAt(i);
                        if (view.getId() > -1) {
                            if (view.getClass().equals(TextView.class)) {
                                room.RoomNumber = Integer.parseInt(((TextView) view).getText().toString());

                            } else if (view.getClass().equals(EditText.class)) {
                                room.RoomPrice = Integer.parseInt(((EditText) view).getText().toString());

                            } else if (view.getClass().equals(Spinner.class)) {
                                room.RoomType = ((Spinner) view).getSelectedItem().toString();
                                if (listOfHotels.size() == 0) {
                                    room.Id_Hotel = 1;
                                } else {
                                    room.Id_Hotel = listOfHotels.size() + 1;
                                }
                                ssdb.add(room);
                            }

                        }
                    }
                }
                dbHelper.insertRooms(ssdb);*/

                 dbHelper.insertPost(hotel);

                listOfHotels.add(hotel);
               // listOfRooms = dbHelper.getAllRooms();
                listOfHotels = dbHelper.getAllHotels();

                refreshHotelsData();

                ad.dismiss();
            }
        });
    }



    /*private void showFilterDialog(View view) {

        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewFilterDialog = layoutInflater.inflate(R.layout.main_filter, null, false);

        final TextView typeTextView = viewFilterDialog.findViewById(R.id.type_View);
        final TextView fromPriceTextView = viewFilterDialog.findViewById(R.id.from_Price_View);
        final TextView ratingView = viewFilterDialog.findViewById(R.id.rating_View);
        final TextView toPriceTextView = viewFilterDialog.findViewById(R.id.to_Price_View);
        final Spinner spinnerType = viewFilterDialog.findViewById(R.id.list_Of_Type);
        final RatingBar ratingBar = viewFilterDialog.findViewById(R.id.rating_View);
        final EditText fromPriceInput = viewFilterDialog.findViewById(R.id.from_Price_Input);
        final EditText toPriceInput = viewFilterDialog.findViewById(R.id.to_Price_Input);


        new AlertDialog.Builder(MainActivity.this).setCancelable(false).setPositiveButton("Cauta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        // This method can be used when query is submitted eg. creatting search history using SQLite DB
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(MainActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Email", userEmail);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
            case R.id.myPosts:
                Toast.makeText(MainActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), PostActivity.class);
                setTitle(MainActivity.this.getResources().getString(R.string.post_View));
                postActivity.putExtra("Image", userImage);
                postActivity.putExtra("Email", userEmail);
                postActivity.putExtra("Id", idUser);
                startActivity(postActivity);

                break;
            case R.id.myReservations:
                Toast.makeText(MainActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationActivity = new Intent(getApplicationContext(), ReservationActivity.class);
                setTitle(MainActivity.this.getResources().getString(R.string.reservationText));
                reservationActivity.putExtra("Image", userImage);
                reservationActivity.putExtra("Email", userEmail);
                reservationActivity.putExtra("Id", idUser);
                startActivity(reservationActivity);
                finish();
                break;
            case R.id.logOut_button:
                session.logoutUser();
                finish();
            default:
                return false;
        }
        return true;
    }
}
