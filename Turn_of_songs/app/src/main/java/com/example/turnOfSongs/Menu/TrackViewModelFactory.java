package com.example.turnOfSongs.Menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrackViewModelFactory implements ViewModelProvider.Factory {

    private  final TrackRepository repository;

    public TrackViewModelFactory(TrackRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T res = null;
        if(modelClass.isAssignableFrom(TrackViewModel.class)){
            res = (T) new TrackViewModel();
        }
        else{
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
        return res;
    }
}
