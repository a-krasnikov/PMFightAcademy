package krasnikov.project.pmfightacademy.login.registation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.login.data.model.Register
import krasnikov.project.pmfightacademy.login.registation.RegistrationViewModel
import krasnikov.project.pmfightacademy.utils.StateLogin
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
            val etLogin: String? = binding.etPhoneNumber.text.toString()
            val password: String? = binding.etPassword.text.toString()
            val name: String? = binding.etName.text.toString()
            val register = Register(etLogin, password, name)
            if (getEtCheck(register)) {
                startRegistration(register)
            }
        }
    }

    private fun startRegistration(register: Register) {
        viewModel.startRegistration(register).onEach {
            when (it) {
                is StateLogin.Loading -> {
                }
                is StateLogin.Success -> {
                    showToast(R.string.toast_login_successful)
                }
                is StateLogin.Error -> {
                    showToast(it.error.stringRes)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun getEtCheck(register: Register): Boolean {
        if (register.login.isNullOrEmpty()) {
            showToast(R.string.registrationFragmentLoginCheck)
            return false
        } else if (register.password.isNullOrEmpty()) {
            showToast(R.string.registrationFragmentPasswordCheck)
            return false
        } else if (register.name.isNullOrEmpty()) {
            showToast(R.string.registrationFragmentNameChack)
            return false
        } else {
            return true
        }
    }

    override fun createViewBinding() {
        mBinding = FragmentRegistrationBinding.inflate(layoutInflater)
    }
}