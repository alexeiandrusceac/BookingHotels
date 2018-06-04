package com.bookinghotels.app.mainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;


import java.util.ArrayList;
import java.util.List;

public class RecyclerPostAdapter extends RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>  {
    private List<Hotels> postList;
    static Context context;


    RecyclerPostAdapter(Context context , List<Hotels> listOfPosts)
    {
        this.context = context;
        this.postList = new ArrayList<>();
        this.postList = listOfPosts;

    }


    @Override
    public RecyclerPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View postedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_posted_main,null);
        RecyclerPostAdapter.ViewHolder viewholder =new RecyclerPostAdapter.ViewHolder(postedView);
        return  viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] byteArray= postList.get(position).Image;
        holder.hotelTitle.setText(postList.get(position).Title);
        holder.hotelAddress.setText(postList.get(position).Address);
        holder.hotelZip.setText(postList.get(position).Zip);
        holder.hotelRating.setText(String.valueOf(postList.get(position).Rating));
        holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(byteArray,0,byteArray.length),holder.imageView.getWidth(),holder.imageView.getHeight(),false));
        holder.hotelPhone.setText(postList.get(position).Phone);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hotelTitle,hotelAddress, hotelRating,hotelPhone,hotelZip ;
        public ImageView imageView;

        public ViewHolder(View postLayoutView) {
            super(postLayoutView);
            hotelTitle = (TextView) postLayoutView.findViewById(R.id.titleView);
            hotelAddress = (TextView)postLayoutView.findViewById(R.id.addressView);
            hotelRating = (TextView)postLayoutView.findViewById(R.id.ratingView);
            hotelPhone = (TextView)postLayoutView.findViewById(R.id.phoneView);
            hotelZip = (TextView)postLayoutView.findViewById(R.id.zipView);
            imageView = (ImageView)postLayoutView.findViewById(R.id.imageView);
        }

    }

}
