package krasnikov.project.pmfightacademy.info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentInfoBinding
import krasnikov.project.pmfightacademy.utils.State

class InfoFragment: BaseFragment<InfoViewModel, FragmentInfoBinding>() {
    override val viewModel: InfoViewModel by viewModels()

    override fun createViewBinding() {
        mBinding = FragmentInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToInfoContent()
        viewModel.getInfo()
    }

    private fun subscribeToInfoContent() {
        viewModel.infoScreenContent.observe(viewLifecycleOwner, { state ->
            when(state) {
                is State.Content -> {
                    setIsLoading(false)
                    binding.tvInfoDescription.text = state.data.description
                }
                is State.Loading -> {
                    setIsLoading(true)
                }
                is State.Error -> {
                    //show error message
                }
            }

        })
    }

    private fun setIsLoading(isVisible: Boolean) {
        binding.pbInfoLoading.isVisible = isVisible
    }


}
