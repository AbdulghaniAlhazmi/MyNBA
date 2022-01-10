package com.example.mynba

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mynba.databinding.ActivityMainBinding
import com.example.mynba.databinding.NavHeaderBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, firebaseAuth.currentUser?.uid.toString())

        navController = findNavController(R.id.main_nav_host)

        appBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_news, R.id.navigation_games,
            R.id.navigation_standings)
            .setOpenableLayout(binding.mainDrawerLayout)
            .build()

        setSupportActionBar(binding.mainToolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)

        visibilityNavElements(navController)

        checkLoggedIn()

    }



    override fun onResume() {
        Log.d(TAG, "Resume ${firebaseAuth.currentUser?.uid.toString()}")
        checkLoggedIn()
        super.onResume()
    }



    private fun visibilityNavElements(navController: NavController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.boxScoreFragment -> hideBottomNavigation()
                R.id.gameStatusFragment -> hideBottomNavigation()
                R.id.newsPageFragment -> hideBottomNavigation()
                R.id.signInFragment -> hideBottomNavigation()
                R.id.signUpFragment -> hideBottomNavigation()
                R.id.gameCommentsFragment -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }

    }


    private fun hideBottomNavigation() {
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer

        binding.mainNavigationView.setupWithNavController(navController)
    }

    private fun showBothNavigation() {
        binding.mainBottomNavigationView.visibility = View.VISIBLE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl()
    }

    private fun setupNavControl() {
        binding.mainNavigationView.setupWithNavController(navController)
        binding.mainBottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when {
            binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)

            }
            else -> {
                super.onBackPressed()
            }
        }
    }



    private fun checkLoggedIn() {
        if (firebaseAuth.currentUser == null){

            binding.mainNavigationView.menu.clear()
            binding.mainNavigationView.inflateMenu(R.menu.drawer_nav)


        }else{
            val viewHeader = binding.mainNavigationView.getHeaderView(0)
            val navViewHeaderBinding : NavHeaderBinding = NavHeaderBinding.bind(viewHeader)
            navViewHeaderBinding.userEmail.visibility = View.VISIBLE
            navViewHeaderBinding.userEmail.text = firebaseAuth.currentUser!!.email
            binding.mainNavigationView.menu.clear()
            binding.mainNavigationView.inflateMenu(R.menu.drawer_user)
            binding.mainNavigationView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.sign_out -> {
                        firebaseAuth.signOut()
                        navController.navigate(R.id.navigation_news)
                        recreate()
                        true
                    }
                    R.id.profileFragment ->{
                        navController.navigate(R.id.profileFragment)
                        true
                    }
                    else -> true
                }
            }
        }
    }
}

