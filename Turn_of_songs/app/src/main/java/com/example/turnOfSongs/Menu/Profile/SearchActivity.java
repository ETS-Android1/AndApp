package com.example.turnOfSongs.Menu.Profile;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turnOfSongs.Menu.TrackRepository;
import com.example.turnOfSongs.Menu.TrackViewModel;
import com.example.turnOfSongs.Menu.TrackViewModelFactory;
import com.example.turnOfSongs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;

public class SearchActivity extends MainActivity implements SearchView.OnQueryTextListener{

    //Spotify
    private static final String CLIENT_ID = "93f5b48731cc487b871e27d6be2f7d5f";
    private static final String REDIRECT_URI = "com.example.turnOfSongs://callback";
    private static final int REQUEST_CODE = 1337;

    private SpotifyAppRemote mSpotifyAppRemote;

    // Some data
    private TrackViewModel viewModel;
    private TrackViewModelFactory factory;
    private TrackRepository repository;
    public ArrayList<com.example.turnOfSongs.Menu.Track> tracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);


        //initialization of the data
        dLayout = findViewById(R.id.search_drawer_layout);
        nView = findViewById(R.id.search_navigation_view);
        tBar = findViewById(R.id.search_toolbar);
        authen = FirebaseAuth.getInstance();
        repository = new TrackRepository();
        factory = new TrackViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(TrackViewModel.class);
        tracks = new ArrayList<>();

        viewModel.getSearchedTrack().observe(this,track -> {
            Toast.makeText(this,"INSIDE",Toast.LENGTH_SHORT).show();
        });

        //bring to the front the navigation view
        nView.bringToFront();

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, dLayout,tBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dLayout.addDrawerListener(toggle);
        toggle.syncState();

        //add an event lister to the navigation items
        nView.setNavigationItemSelectedListener(this);

        RecyclerView mSpotifyTrackList = (RecyclerView)findViewById(R.id.search_recyclerView);
        SearchView search = (SearchView) findViewById(R.id.search_searchView);
        search.setOnQueryTextListener(this);

        mSpotifyTrackList.hasFixedSize();
        mSpotifyTrackList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        SpotifyTrackAdapter mSpotifyTrackAdapter = new SpotifyTrackAdapter(tracks);
        mSpotifyTrackList.setAdapter(mSpotifyTrackAdapter);

    }



    //connect to spotify
    @Override
    protected void onStart() {
        super.onStart();


        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{""});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        /*
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {

                        mSpotifyAppRemote = spotifyAppRemote;
                        // Now you can start interacting with App Remote
                        connected();

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(SearchActivity.this,"Spotify connection:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Toast.makeText(this,"Succefully connected to Spotify" ,Toast.LENGTH_LONG).show();
                    Log.i("SearchActivity","Token successfully aquired " + response.getAccessToken());
                    viewModel.setAuthToken(response.getAccessToken());
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(this,"ERROR  ",Toast.LENGTH_LONG).show();
                    Log.i("SearchActivity","Error in authentification");
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.i("SearchActivity","Error in authentification");
                    Toast.makeText(this,"Auth canceled  ",Toast.LENGTH_LONG).show();
                    // Handle other cases
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Play a playlist
        //mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
        Toast.makeText(SearchActivity.this, "connected to spotify", Toast.LENGTH_SHORT).show();

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Toast.makeText(SearchActivity.this, track.name + " by " + track.artist.name, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(this, "track search: "+text, Toast.LENGTH_LONG).show();
        //searchForTrack(search);
        Log.d("onQuery","onQueryTextSubmit : " + query);
        viewModel.searchForTrack(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}