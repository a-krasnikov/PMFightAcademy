package krasnikov.project.pmfightacademy.auth.registration.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationError
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>(R.layout.fragment_registration) {

    override val viewModel: RegistrationViewModel by viewModels()
    override val bindingFactory = FragmentRegistrationBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
    }

    private fun setupBtnListener() {
        with(binding) {
            btnLogin.setSafeOnClickListener {
                val phone = "${layoutPhoneNumber.prefixText}${etPhoneNumber.text.toString()}"
                val password: String = etPassword.text.toString().trim()
                val name: String = etName.text.toString().trim()

                layoutPhoneNumber.error = null
                layoutPassword.error = null
                layoutName.error = null

                startRegistration(phone, password, name)
            }
        }

    }

    private fun startRegistration(phone: String, password: String, name: String) {
        viewModel.startRegistration(phone, password, name).onEach {
            when (it) {
                is State.Loading, State.Empty -> {
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
            }
        }.launchIn(lifecycleScope)
    }

    private fun showError(error: ErrorWrapper<RegistrationError>) {
        when (error) {
            is ErrorWrapper.General -> {
                binding.etPhoneNumber.text?.clear()
                binding.etPassword.text?.clear()
                binding.etName.text?.clear()
                showToast(R.string.registration_failed)
            }
            is ErrorWrapper.ClassSpecific -> {
                when(error.errorType) {
                    RegistrationError.UserNameInvalid -> {
                        binding.etName.text?.clear()
                        binding.layoutName.error = getString(RegistrationError.UserNameInvalid.errorString)
                    }
                    RegistrationError.UserPasswordInvalid -> {
                        binding.etPassword.text?.clear()
                        binding.layoutPassword.error = getString(RegistrationError.UserPasswordInvalid.errorString)
                    }
                    RegistrationError.UserPhoneInvalid -> {
                        binding.etPhoneNumber.text?.clear()
                        binding.layoutPhoneNumber.error = getString(RegistrationError.UserPhoneInvalid.errorString)
                    }
                }
            }
        }
    }

}
