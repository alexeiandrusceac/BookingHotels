package com.bookinghotels.app.mainActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Buildings.Buildings;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    static List<Buildings> dbList;
    private DatePickerDialog datePicker;
    private static DataBaseHelper dataBaseHelper;
    static Context context;
    RecyclerAdapter(Context context, List<Buildings> dbList ){
        this.dbList = new ArrayList<Buildings>();
        this.context = context;
        this.dbList = dbList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, null);
        final CardView cardView = itemLayoutView.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String build = ((TextView)v.findViewById(R.id.titleView)).getText().toString();
                String checkin = ((TextView)v.findViewById(R.id.checkInView)).getText().toString();
                String checkout = ((TextView)v.findViewById(R.id.checkOutView)).getText().toString();

                showReservationDialog(build,checkin,checkout);
            }
        });

        /*itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    private void showReservationDialog(String build,String checkIn,String checkOut){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View reservationView = layoutInflater.inflate(R.layout.reservation_main,null,false);

        final TextView buildTitle = (TextView)reservationView.findViewById(R.id.titleReservation);
        final EditText nameInput = (EditText)reservationView.findViewById(R.id.nameEditAutoFill);
        final EditText prenameInput = (EditText)reservationView.findViewById(R.id.prenameEditAFill);
        final EditText emailInput = (EditText)reservationView.findViewById(R.id.emailEditAFill);
        final EditText fromDateInput = (EditText)reservationView.findViewById(R.id.nightDayFrom);
        final EditText toDateInput = (EditText)reservationView.findViewById(R.id.nightDayTo);
        final EditText maxPers= (EditText)reservationView.findViewById(R.id.personEditAFill);

        buildTitle.setText(context.getResources().getString(R.string.reservationText) +" la "+ build);
        nameInput.setText("VANEA");
        nameInput.setBackgroundColor(Color.YELLOW);
        /*prenameInput.setText();
        emailInput.setText();
*/
        new AlertDialog.Builder(context)
                .setTitle(buildTitle.toString())
                .setView(reservationView)
                .setPositiveButton("Rezervare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseHelper.makeReservation(buildTitle.getText().toString(),"Ion","Surdu",Integer.parseInt(maxPers.getText().toString()),setDate(fromDateInput).getText().toString(),setDate(toDateInput).getText().toString());
                    }
                })
                .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }
    public EditText setDate(EditText dateFrom) {
        final EditText datefrominput = dateFrom;
        dateFrom.setInputType(InputType.TYPE_NULL);
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        datefrominput.setText(date + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });
        return datefrominput;
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.title.setText(dbList.get(position).Title);
        holder.addres.setText(dbList.get(position).Address);
        holder.rating.setText(String.valueOf(dbList.get(position).Rating));
        holder.price.setText(String.valueOf(dbList.get(position).Price));
        holder.type.setText(dbList.get(position).Type);
        holder.checkIn.setText(dbList.get(position).CheckIn);
        holder.checkOut.setText(dbList.get(position).CheckOut);
        holder.image.setImageResource(dbList.get(position).Image);
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title,addres,rating,price,type,checkIn,checkOut;
        public ImageView image;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView
                    .findViewById(R.id.titleView);
            addres = (TextView)itemLayoutView.findViewById(R.id.addressView);
            rating = (TextView)itemLayoutView.findViewById(R.id.ratingView);
            price = (TextView)itemLayoutView.findViewById(R.id.priceView);
            type = (TextView)itemLayoutView.findViewById(R.id.typeView);
            checkIn = (TextView)itemLayoutView.findViewById(R.id.checkInView);
            checkOut =(TextView)itemLayoutView.findViewById(R.id.checkOutView);
            image = (ImageView)itemLayoutView.findViewById(R.id.imageView);
          //  itemLayoutView.setOnClickListener(this);

        }

        /*@Override
        public void onClick(View v) {
            Intent intent = new Intent(context,MainActivity.class);

            Bundle extras = new Bundle();
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);


            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());
            context.startActivity(intent);
            Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }*/
    }
}
