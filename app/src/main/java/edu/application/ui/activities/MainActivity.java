package edu.application.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.application.R;
import edu.application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private BottomNavigationView bottomNavView;
    private SharedPreferences sharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                case R.id.trainingFragment:
                case R.id.settingsFragment:
                case R.id.colorSchemeFragment:
                case R.id.colorSchemeCustomFragment:
                case R.id.accountFragment:
                    showBottomNav();
                    break;

                default:
                    hideBottomNav();
                    break;
            }
        });

        bottomNavView = binding.bottomNavigation;
        NavigationUI.setupWithNavController(bottomNavView, navController);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        if (sharedPreferences.getInt("auth", 0) == 0)
            navController.navigate(R.id.action_trainingFragment_to_loginFragment);
    }

    private void showBottomNav() {
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }

    private void hideBottomNav() {
        binding.bottomNavigation.setVisibility(View.GONE);
    }
}