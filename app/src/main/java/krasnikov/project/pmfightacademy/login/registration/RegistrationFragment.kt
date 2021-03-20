package krasnikov.project.pmfightacademy.login.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.login.LoginViewModel

class RegistrationFragment :Fragment(R.layout.fragment_registration){

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}