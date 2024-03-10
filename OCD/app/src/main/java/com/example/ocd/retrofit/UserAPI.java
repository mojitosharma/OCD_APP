package com.example.ocd.retrofit;

import com.example.ocd.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserAPI {

    @POST("/auth/register")
    Call<ResponseBody> registerUser(@Body User user);

    @PUT("/auth/verify-account")
    Call<ResponseBody> verifiyAccount(@Query("email") String email, @Query("otp") String otp);

    @PUT("/auth/regenerate-otp")
    Call<ResponseBody> regenerateOTP(@Query("email") String email);

}