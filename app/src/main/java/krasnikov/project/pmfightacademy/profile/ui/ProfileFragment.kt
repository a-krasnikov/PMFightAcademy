package krasnikov.project.pmfightacademy.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.LoginFlowDirections
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentActivitiesPlannedBinding
import krasnikov.project.pmfightacademy.databinding.FragmentProfileBinding
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment: BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {

    override val bindingFactory = FragmentProfileBinding::bind
    override val viewModel by viewModels<ProfileViewModel>()
    @Inject lateinit var sharedPref: SharedPref


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSwitchState()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            cardLogout.setSafeOnClickListener {
                sharedPref.token = SharedPref.TOKEN_DEFAULT_VALUE
                viewModel.logOut()
            }
            switchNotifications.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    viewModel.enableNotifications()
                } else {
                    viewModel.disableNotifications()
                }
            }
        }

    }

    private fun loadSwitchState() {
        with(binding.switchNotifications) {
            isChecked = sharedPref.notificationState == SharedPref.NOTIFICATIONS_ENABLED
        }
    }
}
