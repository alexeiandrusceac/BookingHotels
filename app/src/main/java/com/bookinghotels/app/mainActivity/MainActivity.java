package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bookinghotels.app.mainActivity.Buildings.Buildings;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;
import com.bookinghotels.app.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static SearchView searchFilter;
    private static DataBaseHelper dbHelper;
    private static List<Buildings> listOfBuilds = new ArrayList<>();
    private static CardView cardView;
    private static Button filterButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        searchFilter = (SearchView) findViewById(R.id.searchViewFilter);
        filterButton = (Button) findViewById(R.id.buttonFilter);
        insertData();
        listOfBuilds = dbHelper.getBuildings();
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(v);
            }
        });

    }

    private void insertData() {
        dbHelper = new DataBaseHelper(MainActivity.this);

        dbHelper.insertBuilding("Hotelul Codru", "4,99", "Vlaicu Pircalab 56", "150", "hotel");
        dbHelper.insertBuilding("Hotelul National", "5,00", "Grigore Vieru 34", "375", "hotel");
        dbHelper.insertBuilding("Hotelul Grand Palace", "4,89", "Vasile Alexandri 57", "275", "hotel");
        dbHelper.insertBuilding("Apartament in chirie Balti", "4.65", "Alexandru Cel Bun 46", "160", "apartament");
        dbHelper.insertBuilding("Apartament in chirie Comrat", "4,78", "Grigore Ureche 4", "160", "apartament");
        dbHelper.insertBuilding("Apartament in chirie Ialoveni", "4,88", "Alexei Mateevici 33", "170", "apartament");
        dbHelper.insertBuilding("Motelul Green Land", "4,90", "Bucuresti  44", "200", "motel");
        dbHelper.insertBuilding("Bazar Motel ", "4,50", "Ismail 12", "300", "motel");
        dbHelper.insertBuilding("Kalyan Hotel", "4,78", "Braila89", "140", "hotel");
        dbHelper.insertBuilding("Hotel Codru", "4,89", "Calea Iesilor 6", "210", "hotel");
        dbHelper.insertBuilding("Melrose Hostel", "5,00", "Stefan Cel Mare 145", "190", "hostel");
        dbHelper.insertBuilding("Chisinau Hostel", "4,00", "Calea Iesilor 8", "159", "hostel");
        dbHelper.insertBuilding("Be my Guest Hostel", "4,25", "Grigore Vieru 4", "140", "hostel");
        dbHelper.insertBuilding("The Backpackshack", "4,88", "Vlaicu Pircalab 2", "160", "hostel");
        dbHelper.insertBuilding("Paris Hostel", "4,56", "Codrului 45", "180", "hostel");
        dbHelper.insertBuilding("Apartament in chirie Chisinau", "5,00", "Pacii 26", "300", "apartament");
        dbHelper.insertBuilding("Funky Mamaliga Hostel", "4,67", "Florilor 9", "190", "hostel");
        dbHelper.insertBuilding("Chisianu Chill Hostel", "4,98", "Viilor 7", "170", "hostel");
        dbHelper.insertBuilding("Daima Hotel", "4,96", "Vasile Alexandri 3", "280", "hotel");
        dbHelper.insertBuilding("Adam & Eva Hotel", "4,78", "Puskin 49", "170", "hotel");
        dbHelper.insertBuilding("Apartament in chirie Balti", "4,88", "Florilor 45", "195", "apartament");


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
