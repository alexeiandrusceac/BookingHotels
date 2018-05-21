package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    private static SearchView searchFilter;
    private static DataBaseHelper dbHelper;
    private static List<Hotels> listOfHotels = new ArrayList<>();
    private static CardView cardView;
    private static Button filterButton;
    private FloatingActionButton floatingButton;
    private RelativeLayout relativeLayoutWithCards;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView postView;
    private DatePickerDialog datePicker;
    public  View postMainView;
    public ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postView =(TextView)findViewById(R.id.postView);
        Toolbar toolbar =(Toolbar)findViewById(R.id.main_app_toolbar);

        setContentView(R.layout.main_activity);

        //searchFilter = (SearchView) findViewById(R.id.searchViewFilter);
        floatingButton = (FloatingActionButton)findViewById(R.id.floatingButton);
        //linearLayoutWithCards = (LinearLayout)findViewById(R.id.linearLayoutWithCards);
        //cardView = (CardView)findViewById(R.id.cardView);
        dbHelper= new DataBaseHelper(this);
       // filterButton = (Button) findViewById(R.id.buttonFilter);

        listOfHotels = dbHelper.getHotels();
       /* filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });*/
        refreshData();
        setSupportActionBar(toolbar);


        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
        searchView.setOnQueryTextListener(MainActivity.this);
        searchView.setIconified(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshData()
    {
        recyclerView =  (RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(MainActivity.this,listOfHotels);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

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
       private void showPostDialog()
       {
           LayoutInflater layoutInflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           postMainView = layoutInflater.inflate(R.layout.post_main,null,false);
           final Button imageLoader = postMainView.findViewById(R.id.buttonLoadPicturePost);
           final EditText buildTitle =  postMainView.findViewById(R.id.titleInputPost);
         //  final Spinner buildType = postMainView.findViewById(R.id.typeInputPost);
           final EditText buildAddress = postMainView.findViewById(R.id.addressInputPost);
           final EditText buildPrice = postMainView.findViewById(R.id.priceInputPost);
           //final EditText buildCheckIn = postMainView.findViewById(R.id.checkInInputPost);
          // final EditText buildCheckOut = postMainView.findViewById(R.id.checkOutInputPost);

           //(dbHelper.getAllTypes().toArray(String)).subList(,1)
          // buildType.setAdapter(new ArrayAdapter<Type>(MainActivity.this, android.R.layout.simple_spinner_item, ));

           imageLoader.setOnClickListener(new View.OnClickListener() {
               @SuppressLint("NewApi")
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(intent,RESULT_LOAD_IMAGE);
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
           refreshData();
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

        //spinnerType.setAdapter(new ArrayAdapter<Type>(MainActivity.this, android.R.layout.simple_spinner_item, dbHelper.getAllTypes()));

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

        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }
}
