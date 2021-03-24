package krasnikov.project.pmfightacademy.login.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.databinding.FragmentLoginBinding
import krasnikov.project.pmfightacademy.login.LoginViewModel
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import krasnikov.project.pmfightacademy.utils.State
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
            val register = Register("+380672336981", "Testpass2","Gleb")
            //viewModel.navigateRegistration()
            val etLogin: String? = binding.etPhoneNumber.text.toString()
            val password: String? = binding.etPassword.text.toString()
            val login = Login(etLogin, password)
            if (login.login.isNullOrEmpty() || login.password.isNullOrEmpty()) {
                Log.d("LOGINLOG", "(pass.isNullOrEmpty() || phone.isNullOrEmpty())")
            } else {
                Log.d("LOGINLOG", "LoginFragment -> setupBtnListener -> else")
                startLogin(login)
            }
        }

        binding.btnCreateAccount.setSafeOnClickListener {
            viewModel.showPref()
          //viewModel.navigateRegistration()
        }

        binding.tvDontHaveAccaut.setSafeOnClickListener {
            viewModel.cleanPref()
        }
    }




    private fun startLogin(login: Login) {
        Log.d("LOGINLOG", "LoginFragment -> setupBtnListener -> else")
        viewModel.getAccessToken(login).onEach {
            when (it) {
                is StateLogin.Loading -> {
                    Log.d("LOGINLOG", "LoginFragment -> startLogin() -> StateLogin.Loading")
                }
                is StateLogin.Success -> {
                    Log.d("LOGINLOG", "LoginFragment -> startLogin() -> StateLogin.Success")
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
        mBinding = FragmentLoginBinding.inflate(layoutInflater)
    }
}