package com.example.turnOfSongs;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackRepository {
    private static TrackRepository instance;
    private final MutableLiveData<Track> searchedTrack;

    private TrackRepository() {
        searchedTrack = new MutableLiveData<>();
    }

    public static synchronized TrackRepository getInstance() {
        if (instance == null) {
            instance = new TrackRepository();
        }
        return instance;
    }

    public LiveData<Track> getSearchedTrack() {
        return searchedTrack;
    }

    public void searchForTrack(String trackName) {
        SpotifyTrackApi spotifyTrackApi = ServiceGenerator.getSpotifyTrackApi();
        Call<TrackResponse> call = spotifyTrackApi.getTrack(getAuthorization(),trackName);
        call.enqueue(new Callback<TrackResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if (response.isSuccessful()) {
                    searchedTrack.setValue(response.body().getTrack());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    private static String getAuthorization(){
        return "Bearer ";
    }
}
