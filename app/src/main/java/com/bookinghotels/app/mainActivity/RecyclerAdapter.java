package com.bookinghotels.app.mainActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    static List<Hotels> dbList;
    static List<Hotels> copyDbList;
    private DatePickerDialog datePicker;

    private boolean showContent = true;
    private static DataBaseHelper dataBaseHelper;
    static Context context;
    RecyclerAdapter(Context context, List<Hotels> dbList ){
        this.dbList = new ArrayList<Hotels>();
        this.context = context;
        this.dbList = dbList;
        copyDbList  = new ArrayList<Hotels>();
        copyDbList.addAll(dbList);
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, null);
        //final CardView cardView = itemLayoutView.findViewById(R.id.cardView);
        final Button reservationButton = itemLayoutView.findViewById(R.id.reservButton);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelTitle = ((TextView)itemLayoutView.findViewById(R.id.titleView)).getText().toString();

                showReservationDialog(hotelTitle);
            }
        });


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    private void showReservationDialog(final String build){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View reservationView = layoutInflater.inflate(R.layout.reservation_main,null,false);


        final EditText nameInput = (EditText)reservationView.findViewById(R.id.nameEditAutoFill);
        final EditText prenameInput = (EditText)reservationView.findViewById(R.id.prenameEditAFill);
        final EditText emailInput = (EditText)reservationView.findViewById(R.id.addressEditAFill);
        final EditText fromDateInput = (EditText)reservationView.findViewById(R.id.dateInInput);
        final EditText toDateInput = (EditText)reservationView.findViewById(R.id.dateOutInput);
        final EditText maxPers= (EditText)reservationView.findViewById(R.id.personEditAFill);

        //nameInput.setText("VANEA");
       // nameInput.setBackgroundColor(Color.YELLOW);
        /*prenameInput.setText();
        emailInput.setText();
*/      String titleReservation = context.getResources().getString(R.string.reservationText)+ " la " + build;
        final String dateFrom = setDate(R.id.dateInInput,reservationView).getText().toString();
        final String dateTo = setDate(R.id.dateOutInput,reservationView).getText().toString();
        new AlertDialog.Builder(context)
                .setTitle(titleReservation)
                .setView(reservationView)
                .setPositiveButton("Rezervare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseHelper.makeReservation(build,"Ion","Surdu",Integer.parseInt(maxPers.getText().toString()),dateFrom,dateTo);
                    }
                })
                .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }
    public EditText setDate(int element,View dateView) {

        final EditText dateEditText = (EditText) dateView.findViewById(element);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateEditText.setText(date + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });
        return dateEditText;
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.title.setText(dbList.get(position).Title);
        holder.addres.setText(dbList.get(position).Address);
        holder.zip.setText(dbList.get(position).Zip);
        holder.rating.setText(String.valueOf(dbList.get(position).Rating));
       // holder.price.setText(String.valueOf(dbList.get(position).Price));
        holder.image.setImageResource(dbList.get(position).Image);
        holder.phone.setText(dbList.get(position).Phone);
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title,addres,rating,price,phone,zip;
        public ImageView image;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.titleView);
            zip = (TextView)itemLayoutView.findViewById(R.id.zipView);
            addres = (TextView)itemLayoutView.findViewById(R.id.addressView);
            rating = (TextView)itemLayoutView.findViewById(R.id.ratingView);
            //price = (TextView)itemLayoutView.findViewById(R.id.priceView);
            image = (ImageView)itemLayoutView.findViewById(R.id.imageView);
            phone = (TextView)itemLayoutView.findViewById(R.id.phoneView);

        }

    }
    public void filter(String queryText)
    {
        dbList.clear();

        if(queryText.isEmpty())
        {
            dbList.addAll(copyDbList);
        }
        else
        {

            for(Hotels name: copyDbList)
            {
                if(name.Title.toLowerCase().contains(queryText.toLowerCase()))
                {
                    dbList.add(name);
                }
            }

        }

        notifyDataSetChanged();
    }
}
