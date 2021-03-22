package krasnikov.project.pmfightacademy.info.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentInfoBinding

@AndroidEntryPoint
class InfoFragment: BaseFragment<InfoViewModel, FragmentInfoBinding>() {
    override val viewModel by viewModels<InfoViewModel>()

    override fun createViewBinding() {
        mBinding = FragmentInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun subscribeToFlow() {

    }
}