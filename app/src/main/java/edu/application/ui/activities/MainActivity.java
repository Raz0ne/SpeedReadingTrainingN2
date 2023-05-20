package edu.application.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.application.R;
import edu.application.databinding.ActivityMainBinding;
import edu.application.ui.adapters.TextFormatter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private BottomNavigationView bottomNavView;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth auth;

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
                case R.id.fontSettingsFragment:
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
        TextFormatter.setSharedPreferences(sharedPreferences);

        auth = FirebaseAuth.getInstance();

        //if (sharedPreferences.getInt("auth", 0) == 0)
          //  navController.navigate(R.id.action_trainingFragment_to_loginFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null)
            navController.navigate(R.id.action_trainingFragment_to_loginFragment);
    }

    private void showBottomNav() {
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }

    private void hideBottomNav() {
        binding.bottomNavigation.setVisibility(View.GONE);
    }
}