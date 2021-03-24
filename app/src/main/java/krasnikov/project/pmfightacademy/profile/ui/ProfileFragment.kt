package krasnikov.project.pmfightacademy.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import krasnikov.project.pmfightacademy.LoginFlowDirections
import krasnikov.project.pmfightacademy.databinding.FragmentProfileBinding
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

class ProfileFragment: Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnLogOut.setSafeOnClickListener {
            //remove token from sharedPrefs
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(LoginFlowDirections.actionGlobalToLogin())
    }
}