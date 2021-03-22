package krasnikov.project.pmfightacademy.launch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.app.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentLaunchBinding
import krasnikov.project.pmfightacademy.launch.ui.LaunchViewModel

/*class LaunchFragment: Fragment(R.layout.fragment_launch){

}*/
@AndroidEntryPoint
class LaunchFragment: BaseFragment<FragmentLaunchBinding, LaunchViewModel>() {

    override val viewModel: LaunchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //checkSharedPref()
    }

    override fun setupBinding() {
        binding = FragmentLaunchBinding.inflate(layoutInflater)
    }

    private fun checkSharedPref() {
        viewModel.checkSharedPref()
    }

}