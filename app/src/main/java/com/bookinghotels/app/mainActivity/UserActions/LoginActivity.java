package com.bookinghotels.app.mainActivity.UserActions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.MainActivity;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.UserActions.User.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
private final AppCompatActivity compatActivity = LoginActivity.this;
private NestedScrollView scrollView;
private TextInputLayout nameLayout;
private TextInputLayout passwordLayout;
private TextInputEditText nameInputEditText;
private TextInputEditText passwordInputEditText;
private AppCompatButton loginButton;
private TextView registerLink;
private ValidationUserInputData valUserInput;
private DataBaseHelper userDBHelper;
private AppBarLayout appBarLayout;
private Toolbar loginToolbar;

    UserSession session;


@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        userDBHelper = DataBaseHelper.getInstance(this);
        //DataBaseHelper.getInstance(this).getAllHotels();
        // User Session Manager
        session = new UserSession(getApplicationContext());
        getSupportActionBar().hide();
        ///Initializarea obiectelor din activity
        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        nameInputEditText = (TextInputEditText) findViewById(R.id.user_name_text);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.password_Input);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        registerLink = (TextView) findViewById(R.id.registerView);

        // Initializarea Listeners
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);


        valUserInput = new ValidationUserInputData(compatActivity);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.login_button:
                checkUser();
                break;
            case R.id.registerView:
                Intent intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
    private void checkUser()
    {
        if(!valUserInput.nameValidating(nameInputEditText,nameLayout,getString(R.string.error_name)))
        return;

        if(!valUserInput.textFilled(passwordInputEditText,passwordLayout,getString(R.string.error_password)))
        return;

        if(userDBHelper.checkUserOnLogin(nameInputEditText.getText().toString().trim(),passwordInputEditText.getText().toString().trim()))
        {
            User currUser = userDBHelper.getUser(nameInputEditText.getText().toString(),passwordInputEditText.getText().toString());
            session.createUserLoginSession(nameInputEditText.getText().toString(),passwordInputEditText.getText().toString());

            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
            mainActivityIntent.putExtra("Image",currUser.Image);
            mainActivityIntent.putExtra("Email",currUser.Email);
            mainActivityIntent.putExtra("Id",currUser.ID_User);

            startActivity(mainActivityIntent);
            finish();
        }
        else
        {
            Snackbar.make(scrollView,getString(R.string.error_name_password),Snackbar.LENGTH_LONG).show();
        }
    }
}
