package krasnikov.project.pmfightacademy.auth.login.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentLoginBinding
import krasnikov.project.pmfightacademy.auth.login.domain.LoginError
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()
    override val bindingFactory = FragmentLoginBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
    }

    private fun setupBtnListener() {
        with(binding) {
            btnLogin.setSafeOnClickListener {
                val phone = "${layoutPhoneNumber.prefixText}${etPhoneNumber.text.toString()}"
                val password: String = etPassword.text.toString().trim()

                startLogin(phone, password)
            }

            btnCreateAccount.setSafeOnClickListener {
                viewModel.navigateToRegistration()
            }
        }

    }

    private fun startLogin(phone: String, password: String) {
        viewModel.login(phone, password).onEach {
            when (it) {
                is State.Loading -> {
                    //show loading
                }
                is State.Content -> {
                    viewModel.navigateToMainContent()
                }
                is State.Error -> {
                    showError(it.error)
                }
                is State.Empty -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showError(error: LoginError) {
        when (error) {
            LoginError.UnknownError,
            LoginError.InvalidUserDataSent -> {
                binding.etPhoneNumber.text?.clear()
                binding.etPassword.text?.clear()
            }
            LoginError.UserPhoneInvalid -> {
                binding.etPhoneNumber.text?.clear()
            }
            LoginError.UserPasswordInvalid -> {
                binding.etPassword.text?.clear()
            }
        }
    }

}
