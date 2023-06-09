package com.example.h071211039_finalmobile.model.Data;

import com.example.h071211039_finalmobile.model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowService {
    @GET("tv/on_the_air")
    Call<TvResponse> getAiringTodayTV(
            @Query("api_key") String apiKey
    );
}
