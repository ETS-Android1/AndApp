package com.example.turnOfSongs.Menu;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static SpotifyTrackApi trackApi;

    public static SpotifyTrackApi getSpotifyTrackApi() {
        if (trackApi == null) {
            trackApi = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SpotifyTrackApi.class);
        }
        return trackApi;
    }

}
