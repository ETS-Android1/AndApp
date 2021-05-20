package com.example.turnOfSongs.Menu;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static SpotifyTrackApi trackApi;

    public static SpotifyTrackApi getSpotifyTrackApi(String auth) {
        if (trackApi == null) {
            OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

            okhttpbuilder.addInterceptor( chain -> {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder().header("Authorization","Bearer " + auth);
                Log.i("ServiceGenerator","Interceptor, auth : " + auth);
                return chain.proceed(newRequest.build());
            });

            Retrofit.Builder retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com")
                    .client(okhttpbuilder.build())
                    .addConverterFactory(GsonConverterFactory.create());

            trackApi = retrofit
                    .build()
                    .create(SpotifyTrackApi.class);
        }
        return trackApi;
    }

}
