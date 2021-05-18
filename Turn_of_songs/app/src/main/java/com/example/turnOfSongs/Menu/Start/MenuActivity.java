package com.example.turnOfSongs.Menu.Start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.turnOfSongs.R;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    //Data we want to get out of the design
    private Button btnSign;
    private Button btnLog;

    //Data
    private Pair[] nbrAnimation;
    private int counter;
    private static final String CLIENT_ID = "93f5b48731cc487b871e27d6be2f7d5f";
    private static final String REDIRECT_URI = "com.example.turnOfSongs://callback";
    private SpotifyAppRemote mSpotifyAppRemote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supposed to make the notification bar disappear
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        //initialization of the data
        btnSign = findViewById(R.id.btnSign);
        btnLog = findViewById(R.id.btnLogin);
        nbrAnimation = new Pair[1];
        counter = 0;

        //adding an event listener to buttons
        btnLog.setOnClickListener(this);
        btnSign.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        //whenever you click on the backpress key the counter= counter+1
        counter++;
        //if you click twice you exit the app
        if (counter == 2) {
            counter = 0;
            this.finishAffinity();
        }
        //it makes it impossible to the user to go back and log in the account of the former user and so one
        else {
            Toast.makeText(this, "Clic twice to exit", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if the user click on the button with the id of the login button
            case R.id.btnLogin:
                Intent intentLog = new Intent(MenuActivity.this, LoginActivity.class);
                //animation part
                nbrAnimation[0] = new Pair<View, String>(btnLog, "transition_menu_login_btn");
                //if the user used an upper or an equal version that the LOLLIPOP one
                //he can have an animated transition to the login screen
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuActivity.this, nbrAnimation);
                    startActivity(intentLog, options.toBundle());
                }
                //else he don't have a transition and the login screen is launch automatically
                else {
                    startActivity(intentLog);
                }
                break;
            //if the user click on the button with the id of the sign in button
            case R.id.btnSign:
                Intent intentSign = new Intent(MenuActivity.this, SignupActivity.class);
                //animation part
                nbrAnimation[0] = new Pair<View, String>(btnSign, "transition_menu_sign_btn");
                //if the user used an upper or an equal version that the LOLLIPOP one
                //he can have an animated transition to the login screen
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuActivity.this, nbrAnimation);
                    startActivity(intentSign, options.toBundle());
                }
                //else he don't have a transition and the login screen is launch automatically
                else {
                    startActivity(intentSign);
                }
                break;

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Toast.makeText(MenuActivity.this,"Spotify connection:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Play a playlist
        //mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
        Toast.makeText(MenuActivity.this, "connected to spotify", Toast.LENGTH_SHORT).show();

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Toast.makeText(MenuActivity.this, track.name + " by " + track.artist.name, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}