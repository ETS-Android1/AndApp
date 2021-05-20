package com.example.turnOfSongs.Menu;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyTrackApi {

    @GET("api.spotify.com/v1/search")
    Call<TrackResponse> getTrack(@Header("Authorization") String authorization, @Query("name") String title, @Query("type") String type);

}
