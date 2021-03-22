package krasnikov.project.pmfightacademy.login.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentLoginBinding
import krasnikov.project.pmfightacademy.login.LoginViewModel
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding, LoginViewModel>() {

  override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
        observeContent()
    }

    override fun setupBinding() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    private fun setupBtnListener() {
        binding.btnLogin.setSafeOnClickListener {
            val login: String? = binding.etPhoneNumber.text.toString()
            val password: String? = binding.etPassword.text.toString()
            if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
                Log.d("TestLog", "(pass.isNullOrEmpty() || phone.isNullOrEmpty())")
            } else {
                startLogin(login, password)
            }
        }
    }

    private fun startLogin(login: String, password: String) {
        viewModel.getAccessToken(context, login, password)
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                }
                is State.Content -> {
                    showToast(R.string.toast_login_successful)
                }
                is State.Error -> {
                    showToast(it.error.stringRes)
                }
            }
        }
    }
}