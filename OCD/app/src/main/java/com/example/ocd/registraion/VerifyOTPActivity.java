package com.example.ocd.registraion;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Dialog;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.ocd.HomeActivity;
import com.example.ocd.OtpVerificationCallback;
import com.example.ocd.R;
import com.example.ocd.model.User;
import com.example.ocd.retrofit.RetrofitService;
import com.example.ocd.retrofit.UserAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity {
    private static final String PREF_NAME = "LoginPref";
    private static final String USER_DATA = "user_data";
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
            isOtpValid(new OtpVerificationCallback() {

                @Override
                public void onVerificationResult(boolean isValid) {
                    if (isValid) {
                        Intent intent = new Intent(VerifyOTPActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        cancelCountdownTimer();
                        startActivity(intent);
                        finish();
                    } else {
                        showRetryDialog();
                    }
                }
            });
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
                    navigateBackToTermAndConditionActivity();
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
                    optTextViewOne.requestFocus();
                })
                .setNegativeButton("Go Back", (dialog, which) -> {
                    // Go back to SignUpActivity with the entered details
                    navigateBackToTermAndConditionActivity();
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void isOtpValid(OtpVerificationCallback callback) {
        String enteredOTP = optTextViewOne.getText().toString() +
                optTextViewTwo.getText().toString() +
                optTextViewThree.getText().toString() +
                optTextViewFour.getText().toString();

        final boolean[] flag = {false};

        UserAPI usrapi = retrofitService.getRetrofit().create(UserAPI.class);
        Call<ResponseBody> call = usrapi.verifiyAccount(user.getEmail(), enteredOTP);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String contentType = response.headers().get("Content-Type");
                    ResponseBody responseBody = response.body();

                    try {
                        if (contentType != null && contentType.contains("application/json")) {

                            Gson gson = new Gson();
                            assert responseBody != null;
                            User registeredUser = gson.fromJson(responseBody.string(), User.class);
                            String userJson = gson.toJson(registeredUser);
                            SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(USER_DATA, userJson);
                            editor.apply();
                            flag[0] = true;
                            if (flag[0]) {
                                callback.onVerificationResult(true);
                            } else {
                                callback.onVerificationResult(false);
                            }

                        } else if (contentType != null && contentType.contains("text/plain")) {
                            assert responseBody != null;
                            String stringValue = responseBody.string();
                            Toast.makeText(VerifyOTPActivity.this, stringValue, Toast.LENGTH_SHORT).show();
                            callback.onVerificationResult(false);

                        } else {
                            runOnUiThread(() -> {
                                assert responseBody != null;
                                Toast.makeText(VerifyOTPActivity.this, "Verification failed: " + responseBody, Toast.LENGTH_SHORT).show();
                            });
                            callback.onVerificationResult(false);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    // Server returned an error response
                    Toast.makeText(VerifyOTPActivity.this, "Error: Failed to Verify OTP!! Please try again later.", Toast.LENGTH_SHORT).show();
                    callback.onVerificationResult(false);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(VerifyOTPActivity.this, "Failed to Verify OTP!!", Toast.LENGTH_SHORT).show();
                Logger.getLogger(TermAndConditionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

    }

    private void resetOtpInputFields() {
        // Clear the OTP input fields
        optTextViewOne.setText("");
        optTextViewTwo.setText("");
        optTextViewThree.setText("");
        optTextViewFour.setText("");
        cancelCountdownTimer();
        startCountdownTimer();
        getOTPToken(user.getEmail());
    }

    private void cancelCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }


    private void navigateBackToTermAndConditionActivity() {
        // Create an Intent to go back to SignUpActivity with the entered details
        Intent intent = new Intent(VerifyOTPActivity.this, TermAndConditionActivity.class);
        intent.putExtra("USER", user);
        cancelCountdownTimer();
        startActivity(intent);
        finish();
    }

    // Retrieve OTP token from the server
    private void getOTPToken(String email) {
        UserAPI usrapi = retrofitService.getRetrofit().create(UserAPI.class);
        Call<ResponseBody> call = usrapi.regenerateOTP(user.getEmail());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String contentType = response.headers().get("Content-Type");
                    ResponseBody responseBody = response.body();
                    try {
                        if (contentType != null && contentType.contains("text/plain")) {
                            assert responseBody != null;
                            String stringValue = responseBody.string();
                            Toast.makeText(VerifyOTPActivity.this, stringValue, Toast.LENGTH_SHORT).show();

                        } else {
                            runOnUiThread(() -> {
                                assert responseBody != null;
                                Toast.makeText(VerifyOTPActivity.this, "Error: " + responseBody, Toast.LENGTH_SHORT).show();
                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(VerifyOTPActivity.this, "Error: Failed to Resend OTP!! Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Toast.makeText(VerifyOTPActivity.this, "Failed to Resend OTP", Toast.LENGTH_SHORT).show();
                Logger.getLogger(TermAndConditionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }



    public void onBackPressedCustom() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBackToTermAndConditionActivity();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

}



