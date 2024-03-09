package com.example.ocd;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Dialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

//A 4-digit verification code is sent to . Please enter it here.


public class VerifyOTPActivity extends AppCompatActivity {

    private EditText optTextViewOne, optTextViewTwo, optTextViewThree, optTextViewFour;
    private Button btnLetsBeginMyTherapy;
    private TextView txtDuration, otpSendText;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;

    private String name;
    private String dob;
    private String gender;
    private String email;
    private String education;
    private String occupation;


    private static final long COUNTDOWN_DURATION = 60000; // 60 seconds
    private static final long COUNTDOWN_INTERVAL = 1000; // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        readingData();
        initialize();
        startCountdownTimer();
        validateAndEnableButton();
        onCLickBtnLetsBeginMyTherapy();
        onBackPressedCustom();

    }

    private void readingData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        dob = intent.getStringExtra("DOB");
        gender = intent.getStringExtra("GENDER");
        email = intent.getStringExtra("EMAIL");
        education = intent.getStringExtra("EDUCATION");
        occupation = intent.getStringExtra("OCCUPATION");
    }

    private void initialize() {
        optTextViewOne = findViewById(R.id.optTextViewOne);
        optTextViewTwo = findViewById(R.id.optTextViewTwo);
        optTextViewThree = findViewById(R.id.optTextViewThree);
        optTextViewFour = findViewById(R.id.optTextViewFour);
        btnLetsBeginMyTherapy = findViewById(R.id.btnLetsBeginMyTherapy);
        txtDuration = findViewById(R.id.txtDuration);
        otpSendText = findViewById(R.id.otpSendText);
        btnLetsBeginMyTherapy.setEnabled(false);
        otpSendText.setText(getString(R.string.msg_validateOTP, email));
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateCountdownText(0);
                showTimeoutError();
            }
        }.start();

        timerRunning = true;
    }

    private void updateCountdownText(long millisUntilFinished) {
        long seconds = millisUntilFinished / 1000;
        txtDuration.setText(getString(R.string.countdown_format, seconds));
    }

    private void validateAndEnableButton() {
        optTextViewOne = findViewById(R.id.optTextViewOne);
        optTextViewTwo = findViewById(R.id.optTextViewTwo);
        optTextViewThree = findViewById(R.id.optTextViewThree);
        optTextViewFour = findViewById(R.id.optTextViewFour);

// Add TextWatcher to each EditText
        optTextViewOne.addTextChangedListener(createTextWatcher(optTextViewTwo));
        optTextViewTwo.addTextChangedListener(createTextWatcher(optTextViewThree));
        optTextViewThree.addTextChangedListener(createTextWatcher(optTextViewFour));
        optTextViewFour.addTextChangedListener(createTextWatcher(null)); // Last box, no need to move to the next one
    }

    private void onCLickBtnLetsBeginMyTherapy() {
        btnLetsBeginMyTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOtpValid()) {
                    Intent intent = new Intent(VerifyOTPActivity.this, TermAndConditionActivity.class);
                    // Pass data to the next activity
                    intent.putExtra("NAME", name);
                    intent.putExtra("DOB", dob);
                    intent.putExtra("GENDER", gender);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("EDUCATION", education);
                    intent.putExtra("OCCUPATION", occupation);

                    // Start the next activity
                    startActivity(intent);
                    finish();

                    // If you encounter an issue or need to simulate a failure, show the retry dialog
                    showRetryDialog();
                }
                // check if the otp is valid if valid then send the data and go to the next screen else
            }
        });
    }

   // set text watcher on each edit text box to move to next box
    private TextWatcher createTextWatcher(final EditText nextEditText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Not needed in this case, but required to implement the TextWatcher interface
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed in this case, but required to implement the TextWatcher interface
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the length is 1 (a digit is entered)
                if (editable.length() == 1) {
                    // Move focus to the next EditText if available
                    if (nextEditText != null) {
                        nextEditText.requestFocus();
                    }
                }
                // Check if all four EditText boxes have a digit
                if (optTextViewOne.length() == 1 && optTextViewTwo.length() == 1
                        && optTextViewThree.length() == 1 && optTextViewFour.length() == 1) {
                    // Enable the button when all four digits are entered
                    btnLetsBeginMyTherapy.setEnabled(true);
                    // Change the style of the button when enabled
                    btnLetsBeginMyTherapy.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_radius_5);
                } else {
                    // Disable the button if any digit is missing
                    btnLetsBeginMyTherapy.setEnabled(false);
                    // Change the style of the button when disabled
                    btnLetsBeginMyTherapy.setBackgroundResource(R.drawable.rectangle_bg_indigo_400_7f_radius_5); // Change to the appropriate style
                }
            }
        };
    }

    // show time out error and move to previous screen
    private void showTimeoutError() {
        // Create and show a dialog with timeout error message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Timeout Error")
                .setMessage("The verification process has timed out.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // send the data back to same screen and wait to opt again
                    }
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showRetryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Failed")
                .setMessage("There was an issue with the verification. What would you like to do?")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Reset OTP input fields and restart the countdown timer
                        resetOtpInputFields();
                        startCountdownTimer();
                    }
                })
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Go back to SignUpActivity with the entered details
                        navigateBackToSignUpActivity();
                    }
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private boolean isOtpValid() {
        // Add your OTP validation logic here
        // Return true if the OTP is valid, false otherwise
        return true;  // Replace with your actual validation logic
    }


    private void resetOtpInputFields() {
        // Clear the OTP input fields
        optTextViewOne.setText("");
        optTextViewTwo.setText("");
        optTextViewThree.setText("");
        optTextViewFour.setText("");
    }

    private void navigateBackToSignUpActivity() {
        // Create an Intent to go back to SignUpActivity with the entered details
        Intent intent = new Intent(VerifyOTPActivity.this, SignUpActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("DOB", dob);
        intent.putExtra("GENDER", gender);
        intent.putExtra("EMAIL", email);
        intent.putExtra("EDUCATION", education);
        intent.putExtra("OCCUPATION", occupation);

        // Start the SignUpActivity
        startActivity(intent);
        finish();  // Finish the current activity
    }


    public void onBackPressedCustom() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBackToSignUpActivity();

            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

