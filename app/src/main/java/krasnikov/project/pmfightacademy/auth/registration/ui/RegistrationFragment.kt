package krasnikov.project.pmfightacademy.auth.registration.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationValidationError
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.utils.StateRegistration
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
    }

    private fun setupBtnListener() {
        binding.btnLogin.setSafeOnClickListener {
            val phone: String = binding.etPhoneNumber.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()
            val name: String = binding.etName.text.toString().trim()

            startRegistration(phone, password, name)
        }
    }

    private fun startRegistration(phone: String, password: String, name: String) {
        viewModel.startRegistration(phone, password, name).onEach {
            when (it) {
                is StateRegistration.Loading -> {
                    //show loading
                }
                is StateRegistration.Success -> {
                    viewModel.navigateToMainContent()
                }
                is StateRegistration.DataError -> {
                    binding.etPhoneNumber.text?.clear()
                    binding.etPassword.text?.clear()
                    binding.etName.text?.clear()
                }
                is StateRegistration.ValidationError -> {
                    when(it.validationError) {
                        RegistrationValidationError.UserPhoneInvalid -> {
                            binding.etPhoneNumber.text?.clear()
                        }
                        RegistrationValidationError.UserPasswordInvalid -> {
                            binding.etPassword.text?.clear()
                        }
                        RegistrationValidationError.UserNameInvalid -> {
                            binding.etName.text?.clear()
                        }
                        RegistrationValidationError.UserPhoneNameAndPasswordInvalid -> {
                            binding.etPhoneNumber.text?.clear()
                            binding.etPassword.text?.clear()
                            binding.etName.text?.clear()
                        }
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun createViewBinding() {
        mBinding = FragmentRegistrationBinding.inflate(layoutInflater)
    }
}
