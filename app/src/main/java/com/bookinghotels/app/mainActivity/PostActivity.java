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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.UserActions.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
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
    UserSession userSession;
    private static String userEmail;
    private static int userImage;
    private static int idUser;
    private static  TextView user_name_text;
    private static Button logOut_button;
    private static TextView user_email_text;
    private static ImageView nav_header_imageView;
    private View header;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        Bundle b = getIntent().getExtras();
        userEmail = b.getString("Email");
        userImage = b.getInt("Image");
        idUser = b.getInt("Id");
        layoutPostsInflater = (LayoutInflater)PostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        framePostLayout = (FrameLayout)findViewById(R.id.content_frame);
        postHelper = new DataBaseHelper(PostActivity.this);
        listOfPosts = postHelper.getHotels(idUser);
        navigationPostView = (NavigationView)findViewById(R.id.navigationView);
        navigationPostView.setNavigationItemSelectedListener(this);
        header = (navigationPostView).getHeaderView(0);
        user_name_text = header.findViewById(R.id.user_name_text);
        logOut_button = header.findViewById(R.id.logOut_button);
        user_email_text = header.findViewById(R.id.user_email_text);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);

        drawerPostLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();
        postView = layoutPostsInflater.inflate(R.layout.post_content_activity,framePostLayout);

        postToolbar = postView.findViewById(R.id.post_app_toolbar);
        setSupportActionBar(postToolbar);
        refreshPostData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        userSession = new UserSession(getApplicationContext());
        userSession.checkLogin();
        HashMap<String,String> userDetails = userSession.getUserDetails();
        String name = userDetails.get(UserSession.KEY_NAME);


        user_name_text.setText(name);
        user_email_text.setText(userEmail);
        nav_header_imageView.setImageResource(userImage);

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
                homeActivity.putExtra("Image",userImage);
                homeActivity.putExtra("Email",userEmail);
                homeActivity.putExtra("Id",idUser);
                startActivity(homeActivity);

                break;
            case R.id.myPosts:
                Toast.makeText(PostActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity= new Intent(getApplicationContext(),PostActivity.class);
                setTitle(PostActivity.this.getResources().getString(R.string.post_View));
                postActivity.putExtra("Image",userImage);
                postActivity.putExtra("Email",userEmail);
                postActivity.putExtra("Id",idUser);

                startActivity(postActivity);

                break;
            case R.id.myReservations:
                Toast.makeText(PostActivity.this, "Ati selectat rezervarile dvs", Toast.LENGTH_SHORT).show();
                Intent reservationActivity = new Intent(getApplicationContext(), ReservationActivity.class);
                setTitle(PostActivity.this.getResources().getString(R.string.reservationText));
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
