package com.example.turnOfSongs;

import com.google.gson.annotations.SerializedName;

public class TrackResponse {
    private String id;
    @SerializedName("name")
    private String track_name;
    private Artist[] artists;
    private int duration_ms;
    private ExternalUrl external_urls;

    public Track getTrack() {
        String[] artists_names = new String[artists.length];
        for (int i = 0; i< artists_names.length; i++) {
            artists_names[i] = artists[i].artist_name;
        }
        return new Track(
                id,
                track_name,
                artists_names,
                duration_ms,
                external_urls.url
        );
    }

    private static class Artist {
        @SerializedName("name")
        private String artist_name;
    }

    private static class ExternalUrl {
        @SerializedName("spotify")
        private String url;
    }
}
