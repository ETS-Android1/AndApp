package com.example.turn_of_songs.Menu.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.turn_of_songs.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dLayout;
    NavigationView nView;
    androidx.appcompat.widget.Toolbar tBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supposed to make the notification bar disappear
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        dLayout = findViewById(R.id.drawer_layout);
        nView = findViewById(R.id.navigation_view);
        tBar = findViewById(R.id.toolbar);

        nView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, dLayout,tBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dLayout.addDrawerListener(toggle);
        toggle.syncState();

        nView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if(dLayout.isDrawerOpen(GravityCompat.START)){
            dLayout.closeDrawer((GravityCompat.START));
        }
        else{
            this.finishAffinity();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navigation_toolbar_my_playlist:
                Intent intentPlaylist = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intentPlaylist);
                break;
            case R.id.navigation_toolbar_profil:
                break;
            case R.id.navigation_toolbar_search:
                Intent intentSearch = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.navigation_toolbar_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;

        }
        dLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}