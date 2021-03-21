package krasnikov.project.pmfightacademy.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.ui.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentLoginBinding
import krasnikov.project.pmfightacademy.login.LoginViewModel
import krasnikov.project.pmfightacademy.utils.State

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.handleOauth(requireActivity().intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBtnListener()
        observeContent()
    }

    override fun setupBinding() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    private fun setupBtnListener() {
        binding.btnLogin.setOnClickListener {
            startLogin()
        }
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    showLoading()
                }
                is State.Content -> {
                    hideLoading()
                    showToast(R.string.toast_login_successful)
                }
                is State.Error -> {
                    hideLoading()
                    showToast(it.error.stringRes)
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbLoading.isVisible = true
    }

    private fun hideLoading() {
        binding.pbLoading.isVisible = false
    }

    private fun startLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, viewModel.authGitHubUrl)
        startActivity(authIntent)
    }
}