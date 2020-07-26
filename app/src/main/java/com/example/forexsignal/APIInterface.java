package com.example.forexsignal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

        @GET("/signal")
        Call<ArrayList<Signal>> getAllSignals();
}
