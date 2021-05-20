package com.example.turnOfSongs.Menu;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnOfSongs.Menu.Track;

import retrofit2.Call;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackRepository {
    private static TrackRepository instance;
    private final MutableLiveData<Track> searchedTrack;


    public TrackRepository() {
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

    public void searchForTrack(String auth, String trackName) {
        if(!(auth != null && !auth.isEmpty())){
            Log.i("TrackRepository","TokenIsInvalid");
            return;
        }

        Log.i("TrackRepository","TR.searchForTrack : " + trackName +" auth : " + auth);

        SpotifyTrackApi spotifyTrackApi = ServiceGenerator.getSpotifyTrackApi(auth);
        Call<TrackResponse> call = spotifyTrackApi.getTrack(trackName,"track");


        call.enqueue(new Callback<TrackResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("TrackRepository","TrackRepository.onResponse : Successful");
                    searchedTrack.setValue(response.body().getTrack());
                }
                else if(response.code() == 400) {
                    Log.i("TrackRepository","TrackRepository.onResponse : Unvalid authentication bearer ResponseCode : " + response.code());
                }
                else if (response.code() == 401){
                    Log.i("TrackRepository","TrackRepository.onResponse : The access token expired : ResponseCode : " + response.code());
                }else if (response.code() == 429){
                    Log.i("TrackRepository","" + "Too many request : ResponseCode : " + response.code());
                }
                else {
                    Log.i("TrackRepository","TrackRepository.onResponse : Not successful : ResponseCode : " + response.code());
                }
                //Log.i("TrackRepository",response.headers().toString());
                Log.i("TrackRepository","Request Header : " + call.request().headers().toString());

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

}
