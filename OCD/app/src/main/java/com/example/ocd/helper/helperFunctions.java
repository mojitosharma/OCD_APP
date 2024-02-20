package com.example.ocd.helper;

import java.util.regex.Pattern;

public class helperFunctions {
    // Function to check if the email has a valid format
    public static boolean isValidEmail(String email) {
        // You can use a more complex email validation logic if needed
        String emailRegex = "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Helper method to check if the DOB is in the correct format (DD/YY/MM)
    public static boolean isValidDOBFormat(String dob) {
        String dobPattern = "\\d{2}/\\d{2}/\\d{2}";
        if (dob.matches(dobPattern)) {
            // Check if values are numbers only
            String[] parts = dob.split("/");

            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // Check if values are within a valid range
            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // Helper method to check if a string is numeric
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
