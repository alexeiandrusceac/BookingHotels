package com.bookinghotels.app.mainActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class ReservationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,NavigationView.OnNavigationItemSelectedListener{

    private RecyclerReservationAdapter reservationAdapter;
    private RecyclerView recReservView;
    private RecyclerView.LayoutManager layoutReservManager;
    private List<Reservations> listOfReservations = new ArrayList<>();
    private Toolbar reservToolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DataBaseHelper reservHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservated_room_main);
        reservToolbar = (Toolbar)findViewById(R.id.reserv_app_toolbar);
        setSupportActionBar(reservToolbar);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.reservated_room_main,frameLayout);
        //navigationView = (NavigationView)findViewById(R.id.navigationView);
        //navigationView.setNavigationItemSelectedListener(this);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //setupDrawer();
        listOfReservations = reservHelper.getReservations(1);
        refreshReservData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void refreshReservData() {

        recReservView = (RecyclerView) findViewById(R.id.reservatedRecycleView);
        //.setHasFixedSize(true);
        layoutReservManager = new LinearLayoutManager(ReservationActivity.this);
        recReservView.setLayoutManager(layoutReservManager);
        reservationAdapter = new RecyclerReservationAdapter(ReservationActivity.this, listOfReservations);
        recReservView.setAdapter(reservationAdapter);

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
        searchView.setOnQueryTextListener(ReservationActivity.this);
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

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(ReservationActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        } else if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reservationAdapter.filter(newText);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myPosts:
                Toast.makeText(ReservationActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myReservations:
                Toast.makeText(ReservationActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationIntent = new Intent (getApplicationContext(), ReservationActivity.class);
                startActivity(reservationIntent);
                break;
            default:
                return false;
        }
        return true;
    }
}
