package com.example.demo.API;

import com.example.demo.Login.LoginRequest;
import com.example.demo.Login.LoginResponse;
import com.example.demo.Registration.RegisterRequest;
import com.example.demo.Registration.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

}
