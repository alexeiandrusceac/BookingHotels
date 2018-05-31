package com.bookinghotels.app.mainActivity.UserActions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.UserActions.User.User;

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
    private AppCompatButton registerButton;
    private ValidationUserInputData valUserData;
    private DataBaseHelper userDBHelper;
    private User userData;
    private ImageView userImage;
    private int RESULT_LOAD_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        getSupportActionBar().hide();

        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameInputLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        prenameInputLayout = (TextInputLayout) findViewById(R.id.user_prename_layout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.password_layout);
        confPasswordInputLayout = (TextInputLayout) findViewById(R.id.user_confpass_layout);
        registerButton = (AppCompatButton) findViewById(R.id.register_button);

        ///Initializarea listeners
        registerButton.setOnClickListener(this);
        valUserData = new ValidationUserInputData(compatActivity);
        userDBHelper = new DataBaseHelper(compatActivity);
        userImage = (ImageView)findViewById(R.id.userImage);

        userImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });
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
        if (!valUserData.textFilled(emailInput, emailInputLayout, getString(R.string.error_email))) {
            return;
        }
        if (!valUserData.nameValidating(nameInput, nameInputLayout, getString(R.string.error_email))) {
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
            userData.Image = Integer.parseInt(userImage.toString().trim());

            userDBHelper.registerNewUser(userData);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emailInput.setText(null);
            nameInput.setText(null);

            Intent loginActivity =  new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }
}
