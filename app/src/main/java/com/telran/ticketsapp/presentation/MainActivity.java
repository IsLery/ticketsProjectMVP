package com.telran.ticketsapp.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.databinding.ActivityMainBinding;
import com.telran.ticketsapp.presentation.eventList.view.EventListFragment;
import com.telran.ticketsapp.presentation.login.view.LoginFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        drawer = binding.drawerLayout;
        setSupportActionBar( binding.appToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, binding.appToolbar,
                R.string.drawer_open,R.string.drawer_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = binding.navView;
        navView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root, new EventListFragment())
                    .commit();
            navView.setCheckedItem(R.id.events_item);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
//        else if(getSupportFragmentManager().getBackStackEntryCount()>1){
//            getSupportFragmentManager().
//        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.events_item:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root,new EventListFragment())
                  //      .addToBackStack("LIST")
                        .commit();
                break;
            case R.id.login_item:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root,new LoginFragment())
                  //      .addToBackStack("LOGIN")
                        .commit();
                break;
            default:
                Toast.makeText(this, "No fragment yet", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
       // Log.d("MY_TAG", "onPause called: ");
        super.onPause();
    }
}
