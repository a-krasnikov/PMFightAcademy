package krasnikov.project.pmfightacademy.launch.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.ui.LoginFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
class LaunchFragment : Fragment(R.layout.fragment_launch) {

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timeCalculation()
    }


    private fun checkSharedPref() {
        if (sharedPref.token.isEmpty() || sharedPref.token == "NoPref") {
            navigateLoginFragment()
        } else {
            //TODO -> подставить navigateMainContent()
            navigateLoginFragment()
        }
    }

    private fun navigateLoginFragment() {
        findNavController().navigate(LoginFragmentDirections.actionGlobalToLogin())
    }

    private fun navigateMainContent() {
        findNavController().navigate(LoginFragmentDirections.actionLoginToMainContent())
    }


    private fun timeCalculation() {
        val TIME_CONST: Long = 2000
        lateinit var countDownTimer: CountDownTimer
        countDownTimer = object : CountDownTimer(TIME_CONST, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                checkSharedPref()
            }
        }.start()
    }


}