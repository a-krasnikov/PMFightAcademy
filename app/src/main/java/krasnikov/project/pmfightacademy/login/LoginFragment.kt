package krasnikov.project.pmfightacademy.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.R

class LoginFragment : Fragment(R.layout.fragment_login) {

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
            startGitHubLogin()
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

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, viewModel.authGitHubUrl)
        startActivity(authIntent)
    }
}