package com.example.helsanf.catalogemovie.res;

import com.example.helsanf.catalogemovie.getModel.getMovie;
import com.example.helsanf.catalogemovie.model.MovieItem;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
//    @FormUrlEncoded
//    @POST("query=")
//    Call<> postMovie(@Field("query"))

    String API_KEY = "e28a617e42c74f700967d2d4d66f0426";
    String BAHASA = "en-US";
    @GET("search/movie")
    Call<getMovie> getMovie(@Query("api_key") String api_key ,
                            @Query("language") String bahasa ,
                            @Query("query") String judul);

    @GET("movie/{id}")
    Call<MovieItem> getDetail(@Path("id") int id_film ,
                              @Query("api_key") String api_key ,
                              @Query("language") String bahasa);
}
