package krasnikov.project.pmfightacademy.auth.login.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentLoginBinding
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.login.domain.LoginValidationError
import krasnikov.project.pmfightacademy.utils.StateLogin
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
    }

    private fun setupBtnListener() {
        binding.btnLogin.setSafeOnClickListener {
            val phone: String = binding.etPhoneNumber.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            startLogin(phone, password)
        }

        binding.btnCreateAccount.setSafeOnClickListener {
            viewModel.navigateToRegistration()
        }
    }

    private fun startLogin(phone: String, password: String) {
        viewModel.login(phone, password).onEach {
            when (it) {
                is StateLogin.Loading -> {
                    //show loading
                }
                is StateLogin.Success -> {
                    viewModel.navigateToMainContent()
                }
                is StateLogin.DataError -> {
                    binding.etPhoneNumber.text?.clear()
                    binding.etPassword.text?.clear()
                }
                is StateLogin.ValidationError -> {
                    when(it.validationError) {
                        LoginValidationError.UserPhoneInvalid -> {
                            binding.etPhoneNumber.text?.clear()
                        }
                        LoginValidationError.UserPasswordInvalid -> {
                            binding.etPassword.text?.clear()
                        }
                        LoginValidationError.UserPhoneAndPasswordInvalid -> {
                            binding.etPhoneNumber.text?.clear()
                            binding.etPassword.text?.clear()
                        }
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun createViewBinding() {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)
    }
}
