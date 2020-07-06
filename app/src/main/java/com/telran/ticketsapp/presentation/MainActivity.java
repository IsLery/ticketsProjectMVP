package com.telran.ticketsapp.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       setSupportActionBar( binding.appToolbar);
       navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navView,navController);
        binding.navView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),binding.drawerLayout);
    }

    //    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }
////        else if(getSupportFragmentManager().getBackStackEntryCount()>1){
////            getSupportFragmentManager().
////        }
//        else{
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.events_item:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.root,new EventListFragment())
//                  //      .addToBackStack("LIST")
//                        .commit();
                NavOptions options = new NavOptions.Builder()
                        .setPopUpTo(R.id.main_navigation,true)
                        .build();

               navController.navigate(R.id.action_global_eventListFragment,null,options);
                break;
            case R.id.login_item:
//                getSupportFragmentManager().beginTransaction()
////                        .replace(R.id.root,new LoginFragment())
////                  //      .addToBackStack("LOGIN")
////                        .commit();
              //  Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_eventListFragment_to_loginFragment);
                navController.navigate(R.id.action_global_loginFragment);
                break;
            case R.id.cart_item:
              //  Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_eventListFragment_to_cartFragment);
                navController.navigate(R.id.action_global_cartFragment);
                break;
            case android.R.id.home:
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }else {
                    return false;
                }

            default:
                Toast.makeText(this, "No fragment yet", Toast.LENGTH_SHORT).show();
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
       // Log.d("MY_TAG", "onPause called: ");
        super.onPause();
    }
}
