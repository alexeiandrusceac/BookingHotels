package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;
import com.bookinghotels.app.mainActivity.UserActions.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerReservationAdapter reservationAdapter;
    private RecyclerView recReservView;
    private RecyclerView.LayoutManager layoutReservManager;
    private List<Reservations> listOfReservations = new ArrayList<>();
    private Toolbar reservToolbar;
    private DrawerLayout drawerReservLayout;
    private FrameLayout frameReservLayout;
    private ActionBarDrawerToggle reservToggle;
    private NavigationView navigationReservView;
    private DataBaseHelper reservHelper;
    private LayoutInflater layoutReservInflater;
    private View viewReservation;

    UserSession userSession;
    private static String userEmail;
    private static byte[] userImage;
    private static int idUser;
    private static  TextView user_name_text;
    private static Button logOut_button;
    private static TextView user_email_text;
    private static ImageView nav_header_imageView;
    private View header;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

        Bundle b = getIntent().getExtras();
        userEmail = b.getString("Email");
        userImage = b.getByteArray("Image");
        idUser = b.getInt("Id");

        layoutReservInflater = (LayoutInflater) ReservationActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameReservLayout = (FrameLayout) findViewById(R.id.content_frame);
        reservHelper = new DataBaseHelper(ReservationActivity.this);
        listOfReservations = reservHelper.getReservations(idUser);
        navigationReservView = (NavigationView) findViewById(R.id.navigationView);
        navigationReservView.setNavigationItemSelectedListener(this);
        header = (navigationReservView).getHeaderView(0);
        user_name_text = header.findViewById(R.id.user_name_text);
        logOut_button = header.findViewById(R.id.logOut_button);
        user_email_text = header.findViewById(R.id.user_email_text);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        drawerReservLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        viewReservation = layoutReservInflater.inflate(R.layout.reservated_content_activity, frameReservLayout);
        reservToolbar = viewReservation.findViewById(R.id.reserv_app_toolbar);
        setSupportActionBar(reservToolbar);

        refreshReservData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        userSession = new UserSession(getApplicationContext());
        userSession.checkLogin();
        HashMap<String, String> userDetails = userSession.getUserDetails();
        String name = userDetails.get(UserSession.KEY_NAME);

        user_name_text.setText(name);
        user_email_text.setText(userEmail);
        nav_header_imageView.setImageBitmap(BitmapFactory.decodeByteArray(userImage, 0, userImage.length));
    }

    public void refreshReservData() {

        recReservView = (RecyclerView) findViewById(R.id.reservatedRecycleView);
        recReservView.setHasFixedSize(true);
        layoutReservManager = new LinearLayoutManager(ReservationActivity.this);
        recReservView.setLayoutManager(layoutReservManager);
        reservationAdapter = new RecyclerReservationAdapter(ReservationActivity.this, listOfReservations);
        recReservView.setAdapter(reservationAdapter);

    }

    @Override
    public void onBackPressed() {
        if (drawerReservLayout.isDrawerOpen(GravityCompat.START)) {
            drawerReservLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        reservToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        reservToggle.syncState();
    }

    private void setupDrawer() {
        reservToggle = new ActionBarDrawerToggle(ReservationActivity.this, drawerReservLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

        reservToggle.setDrawerIndicatorEnabled(true);
        drawerReservLayout.addDrawerListener(reservToggle);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        } else if (reservToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(ReservationActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                homeActivity.putExtra("Image",userImage);
                homeActivity.putExtra("Email",userEmail);
                homeActivity.putExtra("Id",idUser);
                startActivity(homeActivity);

                break;
            case R.id.myPosts:
                Toast.makeText(ReservationActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), PostActivity.class);

                postActivity.putExtra("Image",userImage);
                postActivity.putExtra("Email",userEmail);
                postActivity.putExtra("Id",idUser);
                startActivity(postActivity);

                break;
            case R.id.myReservations:
                Toast.makeText(ReservationActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationActivity = new Intent(getApplicationContext(), ReservationActivity.class);
                setTitle(ReservationActivity.this.getResources().getString(R.string.reservationText));
                reservationActivity.putExtra("Image",userImage);
                reservationActivity.putExtra("Email",userEmail);
                reservationActivity.putExtra("Id",idUser);
                startActivity(reservationActivity);

                break;
            case R.id.logOut_button:
                userSession.logoutUser();
                finish();
                break;
            default:
                return false;
        }
        return true;
    }
}
