package krasnikov.project.pmfightacademy.launch.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.ui.LoginFragmentDirections
import javax.inject.Inject

/*class LaunchFragment: Fragment(R.layout.fragment_launch){

}*/
@AndroidEntryPoint
class LaunchFragment : Fragment(R.layout.fragment_launch) {

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSharedPref()
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
}