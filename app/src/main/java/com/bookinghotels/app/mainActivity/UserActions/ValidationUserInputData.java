package com.bookinghotels.app.mainActivity.UserActions;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ValidationUserInputData {
    private Context context;

    public ValidationUserInputData(Context context) {
        this.context = context;
    }

    public boolean textFilled(TextInputEditText inputEditText, TextInputLayout textInputLayout, String message) {
        String valueData = inputEditText.getText().toString().trim();
        if (valueData.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboard(inputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean nameValidating(TextInputEditText textInputName,TextInputLayout textInputNameLayout,String message)
    {
        String valueNameVal = textInputName.getText().toString().trim();
        if(valueNameVal.isEmpty() /*|| !Patterns.DOMAIN_NAME.matcher(valueNameVal).matches()*/)
        {
            textInputNameLayout.setError(message);
            hideKeyboard(textInputName);
            return false;
        }
        else
        {
            textInputNameLayout.setErrorEnabled(false);
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
