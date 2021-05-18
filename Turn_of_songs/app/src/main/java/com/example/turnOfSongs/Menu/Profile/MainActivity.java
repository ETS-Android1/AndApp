package com.example.turnOfSongs.Menu.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.turnOfSongs.Menu.Start.MenuActivity;
import com.example.turnOfSongs.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Data we want to get out of the design
    DrawerLayout dLayout;
    NavigationView nView;
    androidx.appcompat.widget.Toolbar tBar;

    //Data
    private FirebaseAuth authen;
    private String lastFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the window fullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //initialization of the data
        dLayout = findViewById(R.id.drawer_layout);
        nView = findViewById(R.id.navigation_view);
        tBar = findViewById(R.id.toolbar);
        authen = FirebaseAuth.getInstance();

        //bring to the front the navigation view
        nView.bringToFront();

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, dLayout,tBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dLayout.addDrawerListener(toggle);
        toggle.syncState();

        //add an event lister to the navigation items
        nView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        //if on beck press key is clicked
        //and the navigation bar is open
        if(dLayout.isDrawerOpen(GravityCompat.START)){
            //we closed the navigation bar
            dLayout.closeDrawer((GravityCompat.START));
        }
        else{
            //we close the app
            this.finishAffinity();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navigation_toolbar_my_playlist:
                PlaylistsFragment playlistsFragment = new PlaylistsFragment();
                //launch the fragment corresponding to playists with a tag in case we want to see its visibility
                LaunchFragment(playlistsFragment,"PlaylistsFragment");
                break;
            case R.id.navigation_toolbar_profil:
                ProfileFragment profileFragment = new ProfileFragment();
                //launch the fragment corresponding to profile with a tag in case we want to see its visibility
                LaunchFragment(profileFragment,"ProfileFragment");
                break;
            case R.id.navigation_toolbar_search:
                SearchFragment searchFragment = new SearchFragment();
                //launch the fragment corresponding to search with a tag in case we want to see its visibility
                LaunchFragment(searchFragment,"SearchFragment");
                break;
            case R.id.navigation_toolbar_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_toolbar_logout:
                LogOut();
        }
        dLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Launch the display of the fragment
    private void LaunchFragment(Fragment frag,String tag){
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        //set the transition
        fTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        //if the container of the fragment is not empty
        if(!fTransaction.isEmpty()){
            //we suppress the last fragment
            fTransaction.remove(fManager.findFragmentByTag(lastFragmentTag)).commit();
        }
        //we add a fragment to the container
        fTransaction.add(R.id.profil_fragment_container,frag,tag);
        //then we commit
        fTransaction.commit();
        //we update lastFragment
        lastFragmentTag = tag;
    }

    private void LogOut() {
        //we sign out from the profil
        authen.getInstance().signOut();
        //we go back to the menu
        Intent intentMenu = new Intent(this, MenuActivity.class);
        startActivity(intentMenu);
    }
}