package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

        layoutReservInflater = (LayoutInflater)ReservationActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameReservLayout = (FrameLayout)findViewById(R.id.content_frame);
        reservHelper = new DataBaseHelper(ReservationActivity.this);
        listOfReservations = reservHelper.getReservations(1);
        navigationReservView = (NavigationView)findViewById(R.id.navigationView);
        navigationReservView.setNavigationItemSelectedListener(this);
        drawerReservLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        viewReservation = layoutReservInflater.inflate(R.layout.reservated_content_activity,frameReservLayout);
        reservToolbar = viewReservation.findViewById(R.id.reserv_app_toolbar);
        setSupportActionBar(reservToolbar);

        refreshReservData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        reservToggle = new ActionBarDrawerToggle(ReservationActivity.this, drawerReservLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(ReservationActivity.this,"Ati ajuns acasa",Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeActivity);
                break;
            case R.id.myPosts:
                Toast.makeText(ReservationActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postIntent= new Intent(getApplicationContext(),PostActivity.class);
                getSupportActionBar().setTitle(ReservationActivity.this.getResources().getString(R.string.post_View));
                startActivity(postIntent);
                break;
            case R.id.myReservations:
                Toast.makeText(ReservationActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationIntent = new Intent (getApplicationContext(), ReservationActivity.class);
                getSupportActionBar().setTitle(ReservationActivity.this.getResources().getString(R.string.reservationText));
                startActivity(reservationIntent);
                break;
            default:
                return false;
        }
        return true;
    }
}
