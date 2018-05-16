package com.bookinghotels.app.FragmentsMap.User;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bookinghotels.app.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getActionBar().hide();


    }

    @Override
    public void onClick(View v) {

    }
}
