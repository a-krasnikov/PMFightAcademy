package krasnikov.project.pmfightacademy.auth.registration.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationError
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>() {

    override val viewModel: RegistrationViewModel by viewModels()

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

                startRegistration(phone, password, name)
            }
        }

    }

    private fun startRegistration(phone: String, password: String, name: String) {
        viewModel.startRegistration(phone, password, name).onEach {
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
            }
        }.launchIn(lifecycleScope)
    }

    private fun showError(error: RegistrationError) {
        when (error) {
            RegistrationError.UserNameInvalid -> {
                binding.etName.text?.clear()
            }
            RegistrationError.UserPasswordInvalid -> {
                binding.etPassword.text?.clear()
            }
            RegistrationError.UserPhoneInvalid -> {
                binding.etPhoneNumber.text?.clear()
            }
            RegistrationError.InvalidUserDataSent,
            RegistrationError.SuchUserAlreadyExits,
            RegistrationError.UnknownError -> {
                binding.etPhoneNumber.text?.clear()
                binding.etPassword.text?.clear()
                binding.etName.text?.clear()
            }
        }
    }

    override fun createViewBinding() {
        mBinding = FragmentRegistrationBinding.inflate(layoutInflater)
    }
}
