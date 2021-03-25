package krasnikov.project.pmfightacademy.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment
        setupBottomNavigation(navigationHost.navController)

    }

    private fun setupBottomNavigation(controller: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.main_content)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.info_fragment,
                R.id.coaches_fragment,
                R.id.current_activities_fragment,
                R.id.history_fragment,
                R.id.settings_fragment -> {
                    bottomNav.isVisible = true
                }
                else -> {
                    bottomNav.isVisible = false
                }
            }
        }

        bottomNav.setupWithNavController(controller)
    }
}
