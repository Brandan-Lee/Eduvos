package com.example.tripbuddy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_plan_trip, R.id.nav_create_memory, R.id.nav_budget, R.id.nav_view_gallery, R.id.nav_log_out)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        navigationView.setNavigationItemSelectedListener(item -> {
//
//            switch (item.getItemId()) {
//
//                case R.id.nav_home:
//                    navController.navigate(R.id.nav_home);
//                    break;
//
//                case R.id.nav_plan_trip:
//                    navController.navigate(R.id.nav_plan_trip);
//                    break;
//
//                case R.id.nav_create_memory:
//                    navController.navigate(R.id.nav_create_memory);
//                    break;
//
//                case R.id.nav_budget:
//                    navController.navigate(R.id.nav_budget);
//                    break;
//
//                case R.id.nav_view_gallery:
//                    navController.navigate(R.id.nav_view_gallery);
//                    break;
//
//                case R.id.nav_log_out:
//                    navController.navigate(R.id.nav_log_out);
//                    break;
//
//            }
//
//            drawer.closeDrawers();
//            return true;
//
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}