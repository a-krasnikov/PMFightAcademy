package krasnikov.project.pmfightacademy.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import krasnikov.project.pmfightacademy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (SharedPref(this).token.isEmpty()) {
//            Navigator.navigateToLogin(supportFragmentManager)
//        } else {
//            Navigator.startScreen(supportFragmentManager)
//        }

    }
}
