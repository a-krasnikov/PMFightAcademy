package krasnikov.project.pmfightacademy.login.registation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentRegistrationBinding
import krasnikov.project.pmfightacademy.login.registation.RegistrationViewModel
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListener()
        observeContent()
    }



    private fun setupBtnListener() {
        binding.btnLogin.setSafeOnClickListener {
            val login: String? = binding.etPhoneNumber.text.toString()
            val password: String? = binding.etPassword.text.toString()
            val name: String? = binding.etName.text.toString()
            if (login.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
                Log.d("TestLog",
                    "(pass.isNullOrEmpty() || phone.isNullOrEmpty()) || name.isNullOrEmpty()")
            } else {
                startRegistration(login, password, name)
            }
        }
    }

    private fun startRegistration(login: String, password: String, name: String) {
        viewModel.startRegistration(context, login, password, name)
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

    override fun createViewBinding() {
        mBinding = FragmentRegistrationBinding.inflate(layoutInflater)
    }
}