package com.example.ocd.retrofit;

import com.example.ocd.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @POST("/users/register")
    Call<User> registerUser(@Body User user);

    @GET("/users/getOTP")
    Call<Integer> getOTPToken(@Query("email") String email);

}