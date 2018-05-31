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
import com.bookinghotels.app.mainActivity.Hotels.Hotels;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DataBaseHelper postHelper;
    private RecyclerView recPostsView;
    private FrameLayout framePostLayout;
    private RecyclerPostAdapter postAdapter;
    private LayoutInflater layoutPostsInflater;
    private RecyclerView.LayoutManager layoutPostsManager;
    private ActionBarDrawerToggle postToggle;
    private NavigationView navigationPostView;
    private DrawerLayout drawerPostLayout;
    private View postView;
    private List<Hotels> listOfPosts = new ArrayList<Hotels>();
    private Toolbar postToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        layoutPostsInflater = (LayoutInflater)PostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        framePostLayout = (FrameLayout)findViewById(R.id.content_frame);
        postHelper = new DataBaseHelper(PostActivity.this);
        listOfPosts = postHelper.getHotels(1);
        navigationPostView = (NavigationView)findViewById(R.id.navigationView);
        navigationPostView.setNavigationItemSelectedListener(this);
        drawerPostLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();
        postView = layoutPostsInflater.inflate(R.layout.post_content_activity,framePostLayout);

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
        postAdapter = new RecyclerPostAdapter(PostActivity.this, listOfPosts);
        recPostsView.setAdapter(postAdapter);
    }

    private void setupDrawer() {
        postToggle = new ActionBarDrawerToggle(PostActivity.this, drawerPostLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

        postToggle.setDrawerIndicatorEnabled(true);
        drawerPostLayout.addDrawerListener(postToggle);
    }
    @Override
    public void onBackPressed() {
        if (drawerPostLayout.isDrawerOpen(GravityCompat.START)) {
            drawerPostLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        postToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        postToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        } else if (postToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(PostActivity.this,"Ati ajuns acasa",Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeActivity);
                break;
            case R.id.myPosts:
                Toast.makeText(PostActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postIntent= new Intent(getApplicationContext(),PostActivity.class);
                getSupportActionBar().setTitle(PostActivity.this.getResources().getString(R.string.post_View));
                startActivity(postIntent);
                break;
            case R.id.myReservations:
                Toast.makeText(PostActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationIntent = new Intent(getApplicationContext(), ReservationActivity.class);
                getSupportActionBar().setTitle(PostActivity.this.getResources().getString(R.string.reservationText));
                startActivity(reservationIntent);
                break;
            default:
                return false;
        }
        return true;
    }

}
