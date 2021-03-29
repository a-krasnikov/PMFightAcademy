package krasnikov.project.pmfightacademy.launch.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.databinding.FragmentLaunchBinding
import javax.inject.Inject

@Suppress("EmptyFunctionBlock", "MagicNumber")
@AndroidEntryPoint
class LaunchFragment : Fragment(R.layout.fragment_launch) {

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var binding: FragmentLaunchBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAnimationListener()
    }


    private fun checkSharedPref() {
        if (sharedPref.token == SharedPref.TOKEN_DEFAULT_VALUE || sharedPref.token.isEmpty()) {
            navigateLoginFragment()
        } else {
            navigateMainContent()
        }
    }

    private fun navigateLoginFragment() {
        findNavController().navigate(LaunchFragmentDirections.actionGlobalToLogin())
    }

    private fun navigateMainContent() {
        findNavController().navigate(LaunchFragmentDirections.actionLaunchToMainContent())
    }


    private fun setAnimationListener() {
        binding.avLoadingDots.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(p0: Animator?) {
                checkSharedPref()
            }
        })
    }

}
