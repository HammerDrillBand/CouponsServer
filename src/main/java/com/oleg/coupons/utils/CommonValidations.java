package com.oleg.coupons.utils;

import java.util.regex.Pattern;

public class CommonValidations {
    public static boolean validateEmailAddressStructure(String emailAddress) {
        String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailFormat);
        if (!pat.matcher(emailAddress).matches()) {
            return false;
        }
        return true;
    }

    public static int validateStringLength(String string, int min, int max) {
        if (string.length() < min) {
            return -1;
        }
        if (string.length() > max) {
            return 1;
        }
        return 0;
    }
}
