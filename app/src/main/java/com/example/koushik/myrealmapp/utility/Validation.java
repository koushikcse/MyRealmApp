package com.example.koushik.myrealmapp.utility;

/**
 * Created by innofied on 3/1/17.
 */

public class Validation {

    public static boolean isFieldEmpty(String fieldString) {
        boolean isEmpty = false;
        if (fieldString.length() == 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static boolean isEmailInvalid(String email) {
        return !(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isTherePasswordMismatch(String password, String confirmPassword) {
        boolean isMismatch = true;
        if (password.equals(confirmPassword)) {
            isMismatch = false;
        }
        return isMismatch;
    }

    public static boolean isMobileValid(String mobile) {
        boolean isMismatch = false;
        if (mobile.length() < 10) {
            isMismatch = true;
        }
        return isMismatch;

    }
}