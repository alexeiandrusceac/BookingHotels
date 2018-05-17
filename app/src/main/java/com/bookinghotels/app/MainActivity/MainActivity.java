package com.bookinghotels.app.MainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bookinghotels.app.MainActivity.Buildings.Buildings;
import com.bookinghotels.app.MainActivity.User.Database.DataBaseHelper;
import com.bookinghotels.app.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private static SearchView searchFilter;
    private static DataBaseHelper dbHelper;
    private static List<Buildings> listOfBuilds= new ArrayList<>();
    private static CardView cardView;
    private static Button filterButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        searchFilter  =  (SearchView)findViewById(R.id.searchViewFilter);
        filterButton = (Button)findViewById(R.id.buttonFilter);

        listOfBuilds = dbHelper.getHotels();
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });

    }

    private void showFilterDialog(View view)
    {

        LayoutInflater layoutInflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewFilterDialog= layoutInflater.inflate(R.layout.main_filter,null,false);

        final TextView typeTextView = viewFilterDialog.findViewById(R.id.type_View);
        final TextView fromPriceTextView = viewFilterDialog.findViewById(R.id.from_Price_View);
        final TextView ratingView = viewFilterDialog.findViewById(R.id.rating_View);
        final TextView toPriceTextView = viewFilterDialog.findViewById(R.id.to_Price_View);
        final Spinner spinnerType = viewFilterDialog.findViewById(R.id.list_Of_Type);
        final RatingBar ratingBar = viewFilterDialog.findViewById(R.id.rating_View);
        final EditText fromPriceInput = viewFilterDialog.findViewById(R.id.from_Price_Input);
        final EditText toPriceInput = viewFilterDialog.findViewById(R.id.to_Price_Input);

        spinnerType.setAdapter(new ArrayAdapter<Type>(MainActivity.this,android.R.layout.simple_spinner_item, dbHelper.getAllTypes()));
        spinnerType.setOnItemClickListener(this);

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
