package com.example.nayabazar.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nayabazar.R
import com.example.nayabazar.databinding.ActivityMainBinding
import com.example.nayabazar.view.fragments.HomeFragment
import com.example.nayabazar.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val currentUser = authViewModel.currentUser
        if (currentUser != null) {
            navHostFragment.findNavController().navigate(R.id.homeFragment)
        }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destinaton, _ ->
                when (destinaton.id) {
                    R.id.homeFragment, R.id.cartFragment, R.id.profileFragment ->
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    else ->
                        binding.bottomNavigationView.visibility = View.GONE
                }

            }
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        if (currentFragment is HomeFragment) {
            // Close the app when the back button is pressed on the SignUpFragment
            finish()
        } else {
            // Continue with the default back button behavior
            super.onBackPressed()
        }
    }
}