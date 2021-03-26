package krasnikov.project.pmfightacademy.launch.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import javax.inject.Inject

@Suppress("EmptyFunctionBlock", "MagicNumber")
@AndroidEntryPoint
class LaunchFragment : Fragment(R.layout.fragment_launch) {

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timeCalculation()
    }


    private fun checkSharedPref() {
        if (sharedPref.token == SharedPref.TOKEN_DEFAULT_VALUE || sharedPref.token.isEmpty()) {
            navigateLoginFragment()
        } else {
            navigateMainContent()
        }
    }

    private fun navigateLoginFragment() {
        findNavController().navigate(LaunchFragmentDirections.actionGlobalToLogin())
    }

    private fun navigateMainContent() {
        findNavController().navigate(LaunchFragmentDirections.actionLaunchToMainContent())
    }


    private fun timeCalculation() {
        object : CountDownTimer(LAUNCH_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                checkSharedPref()
            }
        }.start()
    }

    private companion object {
        const val LAUNCH_TIME = 2000L
    }

}
