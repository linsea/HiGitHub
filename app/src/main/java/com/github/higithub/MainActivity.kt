package com.github.higithub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.higithub.databinding.ActivityMainBinding
import com.github.higithub.login.AuthManager
import kotlinx.coroutines.launch
import logcat.asLog
import logcat.logcat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        AuthManager.checkLogin()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logcat { "onNewIntent: $intent" }
        if(AuthManager.isOauthCallback(intent)) {
            val errDesc = AuthManager.getOauthErrorDesc(intent!!)
            if (errDesc != null) {
                Toast.makeText(this, "Login Error: $errDesc", Toast.LENGTH_LONG).show()
            } else if (AuthManager.isGotCodeSuccess(intent)) {
                lifecycleScope.launch {
                    try {
                        AuthManager.reqAccessToken(AuthManager.getCode(intent))
                    } catch (e: Throwable) {
                        Toast.makeText(this@MainActivity, "Login Error: ${e.message}", Toast.LENGTH_LONG).show()
                        logcat { e.asLog() }
                    }
                }
            }
        }
    }

}