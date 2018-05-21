package com.bookinghotels.app.mainActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Buildings.Buildings;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static SearchView searchFilter;
    private static DataBaseHelper dbHelper;
    private static List<Buildings> listOfBuilds = new ArrayList<>();
    private static CardView cardView;
    private static Button filterButton;
    private FloatingActionButton floatingButton;
    private RelativeLayout relativeLayoutWithCards;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView postView;
    private DatePickerDialog datePicker;
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

        listOfBuilds = dbHelper.getBuildings();
       /* filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });*/

        setSupportActionBar(toolbar);

        recyclerView =  (RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(MainActivity.this,listOfBuilds);
        recyclerView.setAdapter(adapter);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });
    }
       private void showPostDialog()
       {
           LayoutInflater layoutInflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           final View postView = layoutInflater.inflate(R.layout.post_main,null,false);
          // final EditText buildTitle =  (EditText)postView.findViewById(R.id.)
           new AlertDialog.Builder(MainActivity.this).setView(postView).setCancelable(false).setPositiveButton(
                   "Posteaza", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                            //dbHelper.insertBuilding();
                       }
                   }
           ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

               }
           }).show();
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

        spinnerType.setAdapter(new ArrayAdapter<Type>(MainActivity.this, android.R.layout.simple_spinner_item, dbHelper.getAllTypes()));

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

}
