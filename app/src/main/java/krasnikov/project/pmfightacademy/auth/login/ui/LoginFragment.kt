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
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
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

                layoutPhoneNumber.error = null
                layoutPassword.error = null

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
                    binding.stateInfo.showLoading()
                }
                is State.Content -> {
                    binding.stateInfo.resetState()
                    viewModel.navigateToMainContent()
                }
                is State.Error -> {
                    binding.stateInfo.resetState()
                    showError(it.error)
                }
                is State.Empty -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showError(error: ErrorWrapper<LoginError>) {
        when (error) {
            is ErrorWrapper.General -> {
                binding.etPhoneNumber.text?.clear()
                binding.etPassword.text?.clear()
                showToast(R.string.login_failed)
            }
            is ErrorWrapper.ClassSpecific -> {
                when(error.errorType) {
                    LoginError.UserPasswordInvalid -> {
                        binding.layoutPassword.error = getString(LoginError.UserPasswordInvalid.errorString)
                        binding.etPassword.text?.clear()
                    }
                    LoginError.UserPhoneInvalid -> {
                        binding.layoutPhoneNumber.error = getString(LoginError.UserPhoneInvalid.errorString)
                        binding.etPhoneNumber.text?.clear()
                    }
                }
            }
        }
    }

}
