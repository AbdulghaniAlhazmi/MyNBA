package com.example.mynba

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import coil.load
import com.example.mynba.databinding.ActivityMainBinding
import com.example.mynba.databinding.NavHeaderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private val imageRef = Firebase.storage.reference
    private lateinit var image : Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler(Looper.getMainLooper()).postDelayed({
            binding.mainLayout.visibility = View.VISIBLE
            binding.splashLayout.visibility = View.GONE
            binding.animationView.visibility = View.GONE
        }, 3000)


        firebaseAuth = FirebaseAuth.getInstance()

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

    override fun onStart() {
        Log.d(TAG, "Start ${firebaseAuth.currentUser?.uid.toString()}")
        checkLoggedIn()
        super.onStart()
    }

    override fun onRestart() {
        Log.d(TAG, "Restart ${firebaseAuth.currentUser?.uid.toString()}")
        checkLoggedIn()
        super.onRestart()
    }

    override fun onPause() {
        Log.d(TAG, "Pause ${firebaseAuth.currentUser?.uid.toString()}")
        checkLoggedIn()
        super.onPause()
    }



    private fun visibilityNavElements(navController: NavController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.boxScoreFragment -> hideBottomNavigation()
                R.id.gameStatusFragment -> hideBottomNavigation()
                R.id.signInFragment -> hideBottomNavigation()
                R.id.signUpFragment -> hideBottomNavigation()
                R.id.gameCommentsFragment -> hideBottomNavigation()
                R.id.tabsFragment -> hideBottomNavigation()
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
        val viewHeader = binding.mainNavigationView.getHeaderView(0)
        val navViewHeaderBinding : NavHeaderBinding = NavHeaderBinding.bind(viewHeader)
        if (firebaseAuth.currentUser == null){
            navDrawerInfo(false,navViewHeaderBinding)
            changeUserMenu(false)
        }else{
            navDrawerInfo(true,navViewHeaderBinding)
            changeUserMenu(true)
        }
    }

    private fun changeUserMenu(user : Boolean){
        if (user){
            binding.mainNavigationView.menu.clear()
            binding.mainNavigationView.inflateMenu(R.menu.drawer_user)
        }else{
            binding.mainNavigationView.menu.clear()
            binding.mainNavigationView.inflateMenu(R.menu.drawer_nav)
        }
    }

    private fun navDrawerInfo(user : Boolean,navHeaderBinding: NavHeaderBinding){
        if (user){
            navHeaderBinding.userEmail.visibility = View.VISIBLE
            navHeaderBinding.userEmail.text = firebaseAuth.currentUser!!.email
            userName(firebaseAuth.currentUser!!.uid,navHeaderBinding)
            getImage(firebaseAuth.currentUser!!.uid,navHeaderBinding)

        }else{
            navHeaderBinding.userEmail.visibility = View.INVISIBLE
            navHeaderBinding.imageView2.visibility = View.INVISIBLE
            navHeaderBinding.userName.visibility = View.INVISIBLE
        }
    }

    private fun userName(uid: String,navHeaderBinding: NavHeaderBinding){
        val userCollectionRef = Firebase.firestore.collection("users")
        userCollectionRef.document(uid)
            .get()
            .addOnCompleteListener {
                if (it.result.exists()) {
                    val username = it.result.getString("username")
                    navHeaderBinding.userName.text = username
                }
            }
    }


    private fun getImage(userId : String,navHeaderBinding: NavHeaderBinding) = CoroutineScope(

        Dispatchers.IO).launch {
        try {
            image = imageRef.child("images/myImage-${userId}").downloadUrl.await()
            navHeaderBinding.imageView2.load(image)
        }catch (e: java.lang.Exception){
            Log.d(TAG,e.message.toString())        }
    }


}

