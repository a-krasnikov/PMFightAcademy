package krasnikov.project.pmfightacademy.login.registation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import krasnikov.project.pmfightacademy.login.registation.RegistrationViewModel
import krasnikov.project.pmfightacademy.utils.State
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
        //    val etLogin: String? = binding.etPhoneNumber.text.toString()
            val etLogin: String? = "+380931682232"

        //    val password: String? = binding.etPassword.text.toString)
            val password: String? = "Testpass1"

            val register = Register(etLogin, password, "Gleb")
            if (register.login.isNullOrEmpty() || register.password.isNullOrEmpty() || register.password.isNullOrEmpty()) {
                Log.d("LOGINLOG", "(pass.isNullOrEmpty() || phone.isNullOrEmpty())")
                showToast(R.string.registrationFragment)
            } else {
                Log.d("LOGINLOG", "RegistrationFragment -> setupBtnListener -> else")
                startRegistration(register)
            }
        }
    }

    private fun startRegistration(register: Register) {
        Log.d("LOGINLOG", "RegistrationFragment -> startLogin() -> else")
        viewModel.startRegistration(register).onEach {
            when (it) {
                is StateLogin.Loading -> {
                    Log.d("LOGINLOG", "RegistrationFragment -> startLogin() -> StateLogin.Loading")
                }
                is StateLogin.Success -> {
                    Log.d("LOGINLOG", "RegistrationFragment -> startLogin() -> StateLogin.Success")
                    showToast(R.string.toast_login_successful)
                }
                is StateLogin.Error -> {
                    Log.d("LOGINLOG", "LoginFragment -> startLogin() -> StateLogin.Error")
                    showToast(it.error.stringRes)
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun createViewBinding() {
        mBinding = FragmentRegistrationBinding.inflate(layoutInflater)
    }
}