package com.bookinghotels.app.mainActivity.UserActions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;

import com.bookinghotels.app.R;
import com.bookinghotels.app.mainActivity.Database.DataBaseHelper;
import com.bookinghotels.app.mainActivity.UserActions.User.User;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity compatActivity = RegisterActivity.this;
    private NestedScrollView scrollView;
    private TextInputEditText nameInputValue;
    private TextInputLayout nameInputLayout;
    private TextInputEditText prenameInputValue;
    private TextInputLayout prenameInputLayout;
    private TextInputEditText emailInputValue;
    private TextInputLayout emailInputLayout;
    private TextInputEditText passwordInputValue;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText confPasswdInputValue;
    private TextInputLayout confPasswordInputLayout;
    private AppCompatButton registerButton;
    private ValidationUserInputData valUserData;
    private DataBaseHelper userDBHelper;
    private User userData;
    private ImageView userImage;
    private int RESULT_LOAD_IMAGE = 1;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        getSupportActionBar().hide();

        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameInputValue = (TextInputEditText) findViewById(R.id.user_name_text);
        nameInputLayout = (TextInputLayout)findViewById(R.id.user_name_layout);
        prenameInputValue = (TextInputEditText) findViewById(R.id.user_prename_text);
        prenameInputLayout= (TextInputLayout)findViewById(R.id.user_prename_layout);
        emailInputValue= (TextInputEditText)findViewById(R.id.user_email_text);
        emailInputLayout = (TextInputLayout)findViewById(R.id.user_email_layout);
        passwordInputValue= (TextInputEditText) findViewById(R.id.user_pass_text);
        passwordInputLayout = (TextInputLayout)findViewById(R.id.user_pass_layout);
        confPasswdInputValue = (TextInputEditText) findViewById(R.id.user_confpass_text);
        confPasswordInputLayout = (TextInputLayout)findViewById(R.id.user_confpass_layout);
        registerButton = (AppCompatButton) findViewById(R.id.register_button);

        ///Initializarea listeners
        registerButton.setOnClickListener(this);
        valUserData = new ValidationUserInputData(compatActivity);
        userDBHelper = new DataBaseHelper(compatActivity);
        userImage = (ImageView)findViewById(R.id.userImage);
        userData = new User();
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


        if (!valUserData.textFilled(nameInputValue, nameInputLayout, getString(R.string.error_name))) {
            return;
        }
        /*if (!valUserData.textFilled(emailInputValue, emailInputLayout, getString(R.string.error_email))) {
            return;
        }
        if (!valUserData.nameValidating(emailInputValue, nameInputLayout, getString(R.string.error_email))) {
            return;
        }*/
        if (!valUserData.textFilled(passwordInputValue, passwordInputLayout, getString(R.string.error_password))) {
            return;
        }
        if (!valUserData.isInputEditTextMatches(passwordInputValue, confPasswdInputValue,
                confPasswordInputLayout, getString(R.string.error_password_match))) {
            return;
        }

        if (!userDBHelper.checkUserOnLogin(nameInputValue.getText().toString().trim())) {

            userData.Name = nameInputValue.getText().toString();
            userData.Prename = prenameInputValue.getText().toString();

            userData.Email = emailInputValue.getText().toString();

            userData.Password = passwordInputValue.getText().toString();
            Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            userData.Image = baos.toByteArray();

            userDBHelper.registerNewUser(userData);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emailInputValue.setText(null);
            nameInputValue.setText(null);

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
