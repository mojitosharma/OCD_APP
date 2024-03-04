package com.example.ocd;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.ocd.model.User;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyOTPActivity extends AppCompatActivity {

    private EditText optTextViewOne, optTextViewTwo, optTextViewThree, optTextViewFour;
    private Button btnLetsBeginMyTherapy;
    private TextView txtDuration, otpSendText;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private String otp;

    User user;
    RetrofitService retrofitService;


    private static final long COUNTDOWN_DURATION = 60000; // 60 seconds
    private static final long COUNTDOWN_INTERVAL = 1000; // 1 second


    // todo stop the time when the screen changes
    // todo hash the password

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
        user = (User) getIntent().getSerializableExtra("USER");
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
        otpSendText.setText(getString(R.string.msg_validateOTP, user.getEmail()));
        retrofitService = new RetrofitService();
        getOTPToken(user.getEmail());
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
        btnLetsBeginMyTherapy.setOnClickListener(v -> {
            if (isOtpValid()) {
                Intent intent = new Intent(VerifyOTPActivity.this, TermAndConditionActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
//                    finish();
            } else {
                showRetryDialog();
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

    // show time out error and move to the previous screen
    private void showTimeoutError() {
        // Create and show a dialog with timeout error message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Timeout Error")
                .setMessage("The verification process has timed out. What would you like to do?")
                .setPositiveButton("Retry", (dialog, which) -> {
                    // Reset OTP input fields and restart the countdown timer
                    resetOtpInputFields();
                    startCountdownTimer();
                })
                .setNegativeButton("Go Back", (dialog, which) -> {
                    // Go back to SignUpActivity with the entered details
                    navigateBackToSignUpActivity();
                })
                .setCancelable(false);

        // Show the created dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showRetryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Failed")
                .setMessage("There was an issue with the verification. What would you like to do?")
                .setPositiveButton("Retry", (dialog, which) -> {
                    // Reset OTP input fields and restart the countdown timer
                    resetOtpInputFields();
                    startCountdownTimer();
                })
                .setNegativeButton("Go Back", (dialog, which) -> {
                    // Go back to SignUpActivity with the entered details
                    navigateBackToSignUpActivity();
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private boolean isOtpValid() {
        String enteredOTP = optTextViewOne.getText().toString() +
                optTextViewTwo.getText().toString() +
                optTextViewThree.getText().toString() +
                optTextViewFour.getText().toString();
        return enteredOTP.equals(otp);
    }

    private void resetOtpInputFields() {
        // Clear the OTP input fields
        optTextViewOne.setText("");
        optTextViewTwo.setText("");
        optTextViewThree.setText("");
        optTextViewFour.setText("");
        countDownTimer.cancel();
        startCountdownTimer();
        getOTPToken(user.getEmail());
    }


    private void navigateBackToSignUpActivity() {
        // Create an Intent to go back to SignUpActivity with the entered details
        Intent intent = new Intent(VerifyOTPActivity.this, SignUpActivity.class);
        intent.putExtra("USER", user);

        // Start the SignUpActivity
        startActivity(intent);
        finish();  // Finish the current activity
    }

    // Retrieve OTP token from the server
    private void getOTPToken(String email) {
        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        userAPI.getOTPToken(email).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    otp = String.valueOf(response.body());

                } else {
                    Toast.makeText(VerifyOTPActivity.this, "Failed to retrieve OTP! Please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                // Handle failure
                Toast.makeText(VerifyOTPActivity.this, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show();
                Logger.getLogger(VerifyOTPActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
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

}

