package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;


import com.bookinghotels.app.mainActivity.Rooms.Rooms;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import android.util.Range;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static DataBaseHelper dbHelper;
    private static List<Hotels> listOfHotels = new ArrayList<>();

    // private static CardView cardView;
    //private static Button filterButton;
    private FloatingActionButton floatingButton;
    //private RelativeLayout relativeLayoutWithCards;
    private RecyclerHotelsAdapter adapter;
    private RecyclerView recHotelsView;
    private RecyclerView.LayoutManager layoutHotelsManager;

    public View postMainView;
    public ImageView imageView;
    private Toolbar toolbar;
    //private ImageView userImageView;
    private DrawerLayout drawerLayout;
    protected FrameLayout frameLayout;
    private View view;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private LayoutInflater layoutInflater;
    private static int RESULT_LOAD_IMAGE = 1;
    UserSession session;
    private static String userEmail;
    private static int userImage;
    private static int idUser;
    private static TextView user_name_text;
    private static Button logOut_button;
    private static TextView user_email_text;
    private static ImageView nav_header_imageView;
    private View header;
    private LinearLayout roomLabelLayout;
    private Spinner roomType;
    private TextView roomNumberText;
    private EditText roomPriceText;
    private List<Rooms> listOfRooms = new ArrayList<>();
    private List<Rooms> dbListOfRooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        Bundle b = getIntent().getExtras();
        userEmail = b.getString("Email");
        userImage = b.getInt("Image");
        idUser = b.getInt("Id");
        roomType = new Spinner(MainActivity.this);
        roomType.setAdapter(new ArrayAdapter<TypeRoom>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, TypeRoom.values()
        ));

        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        dbHelper = new DataBaseHelper(MainActivity.this);
        listOfHotels = dbHelper.getHotels();
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

        dbListOfRooms = dbHelper.getAllRooms();
        session = new UserSession(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> userDetails = session.getUserDetails();
        String name = userDetails.get(UserSession.KEY_NAME);
        user_name_text.setText(name);
        user_email_text.setText(userEmail);
        nav_header_imageView.setImageResource(userImage);

    }


    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
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
        adapter = new RecyclerHotelsAdapter(MainActivity.this, listOfHotels);
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

            imageView = (ImageView) postMainView.findViewById(R.id.imageViewPost);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

    private void showPostDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        postMainView = layoutInflater.inflate(R.layout.post_main, null, false);
        final Button imageLoader = postMainView.findViewById(R.id.buttonLoadPicturePost);
        final EditText hotelTitle = postMainView.findViewById(R.id.titleInputPost);
        final EditText hotelAddress = postMainView.findViewById(R.id.addressInputPost);
        final EditText hotelZip = postMainView.findViewById(R.id.zipInputPost);
        final EditText hotelPhone = postMainView.findViewById(R.id.phoneInputPost);
        ///dbHelper.getRooms();
        final EditText roomNr = postMainView.findViewById(R.id.roomInputPost);
        roomLabelLayout = postMainView.findViewById(R.id.roomValueLayout);


        imageLoader.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
        new AlertDialog.Builder(MainActivity.this).setView(postMainView).setCancelable(false).setPositiveButton(
                "Posteaza", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Hotels hotel = new Hotels();
                        hotel.Id_admin = idUser;
                        hotel.Id_room = listOfRooms.lastIndexOf(listOfRooms);
                        hotel.Address = hotelAddress.getText().toString();
                       // hotel.Image = imageView.;
                        hotel.Phone = hotelPhone.getText().toString();
                        hotel.Zip = hotelZip.getText().toString();

                        dbHelper.insertPost(hotel, listOfRooms);

                    }
                }
        ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();


       /* int roomNumber =  Integer.parseInt(roomNr.getText().toString());
        for (int i = 0; i < roomNumber; i++) {
            EditText editText = new EditText(MainActivity.this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setGravity(Gravity.CENTER);
            editText.setText("primult editText");
            if (roomLabelLayout != null) {
                roomLabelLayout.addView(editText);
            }
        }*/
        /*//buildCheckIn.getText().toString(),
        // buildCheckOut.getText().toString());*/

        listOfHotels = dbHelper.getHotels();
        refreshHotelsData();
    }

    public void press(View v) {
        EditText a = v.findViewById(R.id.roomInputPost);


        int roomNumber = Integer.parseInt(a.getText().toString());
        for (int i = 0; i < roomNumber; i++) {
            Rooms room = new Rooms();

            a.setEnabled(false);
            TextView roomLabel = new TextView(MainActivity.this);
            roomNumberText = new TextView(MainActivity.this);
            TextView priceLabel = new TextView(MainActivity.this);
            roomPriceText = new EditText(MainActivity.this);

            roomLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomLabel.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomLabel.setText("Camera:");
            roomLabel.setTextSize(17);

            roomNumberText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomNumberText.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomNumberText.setText(String.valueOf((listOfHotels.size() + 1) + "0" + i));
            roomNumberText.setTextSize(17);

            priceLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            priceLabel.setGravity(Gravity.CENTER);
            priceLabel.setText("pretul:");
            priceLabel.setTextSize(17);

            roomPriceText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomPriceText.getLayoutParams()).setMargins(0, 14, 0, 0);
            roomPriceText.setTextSize(17);

            roomType.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            room.RoomNumber = Integer.parseInt(roomNumberText.getText().toString());
            room.RoomPrice = Integer.parseInt(roomPriceText.getText().toString());
            room.RoomType = roomType.getSelectedItem().toString();
            room.Id_Hotel = listOfHotels.lastIndexOf(listOfHotels) + 1;
            listOfRooms.add(room);

            if (roomLabelLayout != null) {
                roomLabelLayout.addView(roomLabel);
                roomLabelLayout.addView(roomNumberText);
                roomLabelLayout.addView(priceLabel);
                roomLabelLayout.addView(roomPriceText);
                roomLabelLayout.addView(roomType);
            }
        }
    }

    private void showFilterDialog(View view) {

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
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // This method can be used when query is submitted eg. creatting search history using SQLite DB
        //adapter.filter(query);

        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
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
