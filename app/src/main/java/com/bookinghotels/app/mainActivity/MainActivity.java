package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.PersistableBundle;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;

import java.util.ArrayList;
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

    //private TextView postView;
    //private DatePickerDialog datePicker;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        dbHelper = new DataBaseHelper(MainActivity.this);
        listOfHotels = dbHelper.getHotels();
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        view = layoutInflater.inflate(R.layout.main_bar_activity, frameLayout);

        toolbar = view.findViewById(R.id.main_app_toolbar);
        setSupportActionBar(toolbar);
        //postView = view.findViewById(R.id.postView);
        floatingButton =  view.findViewById(R.id.floatingButton);


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

        //userImageView = (ImageView) findViewById(R.id.nav_header_imageView);

    }



    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
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
        final EditText hotelPrice = postMainView.findViewById(R.id.priceInputPost);

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
                            /*dbHelper.insertBuilding(buildTitle.getText().toString(),
                                    buildAddress.getText().toString(),
                                    Integer.parseInt(buildPrice.getText().toString()),
                                    //buildType.toString(),
                                    Integer.parseInt(imageView.getDrawable().toString()));
                                    //buildCheckIn.getText().toString(),
                                   // buildCheckOut.getText().toString());*/
                    }
                }
        ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        listOfHotels = dbHelper.getHotels();
        refreshHotelsData();
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
            case R.id.myPosts:

                Toast.makeText(MainActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postIntent= new Intent(getApplicationContext(),PostActivity.class);

                startActivity(postIntent);
                break;
            case R.id.myReservations:
                Toast.makeText(MainActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationIntent = new Intent(getApplicationContext(), ReservationActivity.class);
                startActivity(reservationIntent);
                break;
            default:
                return false;
        }
        return true;
    }
}
