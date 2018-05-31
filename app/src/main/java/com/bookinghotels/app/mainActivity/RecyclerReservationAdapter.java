package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Reservations.Reservations;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;


import java.util.ArrayList;
import java.util.List;

public class RecyclerReservationAdapter extends RecyclerView.Adapter<RecyclerReservationAdapter.ViewHolder> {
        private List<Reservations> reservationsList;
        static Context context;


    RecyclerReservationAdapter(Context context, List<Reservations> reservationsList)
    {
        this.reservationsList = new ArrayList<Reservations>();
        this.reservationsList = reservationsList;
        this.context = context;

    }
    @Override
    public RecyclerReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View reservationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_reservated_main,null);

        ViewHolder viewholder =new ViewHolder(reservationView);
        return  viewholder;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hotelTitle,hotelAddress, roomNumber,roomPrice,roomType,dateIn, dateOut,nrPers;

        public ViewHolder(View reservationLayoutView) {
            super(reservationLayoutView);
            hotelTitle = (TextView) reservationLayoutView.findViewById(R.id.hotelTitleView);
            hotelAddress = (TextView)reservationLayoutView.findViewById(R.id.hotelAddressView);
            roomNumber = (TextView)reservationLayoutView.findViewById(R.id.roomNumberView);
            roomPrice = (TextView)reservationLayoutView.findViewById(R.id.roomPriceView);
            roomType = (TextView)reservationLayoutView.findViewById(R.id.roomTypeView);
            dateIn = (TextView)reservationLayoutView.findViewById(R.id.dateInView);
            dateOut = (TextView) reservationLayoutView.findViewById(R.id.dateOutView);
            nrPers = (TextView)reservationLayoutView.findViewById(R.id.nrPersView);


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
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }

}
