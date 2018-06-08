package com.bookinghotels.app.mainActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Hotels.Hotels;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.Rooms.Rooms;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class RecyclerHotelsAdapter extends RecyclerView.Adapter<RecyclerHotelsAdapter.ViewHolder> {
    static List<Hotels> dbList;
    static List<Hotels> copyDbList;
    private DatePickerDialog datePicker;
    private int idHotel;
    private boolean showContent = true;
    private DataBaseHelper dataBaseHelper;
    static Context context;
    private int idUser;
    private List<Rooms> listOfRooms;
    private int selectedSpinnerItem;
    private String selectedSpinnerType;
    private int selectedSpinnerId;
    private LinearLayout roomLabelLayout;
    private Spinner roomType;
    private TextView roomNumberText;
    private EditText roomPriceText;
    private LinearLayout linearLayout;

    RecyclerHotelsAdapter(Context context, List<Hotels> dbList, int user_id) {
        this.dbList = new ArrayList<Hotels>();
        this.listOfRooms = new ArrayList<Rooms>();
        this.context = context;
        this.dbList = dbList;
        copyDbList = new ArrayList<Hotels>();
        copyDbList.addAll(dbList);
        dataBaseHelper = DataBaseHelper.getInstance(context);
        this.idUser = user_id;
    }

    @Override
    public RecyclerHotelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, null);

        final Button reservationButton = itemLayoutView.findViewById(R.id.reservButton);

        itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomInsertDialog(itemLayoutView);
            }
        });
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelTitle = ((TextView) itemLayoutView.findViewById(R.id.titleView)).getText().toString();

                idHotel = Integer.parseInt(((TextView) itemLayoutView.findViewById(R.id.idView)).getText().toString());
                showReservationDialog(hotelTitle);
            }
        });


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceType")
    public void roomInsertDialog(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View insertRoom = layoutInflater.inflate(R.layout.insert_room_main, null, false);
        final EditText nrRoom = insertRoom.findViewById(R.id.roomInputPost);
        idHotel = Integer.parseInt(((TextView) v.findViewById(R.id.idView)).getText().toString());

        roomLabelLayout = insertRoom.findViewById(R.id.roomValueLayout);
nrRoom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        EditText a = v.findViewById(R.id.roomInputPost);
        a.setText("2");

        int roomNumber = Integer.parseInt(a.getText().toString());


        for (int i = 0; i < roomNumber; i++) {
            //a.setEnabled(false);
            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView roomLabel = new TextView(context);
            roomNumberText = new TextView(context);
            TextView priceLabel = new TextView(context);
            roomPriceText = new EditText(context);
            roomType = new Spinner(context);
            roomType.setAdapter(new ArrayAdapter<TypeRoom>(context, android.R.layout.simple_spinner_dropdown_item, TypeRoom.values()
            ));

            roomLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomLabel.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomLabel.setText("Cam.:");
            roomLabel.setTextSize(17);
            roomLabel.setTextColor(Color.BLACK);
            linearLayout.addView(roomLabel);

            roomNumberText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomNumberText.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomNumberText.setText(String.valueOf(idHotel + "0" + i));
            roomNumberText.setTextSize(17);
            roomNumberText.setId(i);
            linearLayout.addView(roomNumberText);

            priceLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) priceLabel.getLayoutParams()).setMargins(14, 14, 0, 0);
            priceLabel.setGravity(Gravity.CENTER);
            priceLabel.setText("pretul:");
            priceLabel.setTextSize(17);
            priceLabel.setTextColor(Color.BLACK);
            linearLayout.addView(priceLabel);

            roomPriceText.setLayoutParams(new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT));
            roomPriceText.setTextSize(17);
            roomPriceText.setId(i + 1);
            roomPriceText.setInputType(InputType.TYPE_CLASS_NUMBER);
            linearLayout.addView(roomPriceText);

            roomType.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            roomType.setId(i + 2);
            ((ViewGroup.MarginLayoutParams) roomType.getLayoutParams()).setMargins(14, 14, 0, 0);
            linearLayout.addView(roomType);

            roomLabelLayout.addView(linearLayout);

        }
    }
});
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context).setView(insertRoom).setCancelable(false).setPositiveButton(
                "Posteaza", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }
        ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog ad = alertDialog.create();
        ad.show();
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {


                final List<Rooms> ssdb = new ArrayList<>();
                for (int j = 0; j < roomLabelLayout.getChildCount(); j++) {
                    Rooms room = new Rooms();
                    LinearLayout linearLayout = (LinearLayout) roomLabelLayout.getChildAt(j);
                    for (int i = 0; i < linearLayout.getChildCount(); i++) {

                        View view = linearLayout.getChildAt(i);
                        if (view.getId() > -1) {
                            if (view.getClass().equals(TextView.class)) {
                                room.RoomNumber = Integer.parseInt(((TextView) view).getText().toString());

                            } else if (view.getClass().equals(EditText.class)) {
                                room.RoomPrice = Integer.parseInt(((EditText) view).getText().toString());

                            } else if (view.getClass().equals(Spinner.class)) {
                                room.RoomType = ((Spinner) view).getSelectedItem().toString();
                                if(listOfRooms.size()==0)
                                {
                                    room.Id_Hotel = idHotel;

                                }else
                                {
                                    room.Id_Hotel = listOfRooms.size()+1;
                                }

                                ssdb.add(room);
                            }

                        }
                    }
                }

                dataBaseHelper.insertRooms(ssdb);
                //listOfRooms.addAll(ssdb);
                listOfRooms = dataBaseHelper.getAllRooms();
                ad.dismiss();
            }
        });
    }

    /*public void onAddNumber(View v) {
        EditText a = v.findViewById(R.id.roomInputPost);
        int roomNumber = Integer.parseInt(a.getText().toString());

        for (int i = 0; i < roomNumber; i++) {
            a.setEnabled(false);
            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView roomLabel = new TextView(context);
            roomNumberText = new TextView(context);
            TextView priceLabel = new TextView(context);
            roomPriceText = new EditText(context);
            roomType = new Spinner(context);
            roomType.setAdapter(new ArrayAdapter<TypeRoom>(context, android.R.layout.simple_spinner_dropdown_item, TypeRoom.values()
            ));

            roomLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomLabel.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomLabel.setText("Cam.:");
            roomLabel.setTextSize(17);
            roomLabel.setTextColor(Color.BLACK);
            linearLayout.addView(roomLabel);

            roomNumberText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) roomNumberText.getLayoutParams()).setMargins(14, 14, 0, 0);
            roomNumberText.setText(String.valueOf(idHotel + "0" + i));
            roomNumberText.setTextSize(17);
            roomNumberText.setId(i);
            linearLayout.addView(roomNumberText);

            priceLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) priceLabel.getLayoutParams()).setMargins(14, 14, 0, 0);
            priceLabel.setGravity(Gravity.CENTER);
            priceLabel.setText("pretul:");
            priceLabel.setTextSize(17);
            priceLabel.setTextColor(Color.BLACK);
            linearLayout.addView(priceLabel);

            roomPriceText.setLayoutParams(new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT));
            roomPriceText.setTextSize(17);
            roomPriceText.setId(i + 1);
            roomPriceText.setInputType(InputType.TYPE_CLASS_NUMBER);
            linearLayout.addView(roomPriceText);

            roomType.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            roomType.setId(i + 2);
            ((ViewGroup.MarginLayoutParams) roomType.getLayoutParams()).setMargins(14, 14, 0, 0);
            linearLayout.addView(roomType);

            roomLabelLayout.addView(linearLayout);

        }

    }*/

    private void showReservationDialog(final String hotel) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View reservationView = layoutInflater.inflate(R.layout.reservation_main, null, false);
        final EditText fromDateInput = setDate(R.id.dateInInput, reservationView);

        final EditText toDateInput = setDate(R.id.dateOutInput, reservationView);
        final EditText maxPers = (EditText) reservationView.findViewById(R.id.personEditAFill);
        final RangeSeekBar rangeSeekBar = (RangeSeekBar) reservationView.findViewById(R.id.rangeSeekbar);
        final TextView fromPrice = (TextView) reservationView.findViewById(R.id.from_Price_View);
        final TextView toPrice = (TextView) reservationView.findViewById(R.id.to_Price_View);
        final TextView separator = (TextView) reservationView.findViewById(R.id.separatorView);
        String titleReservation = context.getResources().getString(R.string.reservationText) + " la " + hotel;
        final String dateFrom = setDate(R.id.dateInInput, reservationView).getText().toString();
        final String dateTo = setDate(R.id.dateOutInput, reservationView).getText().toString();
        final Spinner roomSpinner = reservationView.findViewById(R.id.roomNumberViewSpinner);
        final TextView roomType = reservationView.findViewById(R.id.roomTypeView);

        rangeSeekBar.setNotifyWhileDragging(true);
        rangeSeekBar.setRangeValues(0, 1000);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar

                final List<Integer> listOfRNumber = new ArrayList<>();

                fromPrice.setText(String.valueOf(minValue));
                toPrice.setText(String.valueOf(maxValue));

                listOfRooms = dataBaseHelper.getRooms(idHotel, minValue, maxValue);
                for (Rooms room : listOfRooms) {
                    listOfRNumber.add(room.RoomNumber);
                }
                if (listOfRNumber.size() > 0) {
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context,
                            android.R.layout.simple_spinner_item, listOfRNumber);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    roomSpinner.setAdapter(adapter);

                    roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSpinnerItem = Integer.parseInt(parent.getItemAtPosition(position).toString());
                            String string = "";
                            int idRoom = 0;

                            for (Rooms room : listOfRooms) {

                                if (room.RoomNumber == selectedSpinnerItem) {
                                    string = room.RoomType;
                                    idRoom = room.Id_Room;
                                    break;
                                }

                            }
                            selectedSpinnerType = string;
                            selectedSpinnerId = idRoom;

                            roomType.setText(selectedSpinnerType);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });


                } else {

                    roomType.setText("");
                    roomSpinner.setAdapter(null);

                }


            }

        });
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(titleReservation)
                .setView(reservationView)
                .setPositiveButton("Rezervare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog ad = alertDialog.create();
        ad.show();
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (roomSpinner.getAdapter() == null) {
                    Toast.makeText(context, "Introduceti de la ce pret !", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    dataBaseHelper.makeReservation(idUser, selectedSpinnerId, idHotel, Integer.parseInt(maxPers.getText().toString()), fromDateInput.getText().toString(), toDateInput.getText().toString());
                }
                ad.dismiss();

            }
        });
    }

    public EditText setDate(int element, View dateView) {

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
    public void onBindViewHolder(RecyclerHotelsAdapter.ViewHolder holder, int position) {
        byte[] byteArray = dbList.get(position).Image;
        holder.title.setText(dbList.get(position).Title);
        holder.addres.setText(dbList.get(position).Address);
        holder.zip.setText(dbList.get(position).Zip);
        holder.rating.setText(String.valueOf(dbList.get(position).Rating));
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
        holder.phone.setText(dbList.get(position).Phone);
        holder.city.setText(dbList.get(position).City);
        holder.idHotel.setText(String.valueOf(dbList.get(position).Id_Hotel));

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, addres, rating, phone, zip, city, idHotel;
        public ImageView image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.titleView);
            zip = (TextView) itemLayoutView.findViewById(R.id.zipView);
            addres = (TextView) itemLayoutView.findViewById(R.id.addressView);
            rating = (TextView) itemLayoutView.findViewById(R.id.ratingView);
            image = (ImageView) itemLayoutView.findViewById(R.id.imageView);
            phone = (TextView) itemLayoutView.findViewById(R.id.phoneView);
            city = (TextView) itemLayoutView.findViewById(R.id.cityView);
            idHotel = (TextView) itemLayoutView.findViewById(R.id.idView);

        }

    }

    public void filter(String queryText) {
        dbList.clear();

        if (queryText.isEmpty()) {
            dbList.addAll(copyDbList);
        } else {

            for (Hotels name : copyDbList) {
                if (name.Title.toLowerCase().contains(queryText.toLowerCase()) || name.City.toLowerCase().contains(queryText.toLowerCase())) {
                    dbList.add(name);
                }

            }

        }

        notifyDataSetChanged();
    }
}
