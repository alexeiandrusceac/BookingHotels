package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener{
    private DataBaseHelper postHelper;
    private RecyclerView recPostsView;
    private FrameLayout frameLayout;
    private RecyclerHotelsAdapter adapter;
    private LayoutInflater layoutInflater;
    private RecyclerView.LayoutManager layoutPostsManager;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View postView;
    private List<Hotels> listOfPosts = new ArrayList<Hotels>();
    private Toolbar postToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        layoutInflater = (LayoutInflater)PostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        postHelper = new DataBaseHelper(PostActivity.this);
        listOfPosts = postHelper.getHotels(1);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();
        postView = layoutInflater.inflate(R.layout.main_bar_activity,frameLayout);

        postToolbar = postView.findViewById(R.id.post_app_toolbar);
        setSupportActionBar(postToolbar);
        refreshPostData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    private void refreshPostData()
    {
        recPostsView = (RecyclerView)findViewById(R.id.postRecyclerView);
        recPostsView.setHasFixedSize(true);
        layoutPostsManager = new LinearLayoutManager(PostActivity.this);
        recPostsView.setLayoutManager(layoutPostsManager);
        adapter = new RecyclerHotelsAdapter(PostActivity.this, listOfPosts);
        recPostsView.setAdapter(adapter);
    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(PostActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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
        searchView.setOnQueryTextListener(PostActivity.this);
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

                Toast.makeText(PostActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postIntent= new Intent(getApplicationContext(),PostActivity.class);

                startActivity(postIntent);
                break;
            case R.id.myReservations:
                Toast.makeText(PostActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationIntent = new Intent(getApplicationContext(), ReservationActivity.class);
                startActivity(reservationIntent);
                break;
            default:
                return false;
        }
        return true;
    }

}
