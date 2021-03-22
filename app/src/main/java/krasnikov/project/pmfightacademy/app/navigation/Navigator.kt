package krasnikov.project.pmfightacademy.app.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.login.registation.ui.RegistrationFragment
import krasnikov.project.pmfightacademy.login.ui.LoginFragment

object Navigator {

    fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<LoginFragment>(R.id.login_fragment)
            setReorderingAllowed(true)
        }
    }

    fun navigateToRegistration(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<RegistrationFragment>(R.id.register_fragment)
            setReorderingAllowed(true)
        }
    }

    fun navigateToAcademyInfo(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace<RegistrationFragment>(R.id.register_fragment)
            setReorderingAllowed(true)
        }
    }
}