package com.bookinghotels.app.mainActivity.User;

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

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.User.Database.DataBaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity compatActivity = RegisterActivity.this;
    private NestedScrollView scrollView;
    private TextInputLayout nameInputLayout;
    private TextInputEditText nameInput;
    private TextInputLayout prenameInputLayout;
    private TextInputEditText prenameInput;
    private TextInputLayout emailInputLayout;
    private TextInputEditText emailInput;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordInput;
    private TextInputLayout confPasswordInputLayout;
    private TextInputEditText confPasswd;
private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private AppCompatButton registerButton;
    private ValidationUserInputData valUserData;
    private DataBaseHelper userDBHelper;
    private User userData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getActionBar().hide();

        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameInputLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        prenameInputLayout = (TextInputLayout) findViewById(R.id.user_prename_layout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.password_layout);
        confPasswordInputLayout = (TextInputLayout) findViewById(R.id.user_confpass_layout);
        registerButton = (AppCompatButton) findViewById(R.id.register_button);
        //appBarLayout= (AppBarLayout)findViewById(R.id.register_appBarLayout);
        ///toolbar = (Toolbar)findViewById(R.id.register_toolbar);
        //setSupportActionBar(toolbar);
        ///Initializarea listeners
        registerButton.setOnClickListener(this);
        valUserData = new ValidationUserInputData(compatActivity);
        userDBHelper = new DataBaseHelper(compatActivity);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (!valUserData.textFilled(nameInput, nameInputLayout, getString(R.string.error_name))) {
            return;
        }
        if (!valUserData.textFilled(nameInput, nameInputLayout, getString(R.string.error_email))) {
            return;
        }
        if (!valUserData.emailValidating(emailInput, emailInputLayout, getString(R.string.error_email))) {
            return;
        }
        if (!valUserData.textFilled(passwordInput, passwordInputLayout, getString(R.string.error_password))) {
            return;
        }
        if (!valUserData.isInputEditTextMatches(passwordInput, confPasswd,
                confPasswordInputLayout, getString(R.string.error_password_match))) {
            return;
        }

        if (!userDBHelper.checkUserOnLogin(emailInput.getText().toString().trim())) {

            userData.Name = nameInput.getText().toString().trim();
            userData.Email = emailInput.getText().toString().trim();
            userData.Password = passwordInput.getText().toString().trim();

            userDBHelper.registerNewUser(userData);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emailInput.setText(null);
            nameInput.setText(null);
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
}