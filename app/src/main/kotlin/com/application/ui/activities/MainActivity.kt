package com.application.ui.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.application.R
import com.application.databinding.ActivityMainBinding
import com.application.ui.fragments.navigation.adapters.TextFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _: NavController?,
                                                        navDestination: NavDestination,
                                                        _: Bundle? ->
            when (navDestination.id) {
                R.id.trainingFragment,
                R.id.settingsFragment,
                R.id.colorSchemeFragment,
                R.id.colorSchemeCustomFragment,
                R.id.fontSettingsFragment,
                R.id.accountFragment ->
                    showBottomNav()

                else -> hideBottomNav()
            }
        }

        bottomNavView = binding.bottomNavigation
        setupWithNavController(bottomNavView, navController)
        bottomNavView.setOnItemReselectedListener { item ->
            navController.popBackStack(item.itemId, false)
        }
        bottomNavView.setOnItemSelectedListener { item ->
            onNavDestinationSelected(item, navController)
            true
        }

        sharedPreferences = getPreferences(MODE_PRIVATE)
        TextFormatter.sharedPreferences = sharedPreferences

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser == null)
            navController.navigate(R.id.loginFragment)
    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }
}