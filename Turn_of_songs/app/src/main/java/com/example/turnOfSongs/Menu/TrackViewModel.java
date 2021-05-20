package com.example.turnOfSongs.Menu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.turnOfSongs.Menu.TrackRepository;
import com.example.turnOfSongs.Menu.Track;

public class TrackViewModel extends ViewModel {
    public TrackRepository repository;

    public TrackViewModel() {
        repository = TrackRepository.getInstance();
    }

    public LiveData<Track> getSearchedTrack() {
        return repository.getSearchedTrack();
    }

    private String authToken;

    public void setAuthToken(String token){
        if(token != null && !token.isEmpty()){
            authToken = token;
            return;
        }
        Log.i("TrackViewModel","TokenIsInvalid");
    }

    public void searchForTrack(String s) {
        Log.d("TrackViewModel","TVM.searchForTrack : " + s);
        repository.searchForTrack(authToken,s);
    }
}
