package krasnikov.project.pmfightacademy.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import krasnikov.project.pmfightacademy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.fcvNavigationContainer) as NavHostFragment?
        navigationHost?.apply {
            setupBottomNavigation(createNavController(this))
        } ?: return
    }

    private fun setupBottomNavigation(controller: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bnvMainNavigator)
        bottomNav?.setupWithNavController(controller)
    }

    private fun createNavController(navHostFragment: NavHostFragment): NavController {
        return navHostFragment.navController
    }
}
