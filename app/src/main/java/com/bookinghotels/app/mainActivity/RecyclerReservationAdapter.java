package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class RecyclerReservationAdapter extends RecyclerView.Adapter<RecyclerReservationAdapter.ViewHolder> {
    private List<Reservations> reservationsList;
    static Context context;
    private DataBaseHelper dataBaseHelper;
    private int idHotel;


    RecyclerReservationAdapter(Context context, List<Reservations> reservationsList) {

        this.reservationsList = new ArrayList<Reservations>();
        this.reservationsList = reservationsList;
        this.context = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    @Override
    public RecyclerReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View reservationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_reservated_main, null);
        reservationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        RecyclerReservationAdapter.ViewHolder viewholder = new RecyclerReservationAdapter.ViewHolder(reservationView);
        return viewholder;
    }

    private void showRatingDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View ratingView = layoutInflater.inflate(R.layout.rating_main, null, false);

        final RatingBar ratingBar = ratingView.findViewById(R.id.ratingView);
        final TextView txtRatingNote = ratingView.findViewById(R.id.txtRatingNote);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setView(ratingView)
                .setPositiveButton("Apreciaza", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        txtRatingNote.setText(String.valueOf(rating));

                    }
                });

                dataBaseHelper.makeRating(idHotel, Float.parseFloat( String.valueOf(ratingBar.getRating())));

                alert.dismiss();
            }


        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hotelTitle, hotelAddress, roomNumber, roomPrice, roomType, dateIn, dateOut, nrPers;


        public ViewHolder(View reservationLayoutView) {
            super(reservationLayoutView);
            hotelTitle = (TextView) reservationLayoutView.findViewById(R.id.hotelTitleView);
            hotelAddress = (TextView) reservationLayoutView.findViewById(R.id.hotelAddressView);
            roomNumber = (TextView) reservationLayoutView.findViewById(R.id.roomNumberView);
            roomPrice = (TextView) reservationLayoutView.findViewById(R.id.roomPriceView);
            roomType = (TextView) reservationLayoutView.findViewById(R.id.roomTypeView);
            dateIn = (TextView) reservationLayoutView.findViewById(R.id.dateInView);
            dateOut = (TextView) reservationLayoutView.findViewById(R.id.dateOutView);
            nrPers = (TextView) reservationLayoutView.findViewById(R.id.nrPersView);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerReservationAdapter.ViewHolder holder, int position) {
        holder.hotelTitle.setText(reservationsList.get(position).HotelTitle);
        holder.hotelAddress.setText(reservationsList.get(position).HotelAddress);
        holder.roomNumber.setText(String.valueOf(reservationsList.get(position).RoomNumber));
        holder.roomPrice.setText(String.valueOf(reservationsList.get(position).RoomPrice));
        holder.roomType.setText(reservationsList.get(position).RoomType);
        holder.dateIn.setText(reservationsList.get(position).DateIn);
        holder.dateOut.setText(reservationsList.get(position).DateOut);
        holder.nrPers.setText(String.valueOf(reservationsList.get(position).NrPers));
        idHotel = reservationsList.get(position).HotelId;
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }

}
