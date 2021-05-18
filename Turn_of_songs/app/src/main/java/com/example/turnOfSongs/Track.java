package com.example.turnOfSongs;

public class Track {
    //de quoi est composé une chanson
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
}
