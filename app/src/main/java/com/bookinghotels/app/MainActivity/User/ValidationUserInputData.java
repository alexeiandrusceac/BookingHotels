package com.bookinghotels.app.MainActivity.User;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ValidationUserInputData {
    private Context context;

    public ValidationUserInputData(Context context) {
        this.context = context;
    }

    public boolean textFilled(TextInputEditText emailInputText, TextInputLayout emailInputLayout, String message) {
        String valueEmail = emailInputText.getText().toString().trim();
        if (valueEmail.isEmpty()) {
            emailInputLayout.setError(message);
            hideKeyboard(emailInputText);
            return false;
        } else {
            emailInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean emailValidating(TextInputEditText textInputEmail,TextInputLayout textInputEmailLayout,String message)
    {
        String valueEmailVal = textInputEmail.getText().toString().trim();
        if(valueEmailVal.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(valueEmailVal).matches())
        {
            textInputEmailLayout.setError(message);
            hideKeyboard(textInputEmail);
            return false;
        }
        else
        {
            textInputEmailLayout.setErrorEnabled(false);
        }
        return true;
    }
    private void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public boolean isInputEditTextMatches(TextInputEditText textInputEditEmail, TextInputEditText textInputEditPass, TextInputLayout textInputLayout, String message) {
        String valueEmail = textInputEditEmail.getText().toString().trim();
        String valuePass = textInputEditPass.getText().toString().trim();
        if (!valueEmail.contentEquals(valuePass)) {
            textInputLayout.setError(message);
            hideKeyboard(textInputEditPass);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

}
