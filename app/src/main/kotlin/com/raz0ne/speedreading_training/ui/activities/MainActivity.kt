package com.raz0ne.speedreading_training.ui.activities

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.raz0ne.speedreading_training.extension.setLocale
import com.raz0ne.speedreading_training.ui.navigation.BottomNavigationBar
import com.raz0ne.speedreading_training.ui.navigation.Navigation
import com.raz0ne.speedreading_training.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    //private lateinit var binding: ActivityMainBinding
    /*private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView*/
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
        /*
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

            when (navDestination.id) {
                R.id.trainingFragment,
                R.id.settingsFragment,
                R.id.colorSchemeFragment,
                R.id.colorSchemeCustomFragment,
                R.id.fontSettingsFragment,
                R.id.accountFragment,
                R.id.emailResettingFragment,
                R.id.passwordResettingFragment ->
                    showToolbar()

                else -> hideToolbar()
            }
        }

        setSupportActionBar(binding.toolbar)
        with(AppBarConfiguration(bottomNavView.menu)) {
            binding.toolbar.setupWithNavController(navController, this)
        }

        TextFormatter.sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)*/

        auth = Firebase.auth
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setLocale()))
    }

    /*override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser?.reload()
        if (currentUser == null)
            navController.navigate()
    }*/

    /*
    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
        title = navController.currentDestination!!.label
    }

    private fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
    }*/

    fun checkPermission(permission: String) : Boolean =
        if (ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)

            ContextCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED
        } else
            true
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        //topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        backgroundColor = MaterialTheme.colors.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController = navController)
        }
    }
}

@Preview("MainScreen")
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
