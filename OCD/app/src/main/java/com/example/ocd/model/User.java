package com.example.ocd.model;

import com.google.gson.annotations.SerializedName;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User implements Serializable {
    @SerializedName("user_id")
    private ObjectId user_id;
    @SerializedName("name")
    private Name name;
    @SerializedName("patient_number")
    private int patient_number;
    @SerializedName("dob")
    private Date dob;
    @SerializedName("day_of_enrollment")
    private Date day_of_enrollment;
    @SerializedName("gender")
    private String gender;
    @SerializedName("education")
    private String education;
    @SerializedName("occupation")
    private String occupation;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("stage")
    private int stage;
    @SerializedName("therapist_id")
    private int therapist_id;

    public User(Name name, String dob,
                String gender, String education, String occupation,
                String email, String password) {
        this.name = name;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            this.dob = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.day_of_enrollment = new Date();
        this.gender = gender;
        this.education = education;
        this.occupation = occupation;
        this.email = email;
        this.password = password;
    }

    public String getNameString() {
        return name.toString();
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getPatient_number() {
        return patient_number;
    }

    public void setPatient_number(int patient_number) {
        this.patient_number = patient_number;
    }

    public String getDobString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return  sdf.format(this.dob);
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            this.dob = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDay_of_enrollment() {
        return day_of_enrollment;
    }

    public void setDay_of_enrollment(Date day_of_enrollment) {
        this.day_of_enrollment = day_of_enrollment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}




//import com.example.ocd.retrofit.RetrofitService;
//        import com.example.ocd.retrofit.UserAPI;
//
//        import java.util.logging.Level;
//        import java.util.logging.Logger;
//
//        import retrofit2.Call;
//        import retrofit2.Callback;
//        import retrofit2.Response;
//
//
//        retrofitService = new RetrofitService();
//        UserAPI usrapi =retrofitService.getRetrofit().create(UserAPI.class);
//        usrapi.createUser(user).enqueue(new Callback<User>() {
//@Override
//public void onResponse(Call<User> call, Response<User> response) {
//        Toast.makeText(SignUpActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
//        }
//
//@Override
//public void onFailure(Call<User> call, Throwable t) {
//        Toast.makeText(SignUpActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
//        Logger.getLogger(SignUpActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
//        }
//        });