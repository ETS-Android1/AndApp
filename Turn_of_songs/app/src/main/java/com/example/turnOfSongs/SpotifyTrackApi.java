package com.example.turnOfSongs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SpotifyTrackApi {

    @GET("api/v1/search?q={title}&type=track")
    Call<TrackResponse> getTrack(@Header("Authorization") String authorization, @Path("title") String title);

}
