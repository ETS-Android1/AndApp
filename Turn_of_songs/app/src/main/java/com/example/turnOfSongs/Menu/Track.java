package com.example.turnOfSongs.Menu;

public class Track {
    
    private String id;
    private String track_name;
    private String[] artists_names;
    private int duration_ms;
    private String url;

    public Track(String id, String track_name, String[] artists_names, int duration_ms, String url){
        this.id = id;
        this.track_name = track_name;
        this.artists_names = artists_names;
        this.duration_ms = duration_ms;
        this.url = url;
    }

    public String getTrackId(){
        return this.id;
    }

    public String getTrackName(){
        return this.track_name;
    }

    public String[] getTrackArtistNames(){
        return this.artists_names;
    }

    public int getTrackDuration_ms(){
        return this.duration_ms;
    }

    public String getTrackUrl(){
        return this.url;
    }
}
