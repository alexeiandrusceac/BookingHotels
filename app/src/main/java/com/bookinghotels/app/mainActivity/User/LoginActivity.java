package com.bookinghotels.app.mainActivity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

import com.bookinghotels.app.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
private final AppCompatActivity compatActivity = LoginActivity.this;
private NestedScrollView scrollView;
private TextInputLayout nameLayout;
private TextInputLayout passwordLayout;
private TextInputEditText nameInputEditText;
private TextInputEditText passwordInputEditText;
private AppCompatButton loginButton;
private AppCompatTextView registerLink;
private ValidationUserInputData valUserInput;
private DataBaseHelper userDBHelper;

@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        ///Initializarea obiectelor din activity
        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        nameInputEditText = (TextInputEditText) findViewById(R.id.user_name_text);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.password_Input);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        registerLink = (AppCompatTextView) findViewById(R.id.register_text_view);
        // Initializarea Listeners
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        userDBHelper = new DataBaseHelper(compatActivity);
        valUserInput = new ValidationUserInputData(compatActivity);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.login_button:
                checkUser();
                break;
            case R.id.register_button:
                Intent intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
    private void checkUser()
    {
        if(!valUserInput.textFilled(nameInputEditText,nameLayout,getString(R.string.error_email)))
        return;

        if(!valUserInput.emailValidating(nameInputEditText,nameLayout,getString(R.string.error_email)))
        return;

        if(!valUserInput.textFilled(passwordInputEditText,passwordLayout,getString(R.string.error_password)))
        return;

        if(userDBHelper.checkUserOnLogin(nameInputEditText.getText().toString().trim(),passwordInputEditText.getText().toString().trim()))
        {
            Intent accountIntent = new Intent(compatActivity, User.class);
            accountIntent.putExtra("EMAIL",nameInputEditText.getText().toString().trim());
            nameInputEditText.setText(null);
            passwordInputEditText.setText(null);
            startActivity(accountIntent);
        }
        else
        {
            Snackbar.make(scrollView,getString(R.string.error_email_password),Snackbar.LENGTH_LONG).show();
        }
    }
}
